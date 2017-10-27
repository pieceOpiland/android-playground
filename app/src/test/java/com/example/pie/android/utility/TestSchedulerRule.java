package com.example.pie.android.utility;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

public class TestSchedulerRule implements TestRule {

    private TestScheduler testScheduler;
    @Override
    public Statement apply(final Statement base, Description description) {
        testScheduler = new TestScheduler();
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    RxJavaPlugins.setInitIoSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                        @Override
                        public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                            return testScheduler;
                        }
                    });
                    RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
                        @Override
                        public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                            return testScheduler;
                        }
                    });

                    RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                        @Override
                        public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                            return testScheduler;
                        }
                    });
                    RxAndroidPlugins.setMainThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
                        @Override
                        public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                            return testScheduler;
                        }
                    });

                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }

    public static TestSchedulerRule rule() {
        return new TestSchedulerRule();
    }

    public TestScheduler getTestScheduler() {
        return testScheduler;
    }
}
