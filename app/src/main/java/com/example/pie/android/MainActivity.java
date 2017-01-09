package com.example.pie.android;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pie.android.adapter.TodoAdapter;
import com.example.pie.android.model.TodoItem;
import com.example.pie.android.rest.resource.TodoResource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayAdapter<TodoItem> adapter;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setOnRefreshListener(this);
        adapter = new TodoAdapter(this, R.layout.todo_item, new ArrayList<TodoItem>() );

        final ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        retrieveItems();
    }

    public void retrieveItems() {
        TodoResource.getInstance().getItems(new Callback<List<TodoItem>>() {
            @Override
            public void onResponse(Call<List<TodoItem>> call, Response<List<TodoItem>> response) {
                // This should probably be made more efficient.
                adapter.clear();
                adapter.addAll(response.body());
                refresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<TodoItem>> call, Throwable t) {
                refresh.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Failed to contact Server.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void submitItem(View v){
        final EditText input = (EditText) findViewById(R.id.newTask);
        String userInput = input.getText().toString().trim();
        if(userInput.length() > 0){
            TodoItem newItem = new TodoItem();
            newItem.setTask(userInput);
            List<TodoItem> newItems = new ArrayList<>();
            newItems.add(newItem);
            TodoResource.getInstance().addItems(newItems, new Callback<List<TodoItem>>() {
                @Override
                public void onResponse(Call<List<TodoItem>> call, Response<List<TodoItem>> response) {
                    adapter.addAll(response.body());
                    input.setText("");
                }

                @Override
                public void onFailure(Call<List<TodoItem>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to contact Server.", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
    }

    public void clearCompletedTasks(View v) {
        final EditText input = (EditText) findViewById(R.id.newTask);
        TodoResource.getInstance().clearDone(new Callback<List<TodoItem>>() {
            @Override
            public void onResponse(Call<List<TodoItem>> call, Response<List<TodoItem>> response) {
                adapter.clear();
                adapter.addAll(response.body());
                input.setText("");
            }

            @Override
            public void onFailure(Call<List<TodoItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onRefresh() {
        retrieveItems();
    }

    @Override
    public void onRestart() {
        super.onResume();
        retrieveItems();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        MenuInflater mI = getMenuInflater();
        mI.inflate(R.menu.main_menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refresh.setRefreshing(true);
                retrieveItems();
                break;
        }
        return true;
    }
}
