package com.allo.courtcounter.domain.interactor;

import com.allo.courtcounter.domain.base.interactor.UseCase;
import com.allo.courtcounter.domain.base.thread.ExecutorThread;
import com.allo.courtcounter.domain.base.thread.PostExecutionThread;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.domain.repository.GameRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class PreviousGamesUseCase extends UseCase<List<Game>, PreviousGamesUseCase.Params> {

    private GameRepository mGameRepository;

    @Inject
    public PreviousGamesUseCase(@ExecutorThread Scheduler threadExecutor,
                                @PostExecutionThread Scheduler threadPostExecution,
                                GameRepository gameRepository) {
        super(threadExecutor, threadPostExecution);
        this.mGameRepository = gameRepository;
    }

    @Override
    protected Observable<List<Game>> buildUseCaseObservable(Params params) {
        return mGameRepository.previousGames().toObservable();
    }

    public static class Params {

    }
}
