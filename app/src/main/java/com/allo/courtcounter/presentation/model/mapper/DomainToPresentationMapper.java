package com.allo.courtcounter.presentation.model.mapper;

import com.allo.courtcounter.domain.model.Game;
import com.allo.courtcounter.domain.model.GameStatus;
import com.allo.courtcounter.domain.model.Team;
import com.allo.courtcounter.presentation.model.GameModel;
import com.allo.courtcounter.presentation.model.TeamModel;

import javax.inject.Inject;

public class DomainToPresentationMapper {

    @Inject
    public DomainToPresentationMapper() {

    }

    public GameModel transform(Game item) {
        if (item != null) {
            return new GameModel(item.getId(),
                    transform(item.getLocalTeam()),
                    transform(item.getAwayTeam()),
                    transform(item.getStatus()));
        }
        return null;
    }

    public TeamModel transform(Team item) {
        if (item != null) {
            return new TeamModel(item.getId(),
                    item.getName(),
                    item.getPoints());
        }
        return null;
    }

    public int transform(GameStatus item) {
        switch (item) {
            case NOT_STARTED:
                return com.allo.courtcounter.presentation.model.Constants.GameStatusModel.NOT_STARTED;
            case ONGOING:
                return com.allo.courtcounter.presentation.model.Constants.GameStatusModel.ONGOING;
            case FINISHED:
                return com.allo.courtcounter.presentation.model.Constants.GameStatusModel.FINISHED;
            default:
                return com.allo.courtcounter.presentation.model.Constants.GameStatusModel.NOT_STARTED;
        }
    }
}
