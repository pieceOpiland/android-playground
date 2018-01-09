package com.example.pie.android.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

public class TodoItemTest {

    private TodoItem testObj;

    @Before
    public void setUp() {
        testObj = new TodoItem();
    }

    @Test
    public void testGettersAndSetters() {
        int id = 23;
        String task = "Do android testing";
        boolean isDone = false;

        testObj.setId(id);
        testObj.setTask(task);
        testObj.setDone(isDone);

        assertEquals(isDone, testObj.getDone());
        assertEquals(id, testObj.getId());
        assertEquals(task, testObj.getTask());
    }
}
