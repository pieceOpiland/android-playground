package com.example.pie.android.rest.resource;

import com.example.pie.android.model.TodoItem;
import com.example.pie.android.rest.RetrofitProvider;
import com.example.pie.android.rest.endpoint.TodoApi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class TodoResource {

    private static TodoResource instance = new TodoResource();
    private TodoApi resource;

    private TodoResource() {
        resource = RetrofitProvider.getInstance().create(TodoApi.class);
    }

    public static TodoResource getInstance() {
        return instance;
    }

    public void getItems(Callback<List<TodoItem>> cb) {
        resource.getItems().enqueue(cb);
    }

    public void addItem(TodoItem item, Callback<TodoItem> cb) {
        resource.addItem(item).enqueue(cb);
    }

    public void addItems(List<TodoItem> items, Callback<List<TodoItem>> cb) {
        resource.addItems(items).enqueue(cb);
    }

    public void clearDone(Callback<List<TodoItem>> cb) {
        resource.clearDone().enqueue(cb);
    }
    public void completeItem(int id, Callback<ResponseBody> cb) {
        resource.completeItem(id).enqueue(cb);
    }
}
