package com.example.pie.android.resources;

import com.example.pie.android.models.TodoItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class TodoResource {

    private static TodoResource instance = new TodoResource();
    private TodoInterface resource;

    private TodoResource() {
        Retrofit rfit = new Retrofit.Builder()
                .baseUrl("https://limitless-everglades-64303.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resource = rfit.create(TodoInterface.class);
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

    public void clearDone(Callback<List<TodoItem>> cb) {
        resource.clearDone().enqueue(cb);
    }
    public void finishItem(int id, Callback<ResponseBody> cb) {
        resource.finishItem(id).enqueue(cb);
    }

    private interface TodoInterface {
        @GET("/rest/todo")
        Call<List<TodoItem>> getItems();

        @POST("/rest/todo")
        Call<TodoItem> addItem(@Body TodoItem item);

        @DELETE("/rest/todo")
        Call<List<TodoItem>> clearDone();

        @PUT("/rest/todo/{id}")
        Call<ResponseBody> finishItem(@Path("id") int id);
    }
}
