package com.example.pie.android;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<TodoItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new TodoAdapter(this, R.layout.todo_item, new ArrayList<TodoItem>() );

        final ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        final EditText input = (EditText) findViewById(R.id.newTask);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        Button clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        TodoResource.getInstance().getItems(new Callback<List<TodoItem>>() {
            @Override
            public void onResponse(Call<List<TodoItem>> call, Response<List<TodoItem>> response) {
                adapter.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<TodoItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
