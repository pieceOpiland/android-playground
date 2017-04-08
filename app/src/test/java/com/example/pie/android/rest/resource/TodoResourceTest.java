package com.example.pie.android.rest.resource;

import com.example.pie.android.model.TodoItem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TodoResourceTest {

    @Rule public final MockitoRule rule = MockitoJUnit.rule();
    @Mock private TodoResource.TodoApi mockApi;

    private TodoResource testObj;
    private TestScheduler scheduler;

    @Before
    public void setUp() {
        scheduler = new TestScheduler();
        testObj = new TodoResource(mockApi, scheduler, scheduler);
    }

    @Test
    public void basicTest() {
        when(mockApi.getItems())
                .thenReturn(Single.just(Collections.<TodoItem>emptyList()));

        TestObserver<List<TodoItem>> testObserver = testObj.getItems().test();

        testObserver.assertNotComplete();

        scheduler.triggerActions();

        testObserver.assertComplete()
                .assertNoErrors()
                .assertValue(new Predicate<List<TodoItem>>() {
            @Override
            public boolean test(@NonNull List<TodoItem> todoItems) throws Exception {
                return todoItems.size() == 0;
            }
        });

        inOrder(mockApi).verify(mockApi, times(1)).getItems();
    }
}
