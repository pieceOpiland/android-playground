package com.example.pie.android.utility

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler

class TestSchedulerRule : TestRule {

    lateinit var testScheduler: TestScheduler
        private set

    override fun apply(base: Statement, description: Description): Statement {
        testScheduler = TestScheduler()
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                try {
                    RxJavaPlugins.setInitIoSchedulerHandler { testScheduler }
                    RxJavaPlugins.setIoSchedulerHandler { testScheduler }

                    RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }
                    RxAndroidPlugins.setMainThreadSchedulerHandler { testScheduler }

                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }

    companion object {
        fun rule(): TestSchedulerRule {
            return TestSchedulerRule()
        }
    }
}
