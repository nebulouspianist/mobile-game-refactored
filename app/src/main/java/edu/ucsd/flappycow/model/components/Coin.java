/**
 * A Coin
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.model.components;

import static edu.ucsd.flappycow.gameData.SoundEnum.COIN_SOUND;

import android.graphics.Bitmap;

import edu.ucsd.flappycow.*;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.util.Util;
import edu.ucsd.flappycow.gameData.GameEvent;
import edu.ucsd.flappycow.gameData.MusicManagerLoadEvent;
import edu.ucsd.flappycow.interfaces.IObserver;
import edu.ucsd.flappycow.interfaces.ISubject;
import edu.ucsd.flappycow.interfaces.SubjectImpl;

public class Coin extends PowerUp implements ISubject<GameEvent> {
    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;
    private SubjectImpl<GameEvent> subjImpl = new SubjectImpl();
    @Override
    public void register(IObserver<GameEvent> observer) { subjImpl.register(observer); }

    @Override
    public void remove(IObserver<GameEvent> observer) { subjImpl.remove(observer); }
//    public static int coinSound = -1;



    public Coin(GameActivity gameActivity, int width, int speedX) {
        super(gameActivity, width, speedX);
        register(gameActivity);

        if (globalBitmap == null) {
            globalBitmap = Util.getScaledBitmapAlpha8(gameActivity, R.drawable.coin);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth() / (colNr = 12);
        this.height = this.bitmap.getHeight();
        this.frameTime = 1;

        subjImpl.notify(new MusicManagerLoadEvent(COIN_SOUND, gameActivity, R.raw.coin, 1));
    }

    @Override
    public void move() {
        changeToNextFrame();
        super.move();
    }
}
