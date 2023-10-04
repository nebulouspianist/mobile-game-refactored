package edu.ucsd.flappycow.model.connectors;

import static edu.ucsd.flappycow.gameData.SoundEnum.PASS_SOUND;

import android.content.Context;
import android.os.Message;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.view.connectors.GameView;
import edu.ucsd.flappycow.presenter.MainActivity;
import edu.ucsd.flappycow.presenter.ModelViewMediator;
import edu.ucsd.flappycow.R;
import edu.ucsd.flappycow.factory.FactoryItemTypes;
import edu.ucsd.flappycow.factory.PowerUpsFactory;
import edu.ucsd.flappycow.gameData.GameEvent;

import edu.ucsd.flappycow.gameData.MusicManagerLoadEvent;
import edu.ucsd.flappycow.gameData.MusicManagerPlayEvent;
import edu.ucsd.flappycow.gameData.GameEventEnum;

import edu.ucsd.flappycow.gameData.GameOverGameOver;
import edu.ucsd.flappycow.gameData.GameOverPause;
import edu.ucsd.flappycow.gameData.PlayerDeadFallCallViewDraw;
import edu.ucsd.flappycow.gameData.SetUpReviveHideLog;
import edu.ucsd.flappycow.gameData.SetUpReviveOnSetUp;

import edu.ucsd.flappycow.interfaces.IObserver;
import edu.ucsd.flappycow.interfaces.ISubject;
import edu.ucsd.flappycow.interfaces.SubjectImpl;
import edu.ucsd.flappycow.model.components.AchievementBox;
import edu.ucsd.flappycow.model.components.Sprite;
import edu.ucsd.flappycow.pojo.FactoryPojo;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.flappycow.model.components.Background;
import edu.ucsd.flappycow.model.components.Frontground;
import edu.ucsd.flappycow.model.components.NyanCat;
import edu.ucsd.flappycow.model.components.Obstacle;
import edu.ucsd.flappycow.model.components.PauseButton;
import edu.ucsd.flappycow.model.components.PlayableCharacter;
import edu.ucsd.flappycow.model.components.PowerUp;
import edu.ucsd.flappycow.model.components.Toast;

public class GameModel implements ISubject<GameEvent>, IObserver<GameEvent> {

    private SubjectImpl<GameEvent> subjImpl = new SubjectImpl();
    @Override
    public void register(IObserver<GameEvent> observer) { subjImpl.register(observer); }

    @Override
    public void remove(IObserver<GameEvent> observer) { subjImpl.remove(observer); }


    @Override
    public void onUpdate(GameEvent data) {
        switch (data.event_type) {
            case GameEventEnum.CREATE_POWERUP:
                createPowerUp();
                break;
            case GameEventEnum.INCREASE_POINTS:
                increasePoints();
                break;
            case GameEventEnum.MUSIC_MANAGER_LOAD_EVENT:
            case GameEventEnum.MUSIC_MANAGER_PLAY_EVENT:
                subjImpl.notify(data);
                break;
            case GameEventEnum.GAMEOVER_OVERALL:
                this.gameOver();
                break;
            default:
                subjImpl.notify(data);
                break;
        }
    }

    public static final long UPDATE_INTERVAL = 50;

    /**
     * This will increase the revive price
     */
    public int numberOfRevive = 1;
    GameActivity gameActivity;

    public void setBackground(Background background) {
        this.background = background;
    }

    public void setFrontground(Frontground frontground) {
        this.frontground = frontground;
    }

    public void setPauseButton(PauseButton pauseButton) {
        this.pauseButton = pauseButton;
    }

    public void setPlayer(PlayableCharacter player) {
        this.player = player;
    }

    public void setAccomplishmentBox(AchievementBox accomplishmentBox) {
        this.accomplishmentBox = accomplishmentBox;
    }

    ModelViewMediator modelViewMediator;

    private Background background;
    private Frontground frontground;
    private PauseButton pauseButton;
    private PlayableCharacter player;   // m


