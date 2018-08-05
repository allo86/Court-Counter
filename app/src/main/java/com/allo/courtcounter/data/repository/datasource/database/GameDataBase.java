package com.allo.courtcounter.data.repository.datasource.database;

import com.allo.courtcounter.data.model.Constants;
import com.allo.courtcounter.data.model.GameEntity;
import com.allo.courtcounter.data.model.GameEntity_Table;
import com.allo.courtcounter.data.model.TeamEntity;
import com.allo.courtcounter.data.repository.datasource.GameDataSource;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

@Database(name = GameDataBase.NAME, version = GameDataBase.VERSION)
public class GameDataBase implements GameDataSource {

    public static final String NAME = "GameDataBase"; // we will add the .db extension
    public static final int VERSION = 1;

    @Inject
    public GameDataBase() {

    }

    @Override
    public Single<GameEntity> currentGame() {
        return RXSQLite.rx(SQLite.select()
                .from(GameEntity.class)
                .where(GameEntity_Table.status.eq(Constants.GameStatusEntity.ONGOING)))
                .querySingle().toSingle();
    }

    @Override
    public Single<GameEntity> gameDetails(Long gameId) {
        return RXSQLite.rx(SQLite.select()
                .from(GameEntity.class)
                .where(GameEntity_Table.gameId.eq(gameId)))
                .querySingle().toSingle();
    }

    @Override
    public Single<GameEntity> startGame(String localTeam, String awayTeam) {
        TeamEntity local = new TeamEntity();
        local.setPoints(0L);
        local.setName(localTeam);

        TeamEntity away = new TeamEntity();
        away.setPoints(0L);
        away.setName(awayTeam);

        GameEntity game = new GameEntity();
        game.setLocalTeam(local);
        game.setAwayTeam(away);
        game.setStatus(Constants.GameStatusEntity.ONGOING);

        local.save();
        away.save();
        game.save();

        return currentGame();
    }

    @Override
    public Single<GameEntity> endGame(Long gameId) {
        return gameDetails(gameId)
                .map(new Function<GameEntity, GameEntity>() {
                    @Override
                    public GameEntity apply(GameEntity gameEntity) throws Exception {
                        gameEntity.setStatus(Constants.GameStatusEntity.FINISHED);
                        gameEntity.save();
                        return gameEntity;
                    }
                });
    }

    @Override
    public Single<GameEntity> resetGame(Long gameId) {
        return gameDetails(gameId)
                .map(new Function<GameEntity, GameEntity>() {
                    @Override
                    public GameEntity apply(GameEntity gameEntity) throws Exception {
                        gameEntity.getLocalTeam().setPoints(0L);
                        gameEntity.getLocalTeam().save();

                        gameEntity.getAwayTeam().setPoints(0L);
                        gameEntity.getAwayTeam().save();

                        gameEntity.save();

                        return gameEntity;
                    }
                });
    }

    @Override
    public Single<GameEntity> addPoints(Long gameId, final Long teamId, final Long points) {
        return gameDetails(gameId)
                .map(new Function<GameEntity, GameEntity>() {
                    @Override
                    public GameEntity apply(GameEntity gameEntity) throws Exception {
                        TeamEntity localTeam = gameEntity.getLocalTeam();
                        if (localTeam.getTeamId().equals(teamId)) {
                            localTeam.setPoints(localTeam.getPoints() + points);
                            localTeam.save();
                        } else {
                            TeamEntity awayTeam = gameEntity.getAwayTeam();
                            awayTeam.setPoints(awayTeam.getPoints() + points);
                            awayTeam.save();
                        }
                        gameEntity.save();
                        return gameEntity;
                    }
                });
    }

    @Override
    public Single<GameEntity> subtractPoints(Long gameId, final Long teamId, final Long points) {
        return gameDetails(gameId)
                .map(new Function<GameEntity, GameEntity>() {
                    @Override
                    public GameEntity apply(GameEntity gameEntity) throws Exception {
                        TeamEntity localTeam = gameEntity.getLocalTeam();
                        if (localTeam.getTeamId().equals(teamId)) {
                            localTeam.setPoints(localTeam.getPoints() - points);
                            localTeam.save();
                        } else {
                            TeamEntity awayTeam = gameEntity.getAwayTeam();
                            awayTeam.setPoints(localTeam.getPoints() - points);
                            awayTeam.save();
                        }
                        gameEntity.save();
                        return gameEntity;
                    }
                });
    }

    @Override
    public Single<List<GameEntity>> previousGames() {
        return RXSQLite.rx(SQLite.select()
                .from(GameEntity.class)
                .where(GameEntity_Table.status.eq(Constants.GameStatusEntity.FINISHED)))
                .queryList();
    }
}
