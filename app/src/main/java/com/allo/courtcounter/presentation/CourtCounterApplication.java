package com.allo.courtcounter.presentation;

import android.app.Application;

import com.allo.courtcounter.data.di.DataModule;
import com.allo.courtcounter.domain.di.DomainModule;
import com.allo.courtcounter.presentation.di.ApplicationComponent;
import com.allo.courtcounter.presentation.di.DaggerApplicationComponent;

public class CourtCounterApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.mApplicationComponent = DaggerApplicationComponent.builder()
                .domainModule(new DomainModule())
                .dataModule(new DataModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
