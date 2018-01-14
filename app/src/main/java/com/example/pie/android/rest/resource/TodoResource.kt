package com.example.pie.android.rest.resource

import com.example.pie.android.model.TodoItem
import com.example.pie.android.rest.RetrofitProvider

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

class TodoResource(private val resource: TodoApi) {

    val items: Single<List<TodoItem>>
        get() = resource.items
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())


    fun addItems(items: List<TodoItem>): Single<List<TodoItem>> {
        return resource.addItems(items)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun clearDone(): Single<List<TodoItem>> {
        return resource.clearDone()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun completeItem(id: Int): Single<ResponseBody> {
        return resource.completeItem(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    interface TodoApi {
        @get:GET("/rest/todo")
        val items: Single<List<TodoItem>>

        @POST("/rest/todo")
        fun addItems(@Body items: List<TodoItem>): Single<List<TodoItem>>

        @DELETE("/rest/todo")
        fun clearDone(): Single<List<TodoItem>>

        @PUT("/rest/todo/{id}")
        fun completeItem(@Path("id") id: Int): Single<ResponseBody>
    }
}
