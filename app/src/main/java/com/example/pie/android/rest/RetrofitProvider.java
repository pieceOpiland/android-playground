package com.example.pie.android.rest;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static final String BASE_URL = "http://limitless-everglades-64303.herokuapp.com/";

    private static RetrofitProvider instance = new RetrofitProvider();

    private Retrofit retrofit;

    private RetrofitProvider() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .excludeFieldsWithoutExposeAnnotation()
                                .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RetrofitProvider getInstance() {
        return instance;
    }

    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }
}
