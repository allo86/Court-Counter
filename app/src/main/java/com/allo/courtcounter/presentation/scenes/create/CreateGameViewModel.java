package com.allo.courtcounter.presentation.scenes.create;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.allo.courtcounter.domain.interactor.StartGameUseCase;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.presentation.model.GameModel;
import com.allo.courtcounter.presentation.model.mapper.DomainToPresentationMapper;
import com.allo.courtcounter.presentation.util.SingleLiveEvent;
import com.allo.courtcounter.presentation.util.ValidationException;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class CreateGameViewModel extends ViewModel {

    private StartGameUseCase mStartGameUseCase;
    private DomainToPresentationMapper mDomainToPresentationMapper;

    private MutableLiveData<Boolean> mEnableCreateGameButton = new MutableLiveData<>();
    private final SingleLiveEvent<GameModel> mGameDetailsEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mErrorEvent = new SingleLiveEvent<>();

    private String mLocalTeam;
    private String mAwayTeam;

    @Inject
    public CreateGameViewModel(StartGameUseCase startGameUseCase,
                               DomainToPresentationMapper domainToPresentationMapper) {
        this.mStartGameUseCase = startGameUseCase;
        this.mDomainToPresentationMapper = domainToPresentationMapper;
    }

    public void startGame() {
        mStartGameUseCase.execute(new DisposableObserver<Game>() {
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
                // Do nothing for now
            }
        }, new StartGameUseCase.Params(mLocalTeam, mAwayTeam));
    }

    public void setLocalTeam(String localTeam) {
        mLocalTeam = localTeam;
        enableCreateGameButton();
    }

    public void setAwayTeam(String awayTeam) {
        mAwayTeam = awayTeam;
        enableCreateGameButton();
    }

    public MutableLiveData<Boolean> getEnableCreateGameButton() {
        return mEnableCreateGameButton;
    }

    public SingleLiveEvent<GameModel> getGameDetailsEvent() {
        return mGameDetailsEvent;
    }

    public SingleLiveEvent<String> getErrorEvent() {
        return mErrorEvent;
    }

    private void enableCreateGameButton() {
        try {
            if (mLocalTeam == null || "".equals(mLocalTeam.trim())) {
                throw new ValidationException();
            }
            if (mAwayTeam == null || "".equals(mAwayTeam.trim())) {
                throw new ValidationException();
            }

            mEnableCreateGameButton.postValue(true);
        } catch (ValidationException ex) {
            mEnableCreateGameButton.postValue(false);
        }
    }
}
