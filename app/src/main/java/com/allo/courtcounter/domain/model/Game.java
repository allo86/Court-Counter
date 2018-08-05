package com.allo.courtcounter.domain.model;

public class Game {

    private Long mId;

    private Team mLocalTeam;

    private Team mAwayTeam;

    private GameStatus mStatus;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Team getLocalTeam() {
        return mLocalTeam;
    }

    public void setLocalTeam(Team localTeam) {
        mLocalTeam = localTeam;
    }

    public Team getAwayTeam() {
        return mAwayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        mAwayTeam = awayTeam;
    }

    public GameStatus getStatus() {
        return mStatus;
    }

    public void setStatus(GameStatus status) {
        mStatus = status;
    }
}
