package com.allo.courtcounter.domain.interactor;

import com.allo.courtcounter.domain.base.interactor.UseCase;
import com.allo.courtcounter.domain.base.thread.ExecutorThread;
import com.allo.courtcounter.domain.base.thread.PostExecutionThread;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.domain.repository.GameRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GameDetailsUseCase extends UseCase<Game, GameDetailsUseCase.Params> {

    private GameRepository mGameRepository;

    @Inject
    public GameDetailsUseCase(@ExecutorThread Scheduler threadExecutor,
                              @PostExecutionThread Scheduler threadPostExecution,
                              GameRepository gameRepository) {
        super(threadExecutor, threadPostExecution);
        this.mGameRepository = gameRepository;
    }

    @Override
    protected Observable<Game> buildUseCaseObservable(Params params) {
        return mGameRepository.gameDetails(params.getGameId()).toObservable();
    }

    public static class Params {
        private Long mGameId;

        public Params(Long gameId) {
            this.mGameId = gameId;
        }

        public Long getGameId() {
            return mGameId;
        }
    }
}
