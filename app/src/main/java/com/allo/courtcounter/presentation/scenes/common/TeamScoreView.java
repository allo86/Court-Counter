package com.allo.courtcounter.presentation.scenes.common;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allo.courtcounter.R;
import com.allo.courtcounter.presentation.model.TeamModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeamScoreView extends LinearLayout {

    public interface OnTeamScoreViewListener {
        void didClickAddPointsToTeam(Long teamId, Long pointsToAdd);

        void didClickSubtractPointsToTeam(Long teamId, Long pointsToSubtract);
    }

    private OnTeamScoreViewListener mListener;

    @BindView(R.id.tv_team_name)
    TextView tvName;

    @BindView(R.id.tv_team_points)
    TextView tvPoints;

    private TeamModel mTeam;

    public TeamScoreView(Context context) {
        super(context);
        init(null);
    }

    public TeamScoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TeamScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TeamScoreView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        inflate(getContext(), R.layout.view_team_score, this);
        ButterKnife.bind(this);

        //if (attrs != null) {}
    }

    public void setOnTeamScoreViewListener(OnTeamScoreViewListener listener) {
        this.mListener = listener;
    }

    public void setTeam(TeamModel team) {
        this.mTeam = team;
        update();
    }

    private void update() {
        if (mTeam != null) {
            tvName.setText(mTeam.getName());
            tvPoints.setText(String.valueOf(mTeam.getPoints()));
        }
    }

    @OnClick(R.id.bt_add_3_points)
    void didClickAdd3Points() {
        if (this.mTeam != null && this.mListener != null) {
            this.mListener.didClickAddPointsToTeam(mTeam.getId(), 3L);
        }
    }

    @OnClick(R.id.bt_add_2_points)
    void didClickAdd2Points() {
        if (this.mTeam != null && this.mListener != null) {
            this.mListener.didClickAddPointsToTeam(mTeam.getId(), 2L);
        }
    }

    @OnClick(R.id.bt_add_1_points)
    void didClickAdd1Point() {
        if (this.mTeam != null && this.mListener != null) {
            this.mListener.didClickAddPointsToTeam(mTeam.getId(), 1L);
        }
    }

    @OnClick(R.id.bt_substract_1_points)
    void didClickSubstract1Point() {
        if (this.mTeam != null && this.mListener != null) {
            this.mListener.didClickSubtractPointsToTeam(mTeam.getId(), 1L);
        }
    }
}
