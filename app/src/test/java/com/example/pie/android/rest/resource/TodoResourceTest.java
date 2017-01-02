package com.example.pie.android.rest.resource;

import com.example.pie.android.rest.RetrofitProvider;
import com.example.pie.android.rest.endpoint.MockTodoApi;
import com.example.pie.android.rest.endpoint.TodoApi;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;

public class TodoResourceTest {

    private static Retrofit initial;
    private static Retrofit mock;
    private static MockRetrofit mockRetrofit;

    @BeforeClass
    public static void setUpRetrofit() {
        mock = new Retrofit.Builder().build();
        mockRetrofit = new MockRetrofit.Builder(mock).build();
        initial = RetrofitProvider.getInstance().setRetrofit(mock);
    }

    public void setUp() {
        BehaviorDelegate<TodoApi> delegate = mockRetrofit.create(TodoApi.class);
        MockTodoApi mock = new MockTodoApi(delegate);
    }

    @AfterClass
    public static void tearDownRetrofit() {
        RetrofitProvider.getInstance().setRetrofit(initial);
    }
}
