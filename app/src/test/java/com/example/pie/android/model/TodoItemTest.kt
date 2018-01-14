package com.example.pie.android.model

import org.junit.Before
import org.junit.Test

import junit.framework.Assert.*

class TodoItemTest {

    private lateinit var testObj: TodoItem

    @Before
    fun setUp() {
        testObj = TodoItem()
    }

    @Test
    fun testGettersAndSetters() {
        val id = 23
        val task = "Do android testing"
        val isDone = false

        testObj.id = id
        testObj.task = task
        testObj.done = isDone

        assertEquals(isDone, testObj.done)
        assertEquals(id, testObj.id)
        assertEquals(task, testObj.task)
    }
}
