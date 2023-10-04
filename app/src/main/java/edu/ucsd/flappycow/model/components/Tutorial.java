/**
 * The tutorial that says you should tap
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.model.components;

import android.graphics.Bitmap;

import edu.ucsd.flappycow.R;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.util.Util;

public class Tutorial extends Sprite {
    public static Bitmap globalBitmap;
    private final int viewHeight;
    private final int viewWidth;
    public Tutorial(GameActivity gameActivity, int height, int width) {
        super(gameActivity);
        if (globalBitmap == null) {
            globalBitmap = Util.getScaledBitmapAlpha8(gameActivity, R.drawable.tutorial);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
        viewHeight = height;
        viewWidth = width;
    }

    /**
     * Sets the position to the center of the view.
     */
    @Override
    public void move() {
        this.x = viewWidth / 2 - this.width / 2;
        this.y = viewHeight / 2 - this.height / 2;
    }

}
