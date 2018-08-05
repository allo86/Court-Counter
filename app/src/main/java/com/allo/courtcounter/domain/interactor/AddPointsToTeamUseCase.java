package com.allo.courtcounter.domain.interactor;

import com.allo.courtcounter.domain.base.interactor.UseCase;
import com.allo.courtcounter.domain.base.thread.ExecutorThread;
import com.allo.courtcounter.domain.base.thread.PostExecutionThread;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.domain.repository.GameRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class AddPointsToTeamUseCase extends UseCase<Game, AddPointsToTeamUseCase.Params> {

    private GameRepository mGameRepository;

    @Inject
    public AddPointsToTeamUseCase(@ExecutorThread Scheduler threadExecutor,
                                  @PostExecutionThread Scheduler threadPostExecution,
                                  GameRepository gameRepository) {
        super(threadExecutor, threadPostExecution);
        this.mGameRepository = gameRepository;
    }

    @Override
    protected Observable<Game> buildUseCaseObservable(Params params) {
        return mGameRepository.addPoints(params.getGameId(), params.getTeamId(), params.getPoints()).toObservable();
    }

    public static class Params {
        private Long mGameId;
        private Long mTeamId;
        private Long mPoints;

        public Params(Long gameId, Long teamId, Long points) {
            mGameId = gameId;
            mTeamId = teamId;
            mPoints = points;
        }

        public Long getGameId() {
            return mGameId;
        }

        public Long getTeamId() {
            return mTeamId;
        }

        public Long getPoints() {
            return mPoints;
        }
    }
}
