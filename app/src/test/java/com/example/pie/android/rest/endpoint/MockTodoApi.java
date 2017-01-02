package com.example.pie.android.rest.endpoint;

import com.example.pie.android.model.TodoItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;

public class MockTodoApi implements TodoApi {

    private BehaviorDelegate<TodoApi> delegate;

    public MockTodoApi(BehaviorDelegate<TodoApi> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<List<TodoItem>> getItems() {
        return delegate.returning(null).getItems();
    }

    @Override
    public Call<TodoItem> addItem(@Body TodoItem item) {
        return delegate.returning(null).addItem(null);
    }

    @Override
    public Call<List<TodoItem>> addItems(@Body List<TodoItem> items) {
        return delegate.returning(null).addItems(null);
    }

    @Override
    public Call<List<TodoItem>> clearDone() {
        return delegate.returning(null).clearDone();
    }

    @Override
    public Call<ResponseBody> completeItem(@Path("id") int id) {
        return delegate.returning(null).completeItem(0);
    }
}
