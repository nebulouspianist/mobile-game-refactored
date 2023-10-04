/**
 * The dialog shown when the game is over
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.view.components;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.ucsd.flappycow.R;
import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.presenter.MainActivity;
import edu.ucsd.flappycow.util.Util;

public class GameOverDialog extends Dialog {
    public static final int REVIVE_PRICE = 5;

    /**
     * Name of the SharedPreference that saves the score
     */
    public static final String score_save_name = "score_save";

    /**
     * Key that saves the score
     */
    public static final String best_score_key = "score";

    /**
     * The game that invokes this dialog
     */
    private GameActivity gameActivity;

    private TextView tvCurrentScoreVal;
    private TextView tvBestScoreVal;

    public GameOverDialog(GameActivity gameActivity) {
        super(gameActivity);
        this.gameActivity = gameActivity;
        this.setContentView(R.layout.gameover);
        this.setCancelable(false);

        tvCurrentScoreVal = (TextView) findViewById(R.id.tv_current_score_value);
        tvBestScoreVal = (TextView) findViewById(R.id.tv_best_score_value);
    }

    public void init() {
        Button okButton = (Button) findViewById(R.id.b_ok);
        okButton.setOnClickListener(view -> {
            saveCoins();
            if (gameActivity.model.numberOfRevive <= 1) {
                gameActivity.model.accomplishmentBox.save(gameActivity);
            }

            dismiss();
            gameActivity.finish();
        });

        Button reviveButton = (Button) findViewById(R.id.b_revive);
        reviveButton.setText(gameActivity.getResources().getString(R.string.revive_button)
            + " " + REVIVE_PRICE * gameActivity.model.numberOfRevive + " "
            + gameActivity.getResources().getString(R.string.coins));
        reviveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //TODO
                gameActivity.model.coins -= REVIVE_PRICE * gameActivity.model.numberOfRevive;
                saveCoins();
                gameActivity.model.revive();
            }
        });
        //TODO
        if (gameActivity.model.coins < REVIVE_PRICE * gameActivity.model.numberOfRevive) {
            reviveButton.setClickable(false);
        } else {
            reviveButton.setClickable(true);
        }

        manageScore();
        manageMedals();
    }

    private void manageScore() {
        SharedPreferences saves = gameActivity.getSharedPreferences(score_save_name, 0);
        int oldPoints = saves.getInt(best_score_key, 0);
        //TODO
        if (gameActivity.model.accomplishmentBox.points > oldPoints) {
            // Save new highscore
            SharedPreferences.Editor editor = saves.edit();
            editor.putInt(best_score_key, gameActivity.model.accomplishmentBox.points);
            tvBestScoreVal.setTextColor(Color.RED);
            editor.apply();
        }
        tvCurrentScoreVal.setText("" + gameActivity.model.accomplishmentBox.points);
        tvBestScoreVal.setText("" + oldPoints);
    }

    private void manageMedals() {
        SharedPreferences MEDAL_SAVE = gameActivity.getSharedPreferences(MainActivity.MEDAL_SAVE, 0);
        int medal = MEDAL_SAVE.getInt(MainActivity.MEDAL_KEY, 0);

        SharedPreferences.Editor editor = MEDAL_SAVE.edit();

        if (gameActivity.model.accomplishmentBox.achievement_gold) {
            ((ImageView) findViewById(R.id.medal)).setImageBitmap(Util.getScaledBitmapAlpha8(gameActivity, R.drawable.gold));
            if (medal < 3) {
                editor.putInt(MainActivity.MEDAL_KEY, 3);
            }
        } else if (gameActivity.model.accomplishmentBox.achievement_silver) {
            ((ImageView) findViewById(R.id.medal)).setImageBitmap(Util.getScaledBitmapAlpha8(gameActivity, R.drawable.silver));
            if (medal < 2) {
                editor.putInt(MainActivity.MEDAL_KEY, 2);
            }
        } else if (gameActivity.model.accomplishmentBox.achievement_bronze) {
            ((ImageView) findViewById(R.id.medal)).setImageBitmap(Util.getScaledBitmapAlpha8(gameActivity, R.drawable.bronce));
            if (medal < 1) {
                editor.putInt(MainActivity.MEDAL_KEY, 1);
            }
        } else {
            ((ImageView) findViewById(R.id.medal)).setVisibility(View.INVISIBLE);
        }
        editor.apply();
    }

    private void saveCoins() {
        SharedPreferences coin_save = gameActivity.getSharedPreferences(GameActivity.coin_save, 0);
        coin_save.getInt(GameActivity.coin_key, 0);
        SharedPreferences.Editor editor = coin_save.edit();
        //TODO: gameActivity.coins
        //editor.putInt(GameActivity.coin_key, gameActivity.coins);
        editor.putInt(GameActivity.coin_key, gameActivity.model.coins);

        editor.apply();
    }

}
