//package edu.ucsd.flappycow;
//
//import androidx.lifecycle.Lifecycle;
//import androidx.test.core.app.ActivityScenario;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.RobolectricTestRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import edu.ucsd.flappycow.sprites.Cow;
//import edu.ucsd.flappycow.sprites.Obstacle;
//import edu.ucsd.flappycow.sprites.PowerUp;
//
//@RunWith(RobolectricTestRunner.class)
//public class PassTest {
//
//    @Test
//    public void passTest(){
//        ActivityScenario<GameActivity> scenario = ActivityScenario.launch(GameActivity.class);
//        scenario.moveToState(Lifecycle.State.CREATED);
//        scenario.moveToState(Lifecycle.State.RESUMED);
//
//        scenario.onActivity(activity ->{
//            PassMediator passMediator = new PassMediator(activity, activity.model);
//            List<Obstacle> obstacleList = new ArrayList<>();
//            Obstacle obstacle = new Obstacle(activity, 0);
//            obstacle.setX(20);
//            obstacle.setY(20);
//            obstacleList.add(obstacle);
//
//            Cow cow = new Cow(activity, 100, 100);
//            cow.setX(40);
//            cow.setY(40);
//
//            passMediator.checkPasses(activity, obstacleList, cow);
//
//            assert obstacle.isAlreadyPassed;
//
//
//
//        });
//    }
//}
