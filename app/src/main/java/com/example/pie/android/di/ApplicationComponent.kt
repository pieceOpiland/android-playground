package com.example.pie.android.di

import com.example.pie.android.TodoApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityModule::class])
interface ApplicationComponent: AndroidInjector<TodoApplication> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<TodoApplication>()
}