    //TODO: need to change back to private
    public List<Obstacle> obstacles = new ArrayList<Obstacle>();
    public List<PowerUp> powerUps = new ArrayList<PowerUp>();

    public AchievementBox accomplishmentBox;

    private static final int SOUND_VOLUME_DIVIDER = 3;

    public PowerUpsFactory powerUpsFactory;

    private PassMediator passMediator;

    private CollisionMediator collisionMediator;

    public int coins;

    public GameModel(Context context) {
        this.gameActivity = (GameActivity) context;
        register(gameActivity);
        powerUpsFactory = new PowerUpsFactory();

        passMediator = new PassMediator(this);
        collisionMediator = new CollisionMediator(gameActivity, this);

    }


    public void setMediator(ModelViewMediator modelViewMediator){
        this.modelViewMediator = modelViewMediator;
    }

    /**
     * Let the player fall to the ground
     */
    private void playerDeadFall() {
        player.dead();
        do {
            player.move();

            subjImpl.notify(new PlayerDeadFallCallViewDraw(GameEventEnum.playerDeadFall_callViewDraw_EVENT));

            // sleep
            try {
                Thread.sleep(UPDATE_INTERVAL / 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!player.isTouchingGround(modelViewMediator.getViewHeight()));
    }

    /**
     * Checks whether an obstacle is passed.
     */
    public void checkPasses() {
        passMediator.checkPasses(gameActivity, obstacles, player);
    }

    /**
     * Creates a toast with a certain chance
     */
    public void createPowerUp() {
        FactoryPojo factoryPojo = createFactoryPojo();

        if (shouldCreateToast()) {
            createToast(factoryPojo);
        } else if (shouldCreateCoin()) {
            createCoin(factoryPojo);
        } else if (shouldCreateVirus()) {
            createVirus(factoryPojo);
        }
    }

    private FactoryPojo createFactoryPojo() {
        return new FactoryPojo(gameActivity, modelViewMediator.getViewHeight(), modelViewMediator.getViewWidth(), getSpeedX());
    }

    private boolean shouldCreateToast() {
        return accomplishmentBox.points >= Toast.POINTS_TO_TOAST && !(player instanceof NyanCat);
    }

    private boolean shouldCreateCoin() {
        return powerUps.size() < 1 && Math.random() * 100 < 20;
    }

    private boolean shouldCreateVirus() {
        return powerUps.size() < 1 && Math.random() * 100 < 10;
    }

    private void createToast(FactoryPojo factoryPojo) {
        powerUps.add(powerUpsFactory.createPowerUp(FactoryItemTypes.TOAST, factoryPojo));
    }

    private void createCoin(FactoryPojo factoryPojo) {
        powerUps.add(powerUpsFactory.createPowerUp(FactoryItemTypes.COIN, factoryPojo));
    }

    private void createVirus(FactoryPojo factoryPojo) {
        powerUps.add(powerUpsFactory.createPowerUp(FactoryItemTypes.VIRUS, factoryPojo));
    }

    /**
     * Checks whether the obstacles or powerUps are out of range and deletes them
     */
    public void checkOutOfRange() {
        for (int i = 0; i < obstacles.size(); i++) {
            if (this.obstacles.get(i).isOutOfRange()) {
                this.obstacles.remove(i);
                i--;
            }
        }
        for (int i = 0; i < powerUps.size(); i++) {
            if (this.powerUps.get(i).isOutOfRange()) {
                this.powerUps.remove(i);
                i--;
            }
        }
    }

    /**
     * Checks collisions and performs the action
     */
    public void checkCollision() {
        collisionMediator.checkCollision(gameActivity, obstacles, player, powerUps, modelViewMediator.getViewHeight(), getCollisionTolerance());
    }

    /**
     * if no obstacle is present a new one is created
     */
    public void createObstacle() {
        if (obstacles.size() < 1) {
            obstacles.add(new Obstacle(gameActivity, getSpeedX()));
        }
    }

    /**
     * Update sprite movements
     */
    public void move() {
        for (Obstacle o : obstacles) {
            o.setSpeedX(-getSpeedX());
            o.move();
        }
        for (PowerUp p : powerUps) {
            p.move();
        }

        background.setSpeedX(-getSpeedX() / 2);
        background.move();

        frontground.setSpeedX(-getSpeedX() * 4 / 3);
        frontground.move();

        pauseButton.move();

        player.move();
    }

    /**
     * return the speed of the obstacles/cow
     */
    public int getSpeedX() {
        // 16 @ 720x1280 px
        int speedDefault = modelViewMediator.getViewWidth() / 45;

        //modelViewMediator.getWidth()

        // 1,2 every 4 points @ 720x1280 px
        int speedIncrease = (int) (modelViewMediator.getViewWidth() / 600f * (accomplishmentBox.points / 4));

        int speed = speedDefault + speedIncrease;

        return Math.min(speed, 2 * speedDefault);
    }

    /**
     * Let's the player fall down dead, makes sure the runcycle stops
     * and invokes the next method for the dialog and stuff.
     */
    public void gameOver() {

        subjImpl.notify(new GameOverPause(GameEventEnum.gameOver_pause_EVENT));
        playerDeadFall();
        subjImpl.notify(new GameOverGameOver(GameEventEnum.gameOver_gameOver_EVENT));

    }

    public void revive() {
        numberOfRevive++;

        // This needs to run another thread, so the dialog can close.
        new Thread(this::setupRevive).start();
    }

    /**
     * Sets the player into startposition
     * Removes obstacles.
     * Let's the character blink a few times.
     */
    private void setupRevive() {
        //gameActivity.view.gameOverDialog.hide();
       subjImpl.notify(new SetUpReviveHideLog(GameEventEnum.setupRevive_hideDialog_EVENT));



        player.setY(modelViewMediator.getViewHeight() / 2 - player.getWidth() / 2);
        player.setX(modelViewMediator.getViewWidth() / 6);

        obstacles.clear();
        powerUps.clear();
        player.revive();
        //gameActivity.onSetupRevive();
        subjImpl.notify(new SetUpReviveOnSetUp(GameEventEnum.SetupRevive_onSetupRevive_EVENT));

    }

    /**
     * A value for the position and size of the onScreen score Text
     */
    public int getScoreTextMetrics() {
        return (int) (modelViewMediator.getViewHeight() / 21.0f);
    }

    public PlayableCharacter getPlayer() {
        return this.player;
    }

    public boolean PauseButtonTouching(int x,int y){
        return this.pauseButton.isTouching(x,y);
    }

    public void playerOnTap(){
        this.player.onTap();
    }

    public boolean modelPlayerIsDead(){
        return player.isDead();
    }

    public void modelPlayerMove(){
        player.move();
    }

    public void modelPlayerOnTap(){
        player.onTap();
    }

    public PauseButton getPauseButton() { return this.pauseButton; }

    public void modelPauseButtonMove(){
        pauseButton.move();
    }

    public int getBackgroundX(){
        return this.background.getX();
    }

    public int getBackgroundY(){
        return this.background.getY();
    }


    public int getFrontgroundX(){
        return this.frontground.getX();
    }

    public int getFrontgroundY(){
        return this.background.getY();
    }

    public int getPauseButtonX(){
        return this.pauseButton.getX();
    }

    public int getPauseButtonY(){
        return this.pauseButton.getY();
    }


    // Lab5: onCollision isColliding onPass  isPassed Move from sprite to GameModel
    // use two parameters means the items which will collide
    public boolean isColliding(Sprite s, Sprite sprite) { //first: original caller
        //spriteBak = sprite;
        if (s.getX() + getCollisionTolerance() < sprite.getX() + sprite.getWidth()
                && s.getX() + s.getWidth() > sprite.getX() + getCollisionTolerance()
                && s.getY() + getCollisionTolerance() < sprite.getY() + sprite.getHeight()
                && s.getY() + s.getHeight() > sprite.getY() + getCollisionTolerance()) {
            return true;
        }
        return false;
    }

    public void changeToSick(PlayableCharacter player) {
        player.wearMask();
    }

    public void changeToNyanCat(Toast t, PlayableCharacter player) {
        accomplishmentBox.achievement_toastification = true;
        GameView.handler.sendMessage(Message.obtain(GameView.handler, 1, R.string.toast_achievement_toastification, GameView.SHOW_TOAST));

        PlayableCharacter tmp = player;
        player = new NyanCat(gameActivity, t.getViewHeight(), t.getWidth());
        player.setX(tmp.getX());
        player.setY(tmp.getY());
        player.setSpeedX(tmp.getSpeedX());
        player.setSpeedY(tmp.getSpeedY());

        //TODO:
        GameView.musicManager.musicShouldPlay = true;
        GameView.musicManager.musicPlayer.start();

    }

    private int getCollisionTolerance() {
        // 25 @ 720x1280 px
        // TODO: Mediator add gameActivity
        return gameActivity.getResources().getDisplayMetrics().heightPixels / 50;
    }

    public boolean isPassed(Sprite s, PlayableCharacter player){
        return s.getX() + s.getWidth() < player.getX();
    }
    public boolean isPassedForObstacle(Obstacle o, PlayableCharacter player){
        return isPassed(o.getSpider(),player) && isPassed(o.getWoodLog(), player);
    }
    public void onPassForObstacle(Obstacle o) {
        subjImpl.notify(new MusicManagerLoadEvent(PASS_SOUND, gameActivity, R.raw.coin, 1));
        if (!o.isAlreadyPassed) {
            o.isAlreadyPassed = true;
            increasePoints();
            subjImpl.notify(new MusicManagerPlayEvent(
                    PASS_SOUND,
                    MainActivity.volume / SOUND_VOLUME_DIVIDER,
                    MainActivity.volume / SOUND_VOLUME_DIVIDER,
                    0, 0, 1));
        }
    }


    // Lab5: increaseCoin/points... move from GameActivity to GameModel
    // after main logic send a message to GameActivity to call Myhandler

    public void increaseCoin() {
        System.out.println("------------------------DBG--------------------");
        System.out.println(coins);
        coins++;
        if (coins >= 50 && !accomplishmentBox.achievement_50_coins) {
            accomplishmentBox.achievement_50_coins = true;
            //TODO:
            GameView.handler.sendMessage(Message.obtain(GameView.handler, 1, R.string.toast_achievement_50_coins, GameView.SHOW_TOAST));
        }
    }

    public void decreaseCoin() {
        coins--;
    }

    /**
     * What should happen, when an obstacle is passed?
     */
    public void increasePoints() {
        accomplishmentBox.points++;

        this.getPlayer().upgradeBitmap(accomplishmentBox.points);

        if (accomplishmentBox.points >= AchievementBox.BRONZE_POINTS) {
            if (!accomplishmentBox.achievement_bronze) {
                accomplishmentBox.achievement_bronze = true;
                //TODO:
                GameView.handler.sendMessage(Message.obtain(GameView.handler, GameView.SHOW_TOAST, R.string.toast_achievement_bronze, GameView.SHOW_TOAST));
            }

            if (accomplishmentBox.points >= AchievementBox.SILVER_POINTS) {
                if (!accomplishmentBox.achievement_silver) {
                    accomplishmentBox.achievement_silver = true;
                    //TODO:
                    GameView.handler.sendMessage(Message.obtain(GameView.handler, GameView.SHOW_TOAST, R.string.toast_achievement_silver, GameView.SHOW_TOAST));
                }

                if (accomplishmentBox.points >= AchievementBox.GOLD_POINTS) {
                    if (!accomplishmentBox.achievement_gold) {
                        accomplishmentBox.achievement_gold = true;
                        //TODO:
                        GameView.handler.sendMessage(Message.obtain(GameView.handler, GameView.SHOW_TOAST, R.string.toast_achievement_gold, GameView.SHOW_TOAST));
                    }
                }
            }
        }
    }

    public void decreasePoints() {
        accomplishmentBox.points--;
    }

}
