package com.allo.courtcounter.presentation.scenes.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.allo.courtcounter.R;
import com.allo.courtcounter.presentation.CourtCounterApplication;
import com.allo.courtcounter.presentation.base.BaseActivity;
import com.allo.courtcounter.presentation.model.GameModel;
import com.allo.courtcounter.presentation.scenes.common.TeamScoreView;
import com.allo.courtcounter.presentation.scenes.summary.GameSummaryActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class GameDetailsActivity extends BaseActivity implements TeamScoreView.OnTeamScoreViewListener {

    private static final String GAME_ID = "GAME_ID";

    public static Intent getCallingIntent(Context context, Long gameId) {
        Intent intent = new Intent(context, GameDetailsActivity.class);
        intent.putExtra(GAME_ID, gameId);
        return intent;
    }

    @BindView(R.id.tsv_local_team)
    TeamScoreView scoreLocalTeam;

    @BindView(R.id.tsv_away_team)
    TeamScoreView scoreAwayTeam;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private GameDetailsViewModel mGameDetailsViewModel;

    private Long mGameId;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_game_details;
    }

    @Override
    protected void injectDependencies() {
        ((CourtCounterApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected void loadViewModels() {
        mGameDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(GameDetailsViewModel.class);
    }

    @Override
    protected void defineObservers() {
        scoreLocalTeam.setOnTeamScoreViewListener(this);
        scoreAwayTeam.setOnTeamScoreViewListener(this);

        mGameDetailsViewModel.getGameDetailsEvent().observe(this, new Observer<GameModel>() {
            @Override
            public void onChanged(@Nullable GameModel gameModel) {
                if (gameModel != null) {
                    scoreLocalTeam.setTeam(gameModel.getLocalTeam());
                    scoreAwayTeam.setTeam(gameModel.getAwayTeam());
                }
            }
        });

        mGameDetailsViewModel.getErrorEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s == null) s = getString(R.string.generic_error_message);
                Toast.makeText(GameDetailsActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });

        mGameDetailsViewModel.getGameEnded().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean gameFinished) {
                if (gameFinished != null && gameFinished) {
                    goToGameSummary();
                }
            }
        });
    }

    @Override
    protected void readParameters(Bundle args) {
        mGameId = args.getLong(GAME_ID);
    }

    @Override
    protected void restoreSavedInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        mGameDetailsViewModel.loadGameDetails(mGameId);
    }

    @OnClick(R.id.bt_reset)
    public void didClickResetGame() {
        mGameDetailsViewModel.resetGame(mGameId);
    }

    @OnClick(R.id.bt_end)
    public void didClickEndGame() {
        mGameDetailsViewModel.endGame(mGameId);
    }

    @Override
    public void didClickAddPointsToTeam(Long teamId, Long pointsToAdd) {
        mGameDetailsViewModel.addPointsToTeam(mGameId, teamId, pointsToAdd);
    }

    @Override
    public void didClickSubtractPointsToTeam(Long teamId, Long pointsToSubtract) {
        mGameDetailsViewModel.subtractPointsToTeam(mGameId, teamId, pointsToSubtract);
    }

    private void goToGameSummary() {
        startActivity(GameSummaryActivity.getCallingIntent(this, this.mGameId));
        finish();
    }
}
