package edu.ucsd.flappycow.model.connectors;

import edu.ucsd.flappycow.model.components.Obstacle;
import edu.ucsd.flappycow.model.components.PlayableCharacter;
import edu.ucsd.flappycow.model.components.Sprite;

public class IsCollidingMediator {
    public boolean isColliding(Sprite s, Sprite sprite, int collisionTolerance) { //first: original caller
        //spriteBak = sprite;
        if (s.getX() + collisionTolerance < sprite.getX() + sprite.getWidth()
                && s.getX() + s.getWidth() > sprite.getX() + collisionTolerance
                && s.getY() + collisionTolerance < sprite.getY() + sprite.getHeight()
                && s.getY() + s.getHeight() > sprite.getY() + collisionTolerance) {
            return true;
        }
        return false;
    }
    public boolean isCollidingForObstacle(Obstacle o, PlayableCharacter player, int collisionTolerance){ //first: original caller
        return isColliding(o.getSpider(),player, collisionTolerance) || isColliding(o.getWoodLog(), player, collisionTolerance);
    }
}
