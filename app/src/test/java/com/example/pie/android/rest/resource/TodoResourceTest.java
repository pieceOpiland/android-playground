package com.example.pie.android.rest.resource;

import com.example.pie.android.model.TodoItem;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
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

        testObj.getItems();
        inOrder(mockApi).verify(mockApi, times(1)).getItems();
    }
}
