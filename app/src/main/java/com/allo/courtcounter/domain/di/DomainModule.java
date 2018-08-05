package com.allo.courtcounter.domain.di;

import com.allo.courtcounter.domain.base.thread.ExecutorThread;
import com.allo.courtcounter.domain.base.thread.PostExecutionThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class DomainModule {

    public DomainModule() {

    }

    @Provides
    @Singleton
    @ExecutorThread
    Scheduler provideExecutorThread() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @PostExecutionThread
    Scheduler providePostExecutionThread() {
        return AndroidSchedulers.mainThread();
    }
}
