package com.allo.courtcounter.presentation.model;

public class GameModel {

    private Long mId;

    private TeamModel mLocalTeam;

    private TeamModel mAwayTeam;

    private int mStatus;

    public GameModel(Long id, TeamModel localTeam, TeamModel awayTeam, int status) {
        this.mId = id;
        this.mLocalTeam = localTeam;
        this.mAwayTeam = awayTeam;
        this.mStatus = status;
    }

    public Long getId() {
        return mId;
    }

    public TeamModel getLocalTeam() {
        return mLocalTeam;
    }

    public TeamModel getAwayTeam() {
        return mAwayTeam;
    }

    public int getStatus() {
        return mStatus;
    }
}
