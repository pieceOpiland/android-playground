package com.example.pie.android.rest.resource;

import com.example.pie.android.model.TodoItem;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TodoResourceTest {

    private TodoResource testObj;
    private TodoResource.TodoApi mockApi;

    @Before
    public void setUp() {
        mockApi = mock(TodoResource.TodoApi.class);
        testObj = new TodoResource(mockApi, Schedulers.trampoline(), Schedulers.trampoline());
    }

    @Test
    public void basicTest() {
        when(mockApi.getItems())
                .thenReturn(Single.just(Collections.<TodoItem>emptyList()));

        testObj.getItems().subscribe(new Consumer<List<TodoItem>>() {
            @Override
            public void accept(@NonNull List<TodoItem> todoItems) throws Exception {
                assertEquals(0, todoItems.size());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                fail();
            }
        });
    }
}
