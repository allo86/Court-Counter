package com.allo.courtcounter.domain.interactor;

import com.allo.courtcounter.domain.base.interactor.UseCase;
import com.allo.courtcounter.domain.base.thread.ExecutorThread;
import com.allo.courtcounter.domain.base.thread.PostExecutionThread;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.domain.repository.GameRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class CurrentGameUseCase extends UseCase<Game, CurrentGameUseCase.Params> {

    private GameRepository mGameRepository;

    @Inject
    public CurrentGameUseCase(@ExecutorThread Scheduler threadExecutor,
                              @PostExecutionThread Scheduler threadPostExecution,
                              GameRepository gameRepository) {
        super(threadExecutor, threadPostExecution);
        this.mGameRepository = gameRepository;
    }

    @Override
    protected Observable<Game> buildUseCaseObservable(Params params) {
        return mGameRepository.currentGame().toObservable();
    }

    public static class Params {

    }
}
