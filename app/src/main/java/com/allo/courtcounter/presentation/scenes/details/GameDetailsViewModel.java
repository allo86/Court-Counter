package com.allo.courtcounter.presentation.scenes.details;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.allo.courtcounter.domain.interactor.AddPointsToTeamUseCase;
import com.allo.courtcounter.domain.interactor.EndGameUseCase;
import com.allo.courtcounter.domain.interactor.GameDetailsUseCase;
import com.allo.courtcounter.domain.interactor.ResetGameUseCase;
import com.allo.courtcounter.domain.interactor.SubtractPointsToTeamUseCase;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.domain.model.GameStatus;
import com.allo.courtcounter.presentation.model.GameModel;
import com.allo.courtcounter.presentation.model.mapper.DomainToPresentationMapper;
import com.allo.courtcounter.presentation.util.SingleLiveEvent;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class GameDetailsViewModel extends ViewModel {

    private GameDetailsUseCase mGameDetailsUseCase;
    private ResetGameUseCase mResetGameUseCase;
    private EndGameUseCase mEndGameUseCase;
    private AddPointsToTeamUseCase mAddPointsToTeamUseCase;
    private SubtractPointsToTeamUseCase mSubtractPointsToTeamUseCase;
    private DomainToPresentationMapper mDomainToPresentationMapper;

    private final MutableLiveData<GameModel> mGameDetailsEvent = new MutableLiveData<>();
    private final SingleLiveEvent<String> mErrorEvent = new SingleLiveEvent<>();

    private final SingleLiveEvent<Boolean> mGameEnded = new SingleLiveEvent<>();

    @Inject
    public GameDetailsViewModel(GameDetailsUseCase gameDetailsUseCase,
                                ResetGameUseCase resetGameUseCase,
                                EndGameUseCase endGameUseCase,
                                AddPointsToTeamUseCase addPointsToTeamUseCase,
                                SubtractPointsToTeamUseCase subtractPointsToTeamUseCase,
                                DomainToPresentationMapper domainToPresentationMapper) {
        this.mGameDetailsUseCase = gameDetailsUseCase;
        this.mResetGameUseCase = resetGameUseCase;
        this.mEndGameUseCase = endGameUseCase;
        this.mAddPointsToTeamUseCase = addPointsToTeamUseCase;
        this.mSubtractPointsToTeamUseCase = subtractPointsToTeamUseCase;
        this.mDomainToPresentationMapper = domainToPresentationMapper;
    }

    public void loadGameDetails(Long gameId) {
        mGameDetailsUseCase.execute(getGameObserver(), new GameDetailsUseCase.Params(gameId));
    }

    public void resetGame(Long gameId) {
        mResetGameUseCase.execute(getGameObserver(), new ResetGameUseCase.Params(gameId));
    }

    public void endGame(Long gameId) {
        mEndGameUseCase.execute(getGameObserver(), new EndGameUseCase.Params(gameId));
    }

    public void addPointsToTeam(Long gameId, Long teamId, Long points) {
        mAddPointsToTeamUseCase.execute(getGameObserver(), new AddPointsToTeamUseCase.Params(gameId, teamId, points));
    }

    public void subtractPointsToTeam(Long gameId, Long teamId, Long points) {
        mSubtractPointsToTeamUseCase.execute(getGameObserver(), new SubtractPointsToTeamUseCase.Params(gameId, teamId, points));
    }

    public MutableLiveData<GameModel> getGameDetailsEvent() {
        return mGameDetailsEvent;
    }

    public SingleLiveEvent<String> getErrorEvent() {
        return mErrorEvent;
    }

    public SingleLiveEvent<Boolean> getGameEnded() {
        return mGameEnded;
    }

    private DisposableObserver<Game> getGameObserver() {
        return new DisposableObserver<Game>() {
            @Override
            public void onNext(Game game) {
                mGameDetailsEvent.setValue(mDomainToPresentationMapper.transform(game));
                mGameEnded.setValue(game.getStatus() == GameStatus.FINISHED);
            }

            @Override
            public void onError(Throwable e) {
                mErrorEvent.setValue(e.getMessage());
            }

            @Override
            public void onComplete() {
                // Do nothing for now
            }
        };
    }
}
