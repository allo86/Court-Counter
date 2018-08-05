package com.allo.courtcounter.presentation.model;

public class TeamModel {

    private Long mId;

    private String mName;

    private Long mPoints;

    public TeamModel(Long id, String name, Long points) {
        mId = id;
        mName = name;
        mPoints = points;
    }

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Long getPoints() {
        return mPoints;
    }
}
