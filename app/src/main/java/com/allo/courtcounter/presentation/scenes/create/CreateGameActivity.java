package com.allo.courtcounter.presentation.scenes.create;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.allo.courtcounter.R;
import com.allo.courtcounter.presentation.CourtCounterApplication;
import com.allo.courtcounter.presentation.base.BaseActivity;
import com.allo.courtcounter.presentation.model.GameModel;
import com.allo.courtcounter.presentation.scenes.details.GameDetailsActivity;
import com.allo.courtcounter.presentation.scenes.list.PreviousGamesActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateGameActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, CreateGameActivity.class);
    }

    @BindView(R.id.et_local_team)
    EditText inputLocalTeam;

    @BindView(R.id.et_away_team)
    EditText inputAwayTeam;

    @BindView(R.id.bt_create)
    AppCompatButton btCreateGame;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private CreateGameViewModel mCreateGameViewModel;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_game;
    }

    @Override
    protected void injectDependencies() {
        ((CourtCounterApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected void loadViewModels() {
        mCreateGameViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateGameViewModel.class);
    }

    @Override
    protected void defineObservers() {
        inputLocalTeam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mCreateGameViewModel.setLocalTeam(editable != null ? editable.toString() : "");
            }
        });

        inputAwayTeam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mCreateGameViewModel.setAwayTeam(editable != null ? editable.toString() : "");
            }
        });

        mCreateGameViewModel.getEnableCreateGameButton().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean enabled) {
                if (enabled == null) enabled = false;
                btCreateGame.setEnabled(enabled);
            }
        });

        mCreateGameViewModel.getGameDetailsEvent().observe(this, new Observer<GameModel>() {
            @Override
            public void onChanged(@Nullable GameModel game) {
                if (game != null) goToGameDetails(game.getId());
            }
        });

        mCreateGameViewModel.getErrorEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s == null) s = getString(R.string.generic_error_message);
                Toast.makeText(CreateGameActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void readParameters(Bundle args) {

    }

    @Override
    protected void restoreSavedInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onResume() {
        super.onResume();

        mCreateGameViewModel.setLocalTeam(inputLocalTeam.getText().toString());
        mCreateGameViewModel.setAwayTeam(inputAwayTeam.getText().toString());
    }

    @OnClick(R.id.bt_create)
    public void didClickStartGame() {
        mCreateGameViewModel.startGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.previous_games:
                goToPreviousGames();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToGameDetails(Long gameId) {
        startActivity(GameDetailsActivity.getCallingIntent(this, gameId));
        finish();
    }

    private void goToPreviousGames() {
        startActivity(PreviousGamesActivity.getCallingIntent(this));
    }
}
