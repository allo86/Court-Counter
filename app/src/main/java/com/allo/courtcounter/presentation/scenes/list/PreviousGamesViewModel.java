package com.allo.courtcounter.presentation.scenes.list;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.allo.courtcounter.domain.interactor.PreviousGamesUseCase;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.presentation.model.GameModel;
import com.allo.courtcounter.presentation.model.mapper.DomainToPresentationMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class PreviousGamesViewModel extends ViewModel {

    private PreviousGamesUseCase mPreviousGamesUseCase;
    private DomainToPresentationMapper mDomainToPresentationMapper;

    private MutableLiveData<List<GameModel>> mGames = new MutableLiveData<>();

    @Inject
    public PreviousGamesViewModel(PreviousGamesUseCase previousGamesUseCase,
                                  DomainToPresentationMapper domainToPresentationMapper) {
        this.mPreviousGamesUseCase = previousGamesUseCase;
        this.mDomainToPresentationMapper = domainToPresentationMapper;
    }

    public void loadGames() {
        this.mPreviousGamesUseCase.execute(new DisposableObserver<List<Game>>() {
            @Override
            public void onNext(List<Game> games) {
                List<GameModel> models = new ArrayList<>();
                for (Game game : games) {
                    models.add(mDomainToPresentationMapper.transform(game));
                }
                mGames.postValue(models);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, new PreviousGamesUseCase.Params());
    }

    public MutableLiveData<List<GameModel>> getGames() {
        return mGames;
    }
}
