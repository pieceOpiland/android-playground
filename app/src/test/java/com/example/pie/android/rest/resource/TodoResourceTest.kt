package com.example.pie.android.rest.resource

import com.example.pie.android.rest.TodoResource
import com.example.pie.android.utility.TestSchedulerRule

import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit


import io.reactivex.Single
import org.mockito.Mockito.*

class TodoResourceTest {

    @Rule @JvmField val schedulerRule = TestSchedulerRule.rule()
    @Rule @JvmField val rule = MockitoJUnit.rule()

    @Mock private lateinit var mockApi: TodoResource.TodoApi
    @InjectMocks lateinit private var testObj: TodoResource

    @Test
    fun basicTest() {
        `when`(mockApi.items)
                .thenReturn(Single.just(emptyList()))

        val testObserver = testObj.items.test()

        testObserver.assertNotComplete()

        schedulerRule.testScheduler.triggerActions()

        testObserver.assertComplete()
                .assertNoErrors()
                .assertValue { todoItems -> todoItems.isEmpty() }

        inOrder(mockApi).verify<TodoResource.TodoApi>(mockApi, times(1)).items
    }
}
