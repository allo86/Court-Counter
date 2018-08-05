package com.allo.courtcounter.presentation.scenes.splash;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.allo.courtcounter.domain.interactor.CurrentGameUseCase;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.presentation.model.GameModel;
import com.allo.courtcounter.presentation.model.mapper.DomainToPresentationMapper;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class SplashViewModel extends ViewModel {

    private CurrentGameUseCase mCurrentGameUseCase;
    private DomainToPresentationMapper mDomainToPresentationMapper;

    private final MutableLiveData<GameModel> mGameModelLiveData = new MutableLiveData<>();

    @Inject
    public SplashViewModel(CurrentGameUseCase currentGameUseCase,
                           DomainToPresentationMapper domainToPresentationMapper) {
        this.mCurrentGameUseCase = currentGameUseCase;
        this.mDomainToPresentationMapper = domainToPresentationMapper;
    }

    public void loadCurrentGame() {
        this.mCurrentGameUseCase.execute(new DisposableObserver<Game>() {
            @Override
            public void onNext(Game game) {
                mGameModelLiveData.setValue(mDomainToPresentationMapper.transform(game));
            }

            @Override
            public void onError(Throwable e) {
                // No game available
                mGameModelLiveData.postValue(null);
            }

            @Override
            public void onComplete() {
                // Do nothing for now
            }
        }, new CurrentGameUseCase.Params());
    }

    public MutableLiveData<GameModel> getGameModelLiveData() {
        return mGameModelLiveData;
    }
}
