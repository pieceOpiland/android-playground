package com.example.pie.android

import com.example.pie.android.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TodoApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<TodoApplication> =
            DaggerApplicationComponent.factory().create(this)
}