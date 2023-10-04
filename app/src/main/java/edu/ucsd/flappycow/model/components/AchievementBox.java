/**
 * Saves achievements and score in shared preferences.
 * You should use a SQLite DB instead, but I'm too lazy to chance it now.
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.model.components;

import android.app.Activity;
import android.content.SharedPreferences;

import edu.ucsd.flappycow.util.Contract;

public class AchievementBox {
    /**
     * Points needed for a gold medal
     */
    public static final int GOLD_POINTS = 100;

    /**
     * Points needed for a silver medal
     */
    public static final int SILVER_POINTS = 50;

    /**
     * Points needed for a bronze medal
     */
    public static final int BRONZE_POINTS = 10;

    public static final String SAVE_NAME = "achivements";

    public static final String KEY_POINTS = "points";
    public static final String ACHIEVEMENT_KEY_50_COINS = "achievement_survive_5_minutes";
    public static final String ACHIEVEMENT_KEY_TOASTIFICATION = "achievement_toastification";
    public static final String ACHIEVEMENT_KEY_BRONZE = "achievement_bronze";
    public static final String ACHIEVEMENT_KEY_SILVER = "achievement_silver";
    public static final String ACHIEVEMENT_KEY_GOLD = "achievement_gold";

    public int points;
    public boolean achievement_50_coins;
    public boolean achievement_toastification;
    public boolean achievement_bronze;
    public boolean achievement_silver;
    public boolean achievement_gold;

    /**
     * Stores the score and achievements locally.
     * <p>
     * The accomplishments will be saved local via SharedPreferences.
     * This makes it very easy to cheat.
     * <p>
     * todo: is activity the right thing to pass in here?
     *
     * @param activity activity that is needed for shared preferences
     */

    public void save(Activity activity) {
        try {
            // Preconditions
            Contract.require(activity != null, "Activity must not be null");
            Contract.require(points >= 0, "Points must be non-negative");

            SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
            SharedPreferences.Editor editor = saves.edit();

            int savedPoints = saves.getInt(KEY_POINTS, 0);
            Contract.require(points >= savedPoints, "Points in saves should not be less than the current points");

            if (points > savedPoints) {
                editor.putInt(KEY_POINTS, points);
            }

            // Invariants: Once an achievement is achieved, it should remain achieved.
            if (achievement_50_coins) {
                editor.putBoolean(ACHIEVEMENT_KEY_50_COINS, true);
            }
            if (achievement_toastification) {
                editor.putBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, true);
            }
            if (achievement_bronze) {
                editor.putBoolean(ACHIEVEMENT_KEY_BRONZE, true);
            }
            if (achievement_silver) {
                editor.putBoolean(ACHIEVEMENT_KEY_SILVER, true);
            }
            if (achievement_gold) {
                editor.putBoolean(ACHIEVEMENT_KEY_GOLD, true);
            }

            editor.apply();

            // Postconditions: Verify the saved state must reflect the current state.
            SharedPreferences postSave = activity.getSharedPreferences(SAVE_NAME, 0);
            Contract.ensure(points == postSave.getInt(KEY_POINTS, 0), "Saved points does not match current points");
            Contract.ensure(achievement_50_coins == postSave.getBoolean(ACHIEVEMENT_KEY_50_COINS, false), "Saved achievement_50_coins does not match current state");
            Contract.ensure(achievement_toastification == postSave.getBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, false), "Saved achievement_toastification does not match current state");
            Contract.ensure(achievement_bronze == postSave.getBoolean(ACHIEVEMENT_KEY_BRONZE, false), "Saved achievement_bronze does not match current state");
            Contract.ensure(achievement_silver == postSave.getBoolean(ACHIEVEMENT_KEY_SILVER, false), "Saved achievement_silver does not match current state");
            Contract.ensure(achievement_gold == postSave.getBoolean(ACHIEVEMENT_KEY_GOLD, false), "Saved achievement_gold does not match current state");

        } catch (Contract.ViolationException ex) {
            ex.printStackTrace();
        }


    }


    /**
     * reads the local stored data
     *
     * @param activity activity that is needed for shared preferences
     * @return local stored score and achievements
     */

    public static AchievementBox load(Activity activity) {
        try {
            // Preconditions
            Contract.require(activity != null, "Activity must not be null");

            AchievementBox box = new AchievementBox();
            SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);

            box.points = saves.getInt(KEY_POINTS, 0);
            box.achievement_50_coins = saves.getBoolean(ACHIEVEMENT_KEY_50_COINS, false);
            box.achievement_toastification = saves.getBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, false);
            box.achievement_bronze = saves.getBoolean(ACHIEVEMENT_KEY_BRONZE, false);
            box.achievement_silver = saves.getBoolean(ACHIEVEMENT_KEY_SILVER, false);
            box.achievement_gold = saves.getBoolean(ACHIEVEMENT_KEY_GOLD, false);

            // Postconditions
            Contract.ensure(box.points >= 0, "Loaded points must be non-negative");
            Contract.ensure(!box.achievement_50_coins || saves.getBoolean(ACHIEVEMENT_KEY_50_COINS, false), "Loaded achievement_50_coins state must match saved state");
            Contract.ensure(!box.achievement_toastification || saves.getBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, false), "Loaded achievement_toastification state must match saved state");
            Contract.ensure(!box.achievement_bronze || saves.getBoolean(ACHIEVEMENT_KEY_BRONZE, false), "Loaded achievement_bronze state must match saved state");
            Contract.ensure(!box.achievement_silver || saves.getBoolean(ACHIEVEMENT_KEY_SILVER, false), "Loaded achievement_silver state must match saved state");
            Contract.ensure(!box.achievement_gold || saves.getBoolean(ACHIEVEMENT_KEY_GOLD, false), "Loaded achievement_gold state must match saved state");

            return box;

        } catch (Contract.ViolationException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    private void checkInvariants() {
        Contract.invariant(points >= 0, "points must be non-negative");
        // Add checks for other invariants...
    }
}