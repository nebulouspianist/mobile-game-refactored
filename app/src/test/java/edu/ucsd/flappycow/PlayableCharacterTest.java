package edu.ucsd.flappycow;

import static org.junit.Assert.assertTrue;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.flappycow.model.components.Cow;
import edu.ucsd.flappycow.model.components.PlayableCharacter;
import edu.ucsd.flappycow.presenter.GameActivity;

@RunWith(RobolectricTestRunner.class)
public class PlayableCharacterTest {

    @Test
    public void testMove() {

        ActivityScenario<GameActivity> scenario = ActivityScenario.launch(GameActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {

            Cow testCharacter = new Cow(activity, 120, 120);

            assertTrue(testCharacter.getX() == 120/6 );


        });
    }

}
