package com.example.pie.android.rest.resource;

import com.example.pie.android.model.TodoItem;
import com.example.pie.android.rest.RetrofitProvider;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class TodoResource {

    private TodoApi resource;

    public TodoResource() {
        this(RetrofitProvider.getInstance().create(TodoApi.class));
    }

    public TodoResource(TodoApi resource) {
        this.resource = resource;
    }

    public Single<List<TodoItem>> getItems() {
        return resource.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<List<TodoItem>> addItems(List<TodoItem> items) {
        return resource.addItems(items)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<TodoItem>> clearDone() {
        return resource.clearDone()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<ResponseBody> completeItem(int id) {
        return resource.completeItem(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public interface TodoApi {
        @GET("/rest/todo")

        Single<List<TodoItem>> getItems();

        @POST("/rest/todo")
        Single<List<TodoItem>> addItems(@Body List<TodoItem> items);

        @DELETE("/rest/todo")
        Single<List<TodoItem>> clearDone();

        @PUT("/rest/todo/{id}")
        Single<ResponseBody> completeItem(@Path("id") int id);
    }
}
