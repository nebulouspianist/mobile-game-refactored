/**
 * The pauseButton
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.model.components;

import edu.ucsd.flappycow.R;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.util.Util;

public class PauseButton extends Sprite {
    protected  int viewWidth;
    public PauseButton(GameActivity gameActivity, int viewWidth) {
        super(gameActivity);
        this.viewWidth = viewWidth;
        this.bitmap = Util.getScaledBitmapAlpha8(gameActivity, R.drawable.pause_button);
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }

    /**
     * Sets the button in the right upper corner.
     */
    @Override
    public void move() {
        this.x = viewWidth - this.width;
        this.y = 0;
    }
}