package com.allo.courtcounter.domain.interactor;

import com.allo.courtcounter.domain.base.interactor.UseCase;
import com.allo.courtcounter.domain.base.thread.ExecutorThread;
import com.allo.courtcounter.domain.base.thread.PostExecutionThread;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.domain.repository.GameRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class EndGameUseCase extends UseCase<Game, EndGameUseCase.Params> {

    private GameRepository mGameRepository;

    @Inject
    public EndGameUseCase(@ExecutorThread Scheduler threadExecutor,
                          @PostExecutionThread Scheduler threadPostExecution,
                          GameRepository gameRepository) {
        super(threadExecutor, threadPostExecution);
        this.mGameRepository = gameRepository;
    }

    @Override
    protected Observable<Game> buildUseCaseObservable(Params params) {
        return mGameRepository.endGame(params.getGameId()).toObservable();
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
