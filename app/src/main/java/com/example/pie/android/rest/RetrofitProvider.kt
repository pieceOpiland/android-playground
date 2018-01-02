package com.example.pie.android.rest

import com.example.pie.android.BuildConfig
import com.google.gson.GsonBuilder

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider private constructor() {

    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        GsonBuilder()
                                .excludeFieldsWithoutExposeAnnotation()
                                .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    companion object {

        private val BASE_URL = BuildConfig.SERVER_URL

        val instance = RetrofitProvider()
    }
}
