package edu.ucsd.flappycow.model.connectors;

import edu.ucsd.flappycow.model.components.Obstacle;
import edu.ucsd.flappycow.model.components.PlayableCharacter;
import edu.ucsd.flappycow.model.components.Sprite;

public class IsPassedMediator {

    public boolean isPassed(Sprite s, PlayableCharacter player){
        return s.getX() + s.getWidth() < player.getX();
    }
    public boolean isPassedForObstacle(Obstacle o, PlayableCharacter player){
        return isPassed(o.getSpider(),player) && isPassed(o.getWoodLog(), player);
    }

}

