/**
 * The cow that is controlled by the player
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.model.components;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import edu.ucsd.flappycow.R;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.view.connectors.GameView;
import edu.ucsd.flappycow.presenter.MainActivity;
import edu.ucsd.flappycow.util.Util;

public class Cow extends PlayableCharacter {

    private static final int POINTS_TO_SIR = 23;
    private static final int POINTS_TO_COOL = 35;

    /** Static bitmap to reduce memory usage. */
    public static Bitmap globalBitmap;

    /** The moo sound */
    private static int sound = -1;

    /** sunglasses, hats and stuff */
    private Accessory accessory;

    public Cow(GameActivity gameActivity, int viewHeight, int viewWidth) {
        super(gameActivity, viewHeight, viewWidth);
        if (globalBitmap == null) {
            globalBitmap = Util.getScaledBitmapAlpha8(gameActivity, R.drawable.cow);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth() / (colNr = 8);    // The image has 8 frames in a row
        this.height = this.bitmap.getHeight() / 4;            // and 4 in a column
        this.frameTime = 3;        // the frame will change every 3 runs
        this.y = gameActivity.getResources().getDisplayMetrics().heightPixels / 2;    // Startposition in in the middle of the screen

        if (sound == -1) {
            sound = GameView.musicManager.soundPool.load(gameActivity, R.raw.cow, 1);
        }

        this.accessory = new Accessory(gameActivity);
    }

    private void playSound() {
        //TODO: DEBUG for jump sound
        GameView.musicManager.soundPool.play(sound, MainActivity.volume, MainActivity.volume, 0, 0, 1);
    }

    @Override
    public void onTap() {
        super.onTap();
        playSound();
    }

    /**
     * Calls super.move
     * and manages the frames. (flattering cape)
     */


    public void move() {
        changeToNextFrame();
        super.move();

        // manage frames
        if (row != 3) {
            // not dead
            if (speedY > getTabSpeed(viewHeight) / 3 && speedY < getMaxSpeed(viewHeight) * 1 / 3) {
                row = 0;
            } else if (speedY > 0) {
                row = 1;
            } else {
                row = 2;
            }
        }

        if (this.accessory != null) {
            this.accessory.moveTo(this.x, this.y);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.accessory != null && !isDead) {
            this.accessory.draw(canvas);
        }
    }

    /**
     * Calls super.dead
     * And changes the frame to a dead cow -.-
     */
    @Override
    public void dead() {
        this.row = 3;
        this.frameTime = 3;
        super.dead();
    }

    @Override
    public void revive() {
        super.revive();
        this.accessory.setBitmap(Util.getScaledBitmapAlpha8(gameActivity, R.drawable.accessory_scumbag));
    }

    @Override
    public void upgradeBitmap(int points) {
        super.upgradeBitmap(points);
        if (points == POINTS_TO_SIR) {
            this.accessory.setBitmap(Util.getScaledBitmapAlpha8(gameActivity, R.drawable.accessory_sir));
        } else if (points == POINTS_TO_COOL) {
            this.accessory.setBitmap(Util.getScaledBitmapAlpha8(gameActivity, R.drawable.accessory_sunglasses));
        }
    }

    @Override
    public void wearMask() {
        super.wearMask();
        this.accessory.setBitmap(Util.getScaledBitmapAlpha8(gameActivity, R.drawable.mask));
    }
}
