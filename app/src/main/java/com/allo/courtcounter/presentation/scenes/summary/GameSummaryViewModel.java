package com.allo.courtcounter.presentation.scenes.summary;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.allo.courtcounter.domain.interactor.GameDetailsUseCase;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.presentation.model.GameModel;
import com.allo.courtcounter.presentation.model.mapper.DomainToPresentationMapper;
import com.allo.courtcounter.presentation.util.SingleLiveEvent;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class GameSummaryViewModel extends ViewModel {

    private GameDetailsUseCase mGameDetailsUseCase;
    private DomainToPresentationMapper mDomainToPresentationMapper;

    private final MutableLiveData<GameModel> mGameDetailsEvent = new MutableLiveData<>();
    private final SingleLiveEvent<String> mErrorEvent = new SingleLiveEvent<>();

    @Inject
    public GameSummaryViewModel(GameDetailsUseCase gameDetailsUseCase,
                                DomainToPresentationMapper domainToPresentationMapper) {
        this.mGameDetailsUseCase = gameDetailsUseCase;
        this.mDomainToPresentationMapper = domainToPresentationMapper;
    }

    public void loadGameSummary(Long gameId) {
        mGameDetailsUseCase.execute(new DisposableObserver<Game>() {
            @Override
            public void onNext(Game game) {
                mGameDetailsEvent.setValue(mDomainToPresentationMapper.transform(game));
            }

            @Override
            public void onError(Throwable e) {
                mErrorEvent.setValue(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, new GameDetailsUseCase.Params(gameId));
    }

    public MutableLiveData<GameModel> getGameDetailsEvent() {
        return mGameDetailsEvent;
    }

    public SingleLiveEvent<String> getErrorEvent() {
        return mErrorEvent;
    }
}
