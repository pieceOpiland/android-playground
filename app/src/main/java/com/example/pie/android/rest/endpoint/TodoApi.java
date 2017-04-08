package com.example.pie.android.rest.endpoint;

import com.example.pie.android.model.TodoItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TodoApi {
    @GET("/rest/todo")
    Call<List<TodoItem>> getItems();

    @POST("/rest/todo")
    Call<TodoItem> addItem(@Body TodoItem item);

    @POST("/rest/todo")
    Call<List<TodoItem>> addItems(@Body List<TodoItem> items);

    @DELETE("/rest/todo")
    Call<List<TodoItem>> clearDone();

    @PUT("/rest/todo/{id}")
    Call<ResponseBody> completeItem(@Path("id") int id);
}