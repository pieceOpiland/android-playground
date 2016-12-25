package com.example.pie.android.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

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

        Assert.assertEquals(isDone, testObj.isDone());
        Assert.assertEquals(id, testObj.getId());
        Assert.assertEquals(task, testObj.getTask());
    }
}
