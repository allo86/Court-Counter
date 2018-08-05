package com.allo.courtcounter.presentation.di;

import com.allo.courtcounter.data.di.DataModule;
import com.allo.courtcounter.domain.di.DomainModule;
import com.allo.courtcounter.presentation.scenes.create.CreateGameActivity;
import com.allo.courtcounter.presentation.scenes.details.GameDetailsActivity;
import com.allo.courtcounter.presentation.scenes.list.PreviousGamesActivity;
import com.allo.courtcounter.presentation.scenes.splash.SplashActivity;
import com.allo.courtcounter.presentation.scenes.summary.GameSummaryActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataModule.class, DomainModule.class, ViewModelModule.class})
public interface ApplicationComponent {

    void inject(SplashActivity splashActivity);

    void inject(CreateGameActivity createGameActivity);

    void inject(GameDetailsActivity gameDetailsActivity);

    void inject(GameSummaryActivity gameSummaryActivity);

    void inject(PreviousGamesActivity previousGamesActivity);
}
