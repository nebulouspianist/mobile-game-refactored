package edu.ucsd.flappycow;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.flappycow.presenter.GameActivity;

@RunWith(RobolectricTestRunner.class)
public class AdTest {


    @Test
    public void testAdSetup1(){
        ActivityScenario<GameActivity> scenario = ActivityScenario.launch(GameActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.RESUMED);


        scenario.onActivity(activity -> {

            assertTrue(activity.ad_condition_checked == 1);
            assertTrue(activity.ad_loaded == 0);
        });

    }


}
