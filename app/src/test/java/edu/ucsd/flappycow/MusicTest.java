package edu.ucsd.flappycow;

import static edu.ucsd.flappycow.gameData.SoundEnum.COIN_SOUND;
import static edu.ucsd.flappycow.gameData.SoundEnum.COLLIDE_SOUND;
import static edu.ucsd.flappycow.gameData.SoundEnum.PASS_SOUND;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.flappycow.gameData.MusicManagerLoadEvent;
import edu.ucsd.flappycow.presenter.GameActivity;

@RunWith(RobolectricTestRunner.class)
public class MusicTest {

    @Test
    public void testMusicLoad() {
        ActivityScenario<GameActivity> scenario = ActivityScenario.launch(GameActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            activity.view.musicManagerLoad(new MusicManagerLoadEvent(COIN_SOUND, activity, R.raw.coin, 1));
            assert activity.view.musicManager.coinSound == 2;
            activity.view.musicManagerLoad(new MusicManagerLoadEvent(PASS_SOUND, activity, R.raw.pass, 1));
            assert activity.view.musicManager.passSound == 3;
            activity.view.musicManagerLoad(new MusicManagerLoadEvent(COLLIDE_SOUND, activity, R.raw.crash, 1));
            assert activity.view.musicManager.collideSound == 4;
        });
    }
}
