/**
 * The abstract spriteclass for power-ups
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.model.components;

import edu.ucsd.flappycow.presenter.GameActivity;

public abstract class PowerUp extends Sprite {

    public PowerUp(GameActivity gameActivity, int viewWidth, int speedX) {
        super(gameActivity);
        this.x = viewWidth * 4 / 5;
        this.y = 0 - this.height;
        this.speedX = -speedX;
        this.speedY = (int) (speedX * (Math.random() + 0.5));
    }

}