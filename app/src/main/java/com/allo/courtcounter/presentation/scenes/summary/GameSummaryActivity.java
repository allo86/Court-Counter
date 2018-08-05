package com.allo.courtcounter.presentation.scenes.summary;

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
import com.allo.courtcounter.presentation.scenes.common.TeamSummaryView;
import com.allo.courtcounter.presentation.scenes.create.CreateGameActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class GameSummaryActivity extends BaseActivity {

    private static final String GAME_ID = "GAME_ID";

    public static Intent getCallingIntent(Context context, Long gameId) {
        Intent intent = new Intent(context, GameSummaryActivity.class);
        intent.putExtra(GAME_ID, gameId);
        return intent;
    }

    @BindView(R.id.tsv_local_team)
    TeamSummaryView summaryLocalTeam;

    @BindView(R.id.tsv_away_team)
    TeamSummaryView summaryAwayTeam;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private GameSummaryViewModel mGameSummaryViewModel;

    private Long mGameId;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_game_summary;
    }

    @Override
    protected void injectDependencies() {
        ((CourtCounterApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected void loadViewModels() {
        mGameSummaryViewModel = ViewModelProviders.of(this, viewModelFactory).get(GameSummaryViewModel.class);
    }

    @Override
    protected void defineObservers() {
        mGameSummaryViewModel.getGameDetailsEvent().observe(this, new Observer<GameModel>() {
            @Override
            public void onChanged(@Nullable GameModel gameModel) {
                if (gameModel != null) {
                    summaryLocalTeam.setTeam(gameModel.getLocalTeam());
                    summaryAwayTeam.setTeam(gameModel.getAwayTeam());
                }
            }
        });

        mGameSummaryViewModel.getErrorEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s == null) s = getString(R.string.generic_error_message);
                Toast.makeText(GameSummaryActivity.this, s, Toast.LENGTH_LONG).show();
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
        mGameSummaryViewModel.loadGameSummary(mGameId);
    }

    @OnClick(R.id.bt_new_game)
    public void didClickResetGame() {
        startActivity(CreateGameActivity.getCallingIntent(this));
        finish();
    }
}
