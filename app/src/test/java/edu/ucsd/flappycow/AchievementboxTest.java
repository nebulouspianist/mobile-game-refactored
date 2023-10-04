package edu.ucsd.flappycow;

import static org.junit.Assert.assertTrue;

import android.content.SharedPreferences;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.flappycow.model.components.AchievementBox;
import edu.ucsd.flappycow.presenter.GameActivity;

@RunWith(RobolectricTestRunner.class)
public class AchievementboxTest {

    public static final String SAVE_NAME = "achivements";

    public static final String KEY_POINTS = "points";
    public static final String ACHIEVEMENT_KEY_50_COINS = "achievement_survive_5_minutes";
    public static final String ACHIEVEMENT_KEY_TOASTIFICATION = "achievement_toastification";
    public static final String ACHIEVEMENT_KEY_BRONZE = "achievement_bronze";
    public static final String ACHIEVEMENT_KEY_SILVER = "achievement_silver";
    public static final String ACHIEVEMENT_KEY_GOLD = "achievement_gold";

    @Test
    public void testAchievementBox() {
        ActivityScenario<GameActivity> scenario = ActivityScenario.launch(GameActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            AchievementBox.load(activity);
            SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
            assert activity.model.accomplishmentBox.points == saves.getInt(KEY_POINTS, 0);
            assert activity.model.accomplishmentBox.achievement_50_coins == saves.getBoolean(ACHIEVEMENT_KEY_50_COINS, false);
            assert activity.model.accomplishmentBox.achievement_toastification == saves.getBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, false);
            assert activity.model.accomplishmentBox.achievement_bronze == saves.getBoolean(ACHIEVEMENT_KEY_BRONZE, false);
            assert activity.model.accomplishmentBox.achievement_silver == saves.getBoolean(ACHIEVEMENT_KEY_SILVER, false);
            assert activity.model.accomplishmentBox.achievement_gold == saves.getBoolean(ACHIEVEMENT_KEY_GOLD, false);

        });
    }
    @Test
    public void testAchievementBoxSaveAndLoad() {
        ActivityScenario<GameActivity> scenario = ActivityScenario.launch(GameActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            // Modify the current state
            activity.model.accomplishmentBox.points = 100;
            activity.model.accomplishmentBox.achievement_50_coins = true;
            activity.model.accomplishmentBox.achievement_toastification = true;
            activity.model.accomplishmentBox.achievement_bronze = true;
            activity.model.accomplishmentBox.achievement_silver = true;
            activity.model.accomplishmentBox.achievement_gold = true;

            // Save current state
            activity.model.accomplishmentBox.save(activity);

            // Reset state
            activity.model.accomplishmentBox.points = 0;
            activity.model.accomplishmentBox.achievement_50_coins = false;
            activity.model.accomplishmentBox.achievement_toastification = false;
            activity.model.accomplishmentBox.achievement_bronze = false;
            activity.model.accomplishmentBox.achievement_silver = false;
            activity.model.accomplishmentBox.achievement_gold = false;

            // Load saved state
            AchievementBox loadedBox = AchievementBox.load(activity);

            // Compare loaded state to saved state
            assert 100 ==  loadedBox.points;
            assertTrue(loadedBox.achievement_50_coins);
            assertTrue(loadedBox.achievement_toastification);
            assertTrue(loadedBox.achievement_bronze);
            assertTrue(loadedBox.achievement_silver);
            assertTrue(loadedBox.achievement_gold);
        });
    }

}
