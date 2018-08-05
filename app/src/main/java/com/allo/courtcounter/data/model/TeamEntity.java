package com.allo.courtcounter.data.model;

import android.support.annotation.NonNull;

import com.allo.courtcounter.data.repository.datasource.database.GameDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = GameDataBase.class)
public class TeamEntity extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @NonNull
    private Long teamId;

    @Column
    private String name;

    @Column
    private Long points;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
