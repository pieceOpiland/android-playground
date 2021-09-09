package com.example.pie.android.di

import com.example.pie.android.TodoApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    AppModule::class,
    ActivityModule::class])
interface ApplicationComponent: AndroidInjector<TodoApplication> {

    @Component.Factory
    abstract class Builder: AndroidInjector.Factory<TodoApplication>
}