package edu.ucsd.flappycow;


import static org.junit.Assert.assertTrue;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.flappycow.presenter.GameActivity;

@RunWith(RobolectricTestRunner.class)
public class BuilderTest {


    @Test
    public void BuidlerTest1(){
        ActivityScenario<GameActivity> scenario = ActivityScenario.launch(GameActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {

            //test if view is successfully created
            assertTrue(activity.view.SHOW_GAME_OVER_DIALOG == 0);
            assertTrue(activity.view.SHOW_TOAST == 1);
            assertTrue(activity.view.SHOW_AD == 2);

//            test componenets initiated
            assertTrue(activity.view.adManager != null);
            assertTrue(activity.view.musicManager != null);

//            test if model is successfully created
            assertTrue(activity.model.numberOfRevive == 1);

            assertTrue(activity.model.accomplishmentBox != null);






        });
    }
}
