package com.allo.courtcounter.presentation.scenes.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allo.courtcounter.R;
import com.allo.courtcounter.presentation.model.GameModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviousGamesAdapter extends RecyclerView.Adapter<PreviousGamesAdapter.GameViewHolder> {

    private Context mContext;
    private List<GameModel> mGames;

    private Float mScoreWidth;

    public PreviousGamesAdapter(List<GameModel> games) {
        this.mGames = games;
    }

    public PreviousGamesAdapter(Context context, List<GameModel> games) {
        this.mContext = context;
        this.mGames = games;
        calculateScoreWidth();
    }

    private void calculateScoreWidth() {
        if (mGames != null && !mGames.isEmpty()) {
            Comparator<GameModel> scoreComparator = new Comparator<GameModel>() {
                @Override
                public int compare(GameModel g1, GameModel g2) {
                    Long score1 = g1.getLocalTeam().getPoints() > g1.getAwayTeam().getPoints() ? g1.getLocalTeam().getPoints() : g1.getAwayTeam().getPoints();
                    Long score2 = g2.getLocalTeam().getPoints() > g2.getAwayTeam().getPoints() ? g2.getLocalTeam().getPoints() : g2.getAwayTeam().getPoints();
                    return score1.compareTo(score2);
                }
            };
            GameModel gameWithHighestScore = Collections.max(mGames, scoreComparator);
            Long highestScore = gameWithHighestScore.getLocalTeam().getPoints() > gameWithHighestScore.getAwayTeam().getPoints() ?
                    gameWithHighestScore.getLocalTeam().getPoints() : gameWithHighestScore.getAwayTeam().getPoints();
            TextPaint paint = new TextPaint();
            paint.setTextSize(mContext.getResources().getDimension(R.dimen.previous_game_team_score));
            mScoreWidth = paint.measureText(String.valueOf(highestScore));
        }
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_previous_game, parent, false);
        if (mScoreWidth != null) {
            setMaxWidth(mView.findViewById(R.id.tv_local_team_score));
            setMaxWidth(mView.findViewById(R.id.tv_away_team_score));
        }
        return new GameViewHolder(mView);
    }

    private void setMaxWidth(View view) {
        float padding = view.getContext().getResources().getDimension(R.dimen.previous_game_team_padding);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) (mScoreWidth + (2 * padding));
        view.setLayoutParams(params);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        holder.configureWithGame(mGames.get(position));
    }

    @Override
    public int getItemCount() {
        return mGames != null ? mGames.size() : 0;
    }

    public void notifyAdapterDataSetChanged() {
        calculateScoreWidth();
        super.notifyDataSetChanged();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_local_team_name)
        TextView tvLocalTeamName;

        @BindView(R.id.tv_local_team_score)
        TextView tvLocalTeamScore;

        @BindView(R.id.tv_away_team_name)
        TextView tvAwayTeamName;

        @BindView(R.id.tv_away_team_score)
        TextView tvAwayTeamScore;

        public GameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void configureWithGame(GameModel game) {
            tvLocalTeamName.setText(game.getLocalTeam().getName());
            tvLocalTeamScore.setText(String.valueOf(game.getLocalTeam().getPoints()));
            tvAwayTeamName.setText(game.getAwayTeam().getName());
            tvAwayTeamScore.setText(String.valueOf(game.getAwayTeam().getPoints()));
        }
    }
}
