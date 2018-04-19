package com.example.pie.android.di

import com.example.pie.android.SERVER_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun retrofit(): Retrofit =
            Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create(
                            GsonBuilder()
                                    .excludeFieldsWithoutExposeAnnotation()
                                    .create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
}