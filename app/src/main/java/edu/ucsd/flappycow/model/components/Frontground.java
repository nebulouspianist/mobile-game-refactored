/**
 * Manages the bitmap at the front
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.model.components;

import android.graphics.Bitmap;

import edu.ucsd.flappycow.R;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.util.Util;

public class Frontground extends Background {
    /**
     * Height of the ground relative to the height of the bitmap
     */
    public static final float GROUND_HEIGHT = (1f * /*45*/ 35) / 720;

    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;

    public Frontground( GameActivity gameActivity) {
        super(gameActivity);
        if (globalBitmap == null) {
            globalBitmap = Util.getDownScaledBitmapAlpha8(gameActivity, R.drawable.fg);
        }
        this.bitmap = globalBitmap;
    }

}
