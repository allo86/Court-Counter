package com.allo.courtcounter.data.di;

import android.content.Context;

import com.allo.courtcounter.data.repository.GameDataRepository;
import com.allo.courtcounter.data.repository.datasource.GameDataSource;
import com.allo.courtcounter.data.repository.datasource.database.GameDataBase;
import com.allo.courtcounter.domain.repository.GameRepository;
import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    public DataModule(Context context) {
        FlowManager.init(context);
    }

    @Provides
    @Singleton
    GameRepository provideRepository(GameDataRepository gameDataRepository) {
        return gameDataRepository;
    }

    @Provides
    @Singleton
    GameDataSource provideDataSource(GameDataBase gameDataBase) {
        return gameDataBase;
    }
}
