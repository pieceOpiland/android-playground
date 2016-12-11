package com.example.pie.android;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.pie.android.adapters.TodoAdapter;
import com.example.pie.android.models.TodoItem;
import com.example.pie.android.resources.TodoResource;

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
            TodoResource.getInstance().addItem(newItem, new Callback<TodoItem>() {
                @Override
                public void onResponse(Call<TodoItem> call, Response<TodoItem> response) {
                    adapter.add(response.body());
                    input.setText("");
                }

                @Override
                public void onFailure(Call<TodoItem> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public void clearCompletedTasks(View v){
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
}
