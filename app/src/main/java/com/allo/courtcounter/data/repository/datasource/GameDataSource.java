package com.allo.courtcounter.data.repository.datasource;

import com.allo.courtcounter.data.model.GameEntity;

import java.util.List;

import io.reactivex.Single;

public interface GameDataSource {

    Single<GameEntity> currentGame();

    Single<GameEntity> gameDetails(Long gameId);

    Single<GameEntity> startGame(String localTeam, String awayTeam);

    Single<GameEntity> endGame(Long gameId);

    Single<GameEntity> resetGame(Long gameId);

    Single<GameEntity> addPoints(Long gameId, Long teamId, Long points);

    Single<GameEntity> subtractPoints(Long gameId, Long teamId, Long points);

    Single<List<GameEntity>> previousGames();
}
