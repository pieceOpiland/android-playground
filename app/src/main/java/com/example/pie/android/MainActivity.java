package com.example.pie.android;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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
        new TodoResource().getItems().subscribe(new Consumer<List<TodoItem>>() {
            @Override
            public void accept(@NonNull List<TodoItem> todoItems) throws Exception {
                // This should probably be made more efficient.
                adapter.clear();
                adapter.addAll(todoItems);
                refresh.setRefreshing(false);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                refresh.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Failed to contact Server.", Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
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

            new TodoResource().addItems(newItems).subscribe(new Consumer<List<TodoItem>>() {
                @Override
                public void accept(@NonNull List<TodoItem> todoItems) throws Exception {
                    adapter.addAll(todoItems);
                    input.setText("");
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    Toast.makeText(MainActivity.this, "Failed to contact Server.", Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                }
            });
        }
    }

    public void clearCompletedTasks(View v) {
        final EditText input = (EditText) findViewById(R.id.newTask);
        new TodoResource().clearDone().subscribe(new Consumer<List<TodoItem>>() {
            @Override
            public void accept(@NonNull List<TodoItem> todoItems) throws Exception {
                adapter.clear();
                adapter.addAll(todoItems);
                input.setText("");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void onRefresh() {
        retrieveItems();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        retrieveItems();
    }
}
