package com.allo.courtcounter.data.model.mapper;

import com.allo.courtcounter.data.model.Constants;
import com.allo.courtcounter.data.model.GameEntity;
import com.allo.courtcounter.data.model.TeamEntity;
import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.domain.model.GameStatus;
import com.allo.courtcounter.domain.model.Team;

import javax.inject.Inject;

public class DataToDomainMapper {

    @Inject
    public DataToDomainMapper() {

    }

    public Game transform(GameEntity entity) {
        if (entity != null) {
            Game game = new Game();
            game.setId(entity.getGameId());
            game.setLocalTeam(transform(entity.getLocalTeam()));
            game.setAwayTeam(transform(entity.getAwayTeam()));
            switch (entity.getStatus()) {
                case Constants.GameStatusEntity.NOT_STARTED:
                    game.setStatus(GameStatus.NOT_STARTED);
                    break;
                case Constants.GameStatusEntity.ONGOING:
                    game.setStatus(GameStatus.ONGOING);
                    break;
                case Constants.GameStatusEntity.FINISHED:
                    game.setStatus(GameStatus.FINISHED);
                    break;
            }
            return game;
        }
        return null;
    }

    public Team transform(TeamEntity entity) {
        if (entity != null) {
            Team team = new Team();
            team.setId(entity.getTeamId());
            team.setName(entity.getName());
            team.setPoints(entity.getPoints());
            return team;
        }
        return null;
    }
}
