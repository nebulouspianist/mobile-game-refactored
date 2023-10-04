package edu.ucsd.flappycow.factory;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.model.components.Spider;
import edu.ucsd.flappycow.model.components.Sprite;
import edu.ucsd.flappycow.model.components.WoodLog;

public class ObstacleItemFactory {
    public Sprite createObstacle(ObstacleTypeEnum obstacleTypeEnum, GameActivity gameActivity){
        switch (obstacleTypeEnum){
            case WOODLOG:
                return new WoodLog(gameActivity);
            case SPIDER:
                return new Spider(gameActivity);
        }
        return null;
    }
}
