package com.allo.courtcounter.data.repository;

import com.allo.courtcounter.data.model.GameEntity;
import com.allo.courtcounter.data.model.mapper.DataToDomainMapper;
import com.allo.courtcounter.data.repository.datasource.GameDataSource;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.domain.repository.GameRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class GameDataRepository implements GameRepository {

    private GameDataSource mDataSource;
    private DataToDomainMapper mDataToDomainMapper;

    @Inject
    public GameDataRepository(GameDataSource dataSource,
                              DataToDomainMapper dataToDomainMapper) {
        this.mDataSource = dataSource;
        this.mDataToDomainMapper = dataToDomainMapper;
    }

    @Override
    public Single<Game> currentGame() {
        return mDataSource.currentGame().map(new Function<GameEntity, Game>() {
            @Override
            public Game apply(GameEntity gameEntity) throws Exception {
                return mDataToDomainMapper.transform(gameEntity);
            }
        });
    }

    @Override
    public Single<Game> gameDetails(Long gameId) {
        return mDataSource.gameDetails(gameId).map(new Function<GameEntity, Game>() {
            @Override
            public Game apply(GameEntity gameEntity) throws Exception {
                return mDataToDomainMapper.transform(gameEntity);
            }
        });
    }

    @Override
    public Single<Game> startGame(String localTeam, String awayTeam) {
        return mDataSource.startGame(localTeam, awayTeam).map(new Function<GameEntity, Game>() {
            @Override
            public Game apply(GameEntity gameEntity) throws Exception {
                return mDataToDomainMapper.transform(gameEntity);
            }
        });
    }

    @Override
    public Single<Game> endGame(Long gameId) {
        return mDataSource.endGame(gameId).map(new Function<GameEntity, Game>() {
            @Override
            public Game apply(GameEntity gameEntity) throws Exception {
                return mDataToDomainMapper.transform(gameEntity);
            }
        });
    }

    @Override
    public Single<Game> resetGame(Long gameId) {
        return mDataSource.resetGame(gameId).map(new Function<GameEntity, Game>() {
            @Override
            public Game apply(GameEntity gameEntity) throws Exception {
                return mDataToDomainMapper.transform(gameEntity);
            }
        });
    }

    @Override
    public Single<Game> addPoints(Long gameId, Long teamId, Long points) {
        return mDataSource.addPoints(gameId, teamId, points).map(new Function<GameEntity, Game>() {
            @Override
            public Game apply(GameEntity gameEntity) throws Exception {
                return mDataToDomainMapper.transform(gameEntity);
            }
        });
    }

    @Override
    public Single<Game> subtractPoints(Long gameId, Long teamId, Long points) {
        return mDataSource.subtractPoints(gameId, teamId, points).map(new Function<GameEntity, Game>() {
            @Override
            public Game apply(GameEntity gameEntity) throws Exception {
                return mDataToDomainMapper.transform(gameEntity);
            }
        });
    }

    @Override
    public Single<List<Game>> previousGames() {
        return mDataSource.previousGames().map(new Function<List<GameEntity>, List<Game>>() {
            @Override
            public List<Game> apply(List<GameEntity> gameEntities) throws Exception {
                List<Game> games = new ArrayList<>();
                for (GameEntity entity : gameEntities) {
                    games.add(mDataToDomainMapper.transform(entity));
                }
                return games;
            }
        });
    }
}
