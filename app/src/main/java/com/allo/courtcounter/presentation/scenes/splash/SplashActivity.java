package com.allo.courtcounter.presentation.scenes.splash;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.allo.courtcounter.R;
import com.allo.courtcounter.presentation.CourtCounterApplication;
import com.allo.courtcounter.presentation.base.BaseActivity;
import com.allo.courtcounter.presentation.model.GameModel;
import com.allo.courtcounter.presentation.scenes.create.CreateGameActivity;
import com.allo.courtcounter.presentation.scenes.details.GameDetailsActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SplashViewModel mSplashViewModel;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void injectDependencies() {
        ((CourtCounterApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected void loadViewModels() {
        mSplashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel.class);
    }

    @Override
    protected void defineObservers() {
        mSplashViewModel.getGameModelLiveData().observe(this, new Observer<GameModel>() {
            @Override
            public void onChanged(@Nullable GameModel gameModel) {
                if (gameModel != null) {
                    // Game already started
                    goToGameDetails(gameModel.getId());
                } else {
                    // No ongoing game
                    goToCreateGame();
                }
            }
        });
    }

    @Override
    protected void readParameters(Bundle args) {

    }

    @Override
    protected void restoreSavedInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        mSplashViewModel.loadCurrentGame();
    }

    private void goToGameDetails(Long gameId) {
        startActivity(GameDetailsActivity.getCallingIntent(this, gameId));
    }

    private void goToCreateGame() {
        startActivity(CreateGameActivity.getCallingIntent(this));
    }
}
