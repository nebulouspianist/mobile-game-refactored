package edu.ucsd.flappycow.pojo;

import edu.ucsd.flappycow.presenter.GameActivity;

public class FactoryPojo {
    private GameActivity gameActivity;
    private int viewHeight;
    private int viewWidth;
    private int speedX;

    public FactoryPojo(GameActivity gameActivity, int viewHeight, int viewWidth, int speedX) {
        this.gameActivity = gameActivity;
        this.viewHeight = viewHeight;
        this.viewWidth = viewWidth;
        this.speedX = speedX;
    }

    public FactoryPojo(GameActivity gameActivity, int speedX) {
        this.gameActivity = gameActivity;
        this.speedX = speedX;
    }


    public GameActivity getGameActivity() {
        return gameActivity;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public int getSpeedX() {
        return speedX;
    }
}
