package com.allo.courtcounter.domain.interactor;

import com.allo.courtcounter.domain.base.interactor.UseCase;
import com.allo.courtcounter.domain.base.thread.ExecutorThread;
import com.allo.courtcounter.domain.base.thread.PostExecutionThread;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.domain.repository.GameRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class StartGameUseCase extends UseCase<Game, StartGameUseCase.Params> {

    private GameRepository mGameRepository;

    @Inject
    public StartGameUseCase(@ExecutorThread Scheduler threadExecutor,
                            @PostExecutionThread Scheduler threadPostExecution,
                            GameRepository gameRepository) {
        super(threadExecutor, threadPostExecution);
        this.mGameRepository = gameRepository;
    }

    @Override
    protected Observable<Game> buildUseCaseObservable(Params params) {
        return mGameRepository.startGame(params.getLocalTeam(), params.getAwayTeam()).toObservable();
    }

    public static class Params {

        private String mLocalTeam;
        private String mAwayTeam;

        public Params(String localTeam, String awayTeam) {
            this.mLocalTeam = localTeam;
            this.mAwayTeam = awayTeam;
        }

        public String getLocalTeam() {
            return mLocalTeam;
        }

        public String getAwayTeam() {
            return mAwayTeam;
        }
    }
}
