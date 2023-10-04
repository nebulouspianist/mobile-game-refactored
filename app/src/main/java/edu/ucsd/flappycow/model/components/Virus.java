package edu.ucsd.flappycow.model.components;

import android.graphics.Bitmap;

import edu.ucsd.flappycow.R;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.util.Util;

public class Virus extends PowerUp {
    public static Bitmap globalBitmap;

    public Virus(GameActivity gameActivity, int width, int speedX) {
        super(gameActivity, width, speedX);
        if (globalBitmap == null) {
            globalBitmap = Util.getScaledBitmapAlpha8(gameActivity, R.drawable.virus);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }

    /**
     * When eaten the player will become infected.
     */
//    @Override
//    public void onCollision() {
//        super.onCollision();
//        gameActivity.decreasePoints();
//
//        changeToSick((PlayableCharacter) spriteBak);
//    }
    public void changeToSick(PlayableCharacter player) {
        player.wearMask();

    }
}
