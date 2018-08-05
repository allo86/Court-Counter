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

public class TeamSummaryView extends LinearLayout {

    @BindView(R.id.tv_team_name)
    TextView tvName;

    @BindView(R.id.tv_team_score)
    TextView tvPoints;

    private TeamModel mTeam;

    public TeamSummaryView(Context context) {
        super(context);
        init(null);
    }

    public TeamSummaryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TeamSummaryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TeamSummaryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        inflate(getContext(), R.layout.view_team_summary, this);
        ButterKnife.bind(this);

        //if (attrs != null) {}
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
}
