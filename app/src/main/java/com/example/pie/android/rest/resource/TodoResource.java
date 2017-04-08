package com.example.pie.android.rest.resource;

import com.example.pie.android.model.TodoItem;
import com.example.pie.android.rest.RetrofitProvider;

import java.util.List;

import io.reactivex.Scheduler;
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
    private Scheduler subscribeOn;
    private Scheduler observeOn;

    public TodoResource() {
        this(RetrofitProvider.getInstance().create(TodoApi.class),
                Schedulers.io(),
                AndroidSchedulers.mainThread());
    }

    public TodoResource(TodoApi resource, Scheduler subscribeOn, Scheduler observeOn) {
        this.resource = resource;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public Single<List<TodoItem>> getItems() {
        return resource.getItems()
                .subscribeOn(subscribeOn)
                .observeOn(observeOn);
    }

    public Single<List<TodoItem>> addItems(List<TodoItem> items) {
        return resource.addItems(items)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn);
    }

    public Single<List<TodoItem>> clearDone() {
        return resource.clearDone()
                .subscribeOn(subscribeOn)
                .observeOn(observeOn);
    }
    public Single<ResponseBody> completeItem(int id) {
        return resource.completeItem(id)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn);
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
