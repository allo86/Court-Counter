package com.allo.courtcounter.presentation.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.allo.courtcounter.presentation.base.ViewModelFactory;
import com.allo.courtcounter.presentation.base.ViewModelKey;
import com.allo.courtcounter.presentation.scenes.create.CreateGameViewModel;
import com.allo.courtcounter.presentation.scenes.details.GameDetailsViewModel;
import com.allo.courtcounter.presentation.scenes.list.PreviousGamesViewModel;
import com.allo.courtcounter.presentation.scenes.splash.SplashViewModel;
import com.allo.courtcounter.presentation.scenes.summary.GameSummaryViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    public abstract ViewModel bindSplashViewModel(SplashViewModel splashViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GameDetailsViewModel.class)
    public abstract ViewModel bindGameDetailsViewModel(GameDetailsViewModel gameDetailsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CreateGameViewModel.class)
    public abstract ViewModel bindCreateGameViewModel(CreateGameViewModel createGameViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GameSummaryViewModel.class)
    public abstract ViewModel bindGameSummaryViewModel(GameSummaryViewModel gameSummaryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PreviousGamesViewModel.class)
    public abstract ViewModel bindPreviousGamesViewModel(PreviousGamesViewModel previousGamesViewModel);
}
