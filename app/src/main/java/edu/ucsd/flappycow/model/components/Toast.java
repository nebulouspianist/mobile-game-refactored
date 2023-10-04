/**
 * A yummy toast
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.model.components;

import android.graphics.Bitmap;

import edu.ucsd.flappycow.R;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.util.Util;

public class Toast extends PowerUp {

    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;

    public static final int POINTS_TO_TOAST = 42;

    private int viewHeight;

    public Toast( GameActivity gameActivity,int height, int width, int speedX) {
        super(gameActivity, width, speedX);
        this.viewHeight = height;
        if (globalBitmap == null) {
            globalBitmap = Util.getScaledBitmapAlpha8(gameActivity, R.drawable.toast);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }

    /**
     * When eaten the player will turn into nyan cat.
     */
//    @Override
//    public void onCollision() {
//        super.onCollision();
//
//        changeToNyanCat((PlayableCharacter) spriteBak);
//    }
//
//    public void changeToNyanCat(PlayableCharacter player) {
//        gameActivity.accomplishmentBox.achievement_toastification = true;
////        gameActivity.handler.sendMessage(Message.obtain(gameActivity.handler, 1, R.string.toast_achievement_toastification, GameView.SHOW_TOAST));
//
//        PlayableCharacter tmp = player;
//        player = new NyanCat(gameActivity, viewHeight, this.getWidth());
//        player.setX(tmp.getX());
//        player.setY(tmp.getY());
//        player.setSpeedX(tmp.getSpeedX());
//        player.setSpeedY(tmp.getSpeedY());
//
//        GameView.musicManager.musicShouldPlay = true;
//        GameView.musicManager.musicPlayer.start();
//
//    }

    public int getViewHeight(){
        return viewHeight;
    }

}