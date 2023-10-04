package edu.ucsd.flappycow;


import org.junit.Before;
import org.junit.Test;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.flappycow.factory.FactoryItemTypes;
import edu.ucsd.flappycow.factory.PowerUpsFactory;
import edu.ucsd.flappycow.model.connectors.GameModel;
import edu.ucsd.flappycow.pojo.FactoryPojo;
import edu.ucsd.flappycow.model.components.Coin;
import edu.ucsd.flappycow.model.components.Toast;
import edu.ucsd.flappycow.model.components.Virus;
import edu.ucsd.flappycow.presenter.GameActivity;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class CreatePowerUpTest {
    private GameModel gameModel;
    private PowerUpsFactory powerUpsFactory;

    @Before
    public void setUp() {
        gameModel = new GameModel(mock(GameActivity.class));
        powerUpsFactory = mock(PowerUpsFactory.class);
        gameModel.powerUpsFactory = powerUpsFactory;
    }

    @Test
    public void testCreatePowerUp_WhenPointsToToastAndNoExistingPowerUp_ShouldCreateToastPowerUp() {
        FactoryPojo factoryPojo = mock(FactoryPojo.class);
        gameModel.accomplishmentBox.points = Toast.POINTS_TO_TOAST;
        gameModel.powerUps = new ArrayList<>();

        gameModel.createPowerUp();

        verify(powerUpsFactory).createPowerUp(FactoryItemTypes.TOAST, factoryPojo);
        assertEquals(1, gameModel.powerUps.size());
    }

    @Test
    public void testCreatePowerUp_WhenRandomLessThan20AndNoExistingPowerUp_ShouldCreateCoinPowerUp() {
        FactoryPojo factoryPojo = mock(FactoryPojo.class);
        gameModel.accomplishmentBox.points = 0;
        gameModel.powerUps = new ArrayList<>();
        when(Math.random()).thenReturn(0.15); // Mock the random number

        gameModel.createPowerUp();

        verify(powerUpsFactory).createPowerUp(FactoryItemTypes.COIN, factoryPojo);
        assertEquals(1, gameModel.powerUps.size());
    }

    @Test
    public void testCreatePowerUp_WhenRandomLessThan10AndNoExistingPowerUp_ShouldCreateVirusPowerUp() {
        FactoryPojo factoryPojo = mock(FactoryPojo.class);
        gameModel.accomplishmentBox.points = 0;
        gameModel.powerUps = new ArrayList<>();
        when(Math.random()).thenReturn(0.08); // Mock the random number

        gameModel.createPowerUp();

        verify(powerUpsFactory).createPowerUp(FactoryItemTypes.VIRUS, factoryPojo);
        assertEquals(1, gameModel.powerUps.size());
    }

}