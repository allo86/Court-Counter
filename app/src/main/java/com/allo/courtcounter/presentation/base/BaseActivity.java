package com.allo.courtcounter.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        ButterKnife.bind(this);

        injectDependencies();

        loadViewModels();

        defineObservers();

        if (getIntent() != null && getIntent().getExtras() != null) {
            readParameters(getIntent().getExtras());
        }

        if (savedInstanceState != null) {
            restoreSavedInstanceState(savedInstanceState);
        } else {
            loadData();
        }
    }

    protected abstract @LayoutRes
    int getLayoutResourceId();

    protected abstract void injectDependencies();

    protected abstract void loadViewModels();

    protected abstract void defineObservers();

    protected abstract void readParameters(Bundle args);

    protected abstract void restoreSavedInstanceState(Bundle savedInstanceState);

    protected abstract void loadData();
}
