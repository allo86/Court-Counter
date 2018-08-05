package com.allo.courtcounter.domain.repository;

import com.allo.courtcounter.domain.model.Game;

import java.util.List;

import io.reactivex.Single;

public interface GameRepository {

    Single<Game> currentGame();

    Single<Game> gameDetails(Long gameId);

    Single<Game> startGame(String localTeam, String awayTeam);

    Single<Game> endGame(Long gameId);

    Single<Game> resetGame(Long gameId);

    Single<Game> addPoints(Long gameId, Long teamId, Long points);

    Single<Game> subtractPoints(Long gameId, Long teamId, Long points);

    Single<List<Game>> previousGames();
}
