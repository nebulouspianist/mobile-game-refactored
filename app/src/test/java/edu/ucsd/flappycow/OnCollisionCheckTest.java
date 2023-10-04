package edu.ucsd.flappycow;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.flappycow.model.components.Coin;
import edu.ucsd.flappycow.model.components.Cow;
import edu.ucsd.flappycow.model.components.Obstacle;
import edu.ucsd.flappycow.model.components.PowerUp;
import edu.ucsd.flappycow.model.connectors.CollisionMediator;
import edu.ucsd.flappycow.presenter.GameActivity;

@RunWith(RobolectricTestRunner.class)
public class OnCollisionCheckTest {
    @Test
    public void testOnCollision(){
        ActivityScenario<GameActivity> scenario = ActivityScenario.launch(GameActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            CollisionMediator collisionMediator = new CollisionMediator(activity, activity.model);
            List<PowerUp> powerUpsList = new ArrayList<>();
            List<Obstacle> obstacleList = new ArrayList<>();


            Coin coin = new Coin(activity, 100, 0);
            coin.setX(20);
            coin.setY(20);
            powerUpsList.add(coin);

            Cow cow = new Cow(activity,100,100);
            cow.setX(20);
            cow.setY(20);

            collisionMediator.checkCollision(activity, obstacleList, cow, powerUpsList,100,1);

            assert activity.model.coins == 1;
        });
    }
}
