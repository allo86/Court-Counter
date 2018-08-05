package com.allo.courtcounter.data.model;

import android.support.annotation.NonNull;

import com.allo.courtcounter.data.repository.datasource.database.GameDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = GameDataBase.class)
public class GameEntity extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @NonNull
    private Long gameId;

    @ForeignKey(tableClass = TeamEntity.class)
    private TeamEntity localTeam;

    @ForeignKey(tableClass = TeamEntity.class)
    private TeamEntity awayTeam;

    @Column
    private int status;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public TeamEntity getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(TeamEntity localTeam) {
        this.localTeam = localTeam;
    }

    public TeamEntity getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(TeamEntity awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
