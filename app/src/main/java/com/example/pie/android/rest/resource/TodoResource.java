package com.example.pie.android.rest.resource;

import com.example.pie.android.model.TodoItem;
import com.example.pie.android.rest.RetrofitProvider;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    public void addItems(List<TodoItem> items, Callback<List<TodoItem>> cb) {
        resource.addItems(items).enqueue(cb);
    }

    public void clearDone(Callback<List<TodoItem>> cb) {
        resource.clearDone().enqueue(cb);
    }
    public void completeItem(int id, Callback<ResponseBody> cb) {
        resource.completeItem(id).enqueue(cb);
    }

    public interface TodoApi {
        @GET("/rest/todo")
        Call<List<TodoItem>> getItems();

        @POST("/rest/todo")
        Call<List<TodoItem>> addItems(@Body List<TodoItem> items);

        @DELETE("/rest/todo")
        Call<List<TodoItem>> clearDone();

        @PUT("/rest/todo/{id}")
        Call<ResponseBody> completeItem(@Path("id") int id);
    }
}
