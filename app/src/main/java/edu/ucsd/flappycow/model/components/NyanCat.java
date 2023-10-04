/**
 * Nyan Cat character
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 * <p>
 * Nyan Cat was drawn by Christopher Torres and momo momo remixed the music by daniwell
 */

package edu.ucsd.flappycow.model.components;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import edu.ucsd.flappycow.R;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.util.Util;

public class NyanCat extends PlayableCharacter {

    /** Static bitmap to reduce memory usage */
    public static Bitmap globalBitmap;

    /** The rainbow tail behind the cat */
    private Rainbow rainbow;

    public NyanCat(GameActivity gameActivity, int viewHeight, int viewWidth) {
        super(gameActivity, viewHeight, viewWidth);
        if (globalBitmap == null) {
            globalBitmap = Util.getScaledBitmapAlpha8(gameActivity, R.drawable.nyan_cat);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight() / 2;
        this.y = gameActivity.getResources().getDisplayMetrics().heightPixels / 2;

        this.rainbow = new Rainbow(gameActivity);
    }

    /**
     * Moves itself via super.move
     * and moves the rainbow and manages its frames
     */
    @Override
    public void move() {
        super.move();

        if (rainbow != null) {
            manageRainbowMovement();
        }
    }

    private void manageRainbowMovement() {
        rainbow.y = this.y;        // nyan cat and rainbow bitmap have the same height
        rainbow.x = this.x - rainbow.width;
        rainbow.move();

        // manage frames of the rainbow
        if (speedY > getTabSpeed(viewHeight) / 3 && speedY < getMaxSpeed(viewHeight) * 1 / 3) {
            rainbow.row = 0;
        } else if (speedY > 0) {
            rainbow.row = 1;
        } else {
            rainbow.row = 2;
        }
    }

    /**
     * Draws itself via super.draw
     * and the rainbow.
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (rainbow != null && !isDead) {
            rainbow.draw(canvas);
        }
    }

    /**
     * Calls super.dead,
     * removes the rainbow tail
     * and set the bitmapframe to a dead cat -.-
     */
    @Override
    public void dead() {
        super.dead();
        this.row = 1;

        // Maybe an explosion
    }

    @Override
    public void revive() {
        super.revive();
        manageRainbowMovement();
    }

}
