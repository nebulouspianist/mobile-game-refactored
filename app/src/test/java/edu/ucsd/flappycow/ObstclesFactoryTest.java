package edu.ucsd.flappycow;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.flappycow.model.components.Obstacle;
import edu.ucsd.flappycow.model.components.Spider;
import edu.ucsd.flappycow.model.components.WoodLog;
import edu.ucsd.flappycow.presenter.GameActivity;

@RunWith(RobolectricTestRunner.class)
public class ObstclesFactoryTest {

    @Test
    public void testObstclesFactory() {
        ActivityScenario<GameActivity> scenario = ActivityScenario.launch(GameActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.onActivity(activity -> {
            Obstacle obstacle =  new Obstacle(activity, 0);
            assert obstacle.getSpider() instanceof Spider;
            assert obstacle.getWoodLog() instanceof WoodLog;
            assert obstacle.getSpider().getSpeedX() == 0;
        });
    }
}
