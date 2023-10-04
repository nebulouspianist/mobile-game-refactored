package edu.ucsd.flappycow;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.flappycow.factory.FactoryItemTypes;
import edu.ucsd.flappycow.factory.PowerUpsFactory;
import edu.ucsd.flappycow.pojo.FactoryPojo;
import edu.ucsd.flappycow.model.components.Coin;
import edu.ucsd.flappycow.model.components.Toast;
import edu.ucsd.flappycow.model.components.Virus;
import edu.ucsd.flappycow.presenter.GameActivity;

@RunWith(RobolectricTestRunner.class)
public class PowerUpsFactoryTest {

    @Test
    public void testPowerUpsFactory() {
        ActivityScenario<GameActivity> scenario = ActivityScenario.launch(GameActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.onActivity(activity -> {
            PowerUpsFactory powerUpsFactory =  new PowerUpsFactory();
            FactoryPojo powerUpFactoryPojo = new FactoryPojo(activity,0, 0, 0);
            var toastItem = powerUpsFactory.createPowerUp(FactoryItemTypes.TOAST, powerUpFactoryPojo);
            var coinItem = powerUpsFactory.createPowerUp(FactoryItemTypes.COIN ,powerUpFactoryPojo);
            var virusItem = powerUpsFactory.createPowerUp(FactoryItemTypes.VIRUS ,powerUpFactoryPojo);
            assert toastItem instanceof Toast;
            assert coinItem instanceof Coin;
            assert virusItem instanceof Virus;
            assert ((Toast) toastItem).getViewHeight() == 0;
            assert coinItem.getSpeedX() == 0;
        });
    }
}
