package edu.ucsd.flappycow.factory;

import edu.ucsd.flappycow.pojo.FactoryPojo;
import edu.ucsd.flappycow.model.components.Coin;
import edu.ucsd.flappycow.model.components.PowerUp;
import edu.ucsd.flappycow.model.components.Toast;
import edu.ucsd.flappycow.model.components.Virus;

public class PowerUpsFactory {
    public PowerUp createPowerUp(FactoryItemTypes factoryItemTypes, FactoryPojo factoryPojo){
        switch (factoryItemTypes){
            case COIN:
                return new Coin(factoryPojo.getGameActivity(), factoryPojo.getViewWidth(), factoryPojo.getSpeedX() );
            case VIRUS:
                return new Virus(factoryPojo.getGameActivity(), factoryPojo.getViewWidth(), factoryPojo.getSpeedX());
            case TOAST:
                return new Toast(factoryPojo.getGameActivity(), factoryPojo.getViewHeight(), factoryPojo.getViewWidth(),factoryPojo.getSpeedX());
        }
        return null;
    }

    public PowerUpsFactory() {
    }
}
