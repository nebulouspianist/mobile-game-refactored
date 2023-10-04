/**
 * An obstacle: spider + logHead
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.model.components;

import static edu.ucsd.flappycow.gameData.SoundEnum.COLLIDE_SOUND;
import static edu.ucsd.flappycow.gameData.SoundEnum.PASS_SOUND;

import android.graphics.Canvas;

import edu.ucsd.flappycow.R;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.factory.ObstacleItemFactory;
import edu.ucsd.flappycow.factory.ObstacleTypeEnum;
import edu.ucsd.flappycow.gameData.GameEvent;
import edu.ucsd.flappycow.gameData.MusicManagerLoadEvent;
import edu.ucsd.flappycow.interfaces.IObserver;
import edu.ucsd.flappycow.interfaces.ISubject;
import edu.ucsd.flappycow.interfaces.SubjectImpl;

public class Obstacle extends Sprite implements ISubject<GameEvent> {
    private SubjectImpl<GameEvent> subjImpl = new SubjectImpl();
    @Override
    public void register(IObserver<GameEvent> observer) { subjImpl.register(observer); }
    @Override
    public void remove(IObserver<GameEvent> observer) { subjImpl.remove(observer); }
    private Spider spider;
    private WoodLog log;

    private int speed;

    /** Necessary so the onPass method is just called once */
    public boolean isAlreadyPassed = false;

    public Sprite spriteBak;

    private ObstacleItemFactory obstacleItemFactory;

    public Obstacle(GameActivity gameActivity, int speed) {
        super(gameActivity);
        this.speed = speed;
        obstacleItemFactory = new ObstacleItemFactory();
        spider = (Spider) obstacleItemFactory.createObstacle(ObstacleTypeEnum.SPIDER, gameActivity);
        log = (WoodLog) obstacleItemFactory.createObstacle(ObstacleTypeEnum.WOODLOG, gameActivity);

        subjImpl.register(gameActivity) ;
        subjImpl.notify(new MusicManagerLoadEvent(COLLIDE_SOUND, gameActivity, R.raw.crash, 1));
        subjImpl.notify(new MusicManagerLoadEvent(PASS_SOUND, gameActivity, R.raw.pass, 1));
        initPos();
    }

    /**
     * Creates a spider and a wooden log at the right of the screen.
     * With a certain gap between them.
     * The vertical position is in a certain area random.
     */
    private void initPos() {
        int height = gameActivity.getResources().getDisplayMetrics().heightPixels;


        int gab = height / 4 - speed;

        if (gab < height / 5) {
            gab = height / 5;
        }
        int random = (int) (Math.random() * height * 2 / 5);
        int y1 = (height / 10) + random - spider.height;
        int y2 = (height / 10) + random + gab;

        spider.init(gameActivity.getResources().getDisplayMetrics().widthPixels, y1);
        log.init(gameActivity.getResources().getDisplayMetrics().widthPixels, y2);
    }

    /**
     * Draws spider and log.
     */
    @Override
    public void draw(Canvas canvas) {
        spider.draw(canvas);
        log.draw(canvas);
    }

    /**
     * Checks whether both, spider and log, are out of range.
     */
    @Override
    public boolean isOutOfRange() {
        return spider.isOutOfRange() && log.isOutOfRange();
    }

    /**
     * Checks whether the spider or the log is colliding with the sprite.
     */
//   @Override
//    public boolean isColliding(Sprite sprite) {
//        spriteBak = sprite;
//        return spider.isColliding(sprite) || log.isColliding(sprite);
//    }

    /**
     * Moves both, spider and log.
     */
    @Override
    public void move() {
        spider.move();
        log.move();
    }

    /**
     * Sets the speed of the spider and the log.
     */
    @Override
    public void setSpeedX(float speedX) {
        spider.setSpeedX(speedX);
        log.setSpeedX(speedX);
    }

    /**
     * Checks whether the spider and the log are passed.
     */
//    @Override
//    public boolean isPassed(PlayableCharacter player) {
//        return spider.isPassed(player) && log.isPassed(player);
//    }
//
//    private static final int SOUND_VOLUME_DIVIDER = 3;
//
//    /**
//     * Will call obstaclePassed of the game, if this is the first pass of this obstacle.
//     */
//    public void onPass() {
//        if (!isAlreadyPassed) {
//            isAlreadyPassed = true;
//            gameActivity.increasePoints();
//            GameView.musicManager.soundPool.play(passSound, MainActivity.volume / SOUND_VOLUME_DIVIDER, MainActivity.volume / SOUND_VOLUME_DIVIDER, 0, 0, 1);
//        }
//    }
//
//    @Override
//    public void onCollision() {
//        super.onCollision();
//        GameView.musicManager.soundPool.play(collideSound, MainActivity.volume / SOUND_VOLUME_DIVIDER, MainActivity.volume / SOUND_VOLUME_DIVIDER, 0, 0, 1);
//    }

    public Spider getSpider(){
        return spider;
    }
    public WoodLog getWoodLog(){
        return log;
    }
}
