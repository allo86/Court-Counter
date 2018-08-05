package com.allo.courtcounter.presentation.scenes.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.allo.courtcounter.R;
import com.allo.courtcounter.presentation.CourtCounterApplication;
import com.allo.courtcounter.presentation.base.BaseActivity;
import com.allo.courtcounter.presentation.model.GameModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class PreviousGamesActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PreviousGamesActivity.class);
    }

    @BindView(R.id.rv_games)
    RecyclerView rvGames;

    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private PreviousGamesViewModel mPreviousGamesViewModel;

    private List<GameModel> mGames = new ArrayList<>();

    private PreviousGamesAdapter mAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_previous_games;
    }

    @Override
    protected void injectDependencies() {
        ((CourtCounterApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected void loadViewModels() {
        mPreviousGamesViewModel = ViewModelProviders.of(this, viewModelFactory).get(PreviousGamesViewModel.class);
    }

    @Override
    protected void defineObservers() {
        tvEmpty.setVisibility(View.GONE);
        rvGames.setVisibility(View.GONE);

        rvGames.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PreviousGamesAdapter(this, mGames);
        rvGames.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvGames.addItemDecoration(itemDecoration);

        mPreviousGamesViewModel.getGames().observe(this, new Observer<List<GameModel>>() {
            @Override
            public void onChanged(@Nullable List<GameModel> games) {
                if (games != null && !games.isEmpty()) {
                    for (GameModel game : games) {
                        if (!mGames.contains(game)) {
                            mGames.add(game);
                        }
                    }
                }
                mAdapter.notifyAdapterDataSetChanged();

                if (mGames.isEmpty()) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    rvGames.setVisibility(View.GONE);
                } else {
                    tvEmpty.setVisibility(View.GONE);
                    rvGames.setVisibility(View.VISIBLE);
                }
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
        mPreviousGamesViewModel.loadGames();
    }
}
