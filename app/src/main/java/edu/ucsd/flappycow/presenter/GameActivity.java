/*
 * The Game Activity
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.presenter;

import static edu.ucsd.flappycow.view.connectors.GameView.handler;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import edu.ucsd.flappycow.R;
import edu.ucsd.flappycow.builder.GameModelBuilder;
import edu.ucsd.flappycow.builder.GameViewBuilder;
import edu.ucsd.flappycow.gameData.ChangeToNyanCatEvent;
import edu.ucsd.flappycow.gameData.ChangeToSickEvent;
import edu.ucsd.flappycow.gameData.GameEvent;
import edu.ucsd.flappycow.gameData.GameEventEnum;

import edu.ucsd.flappycow.gameData.MusicManagerLoadEvent;
import edu.ucsd.flappycow.gameData.MusicManagerPlayEvent;
import edu.ucsd.flappycow.interfaces.IObserver;
import edu.ucsd.flappycow.model.connectors.GameModel;
import edu.ucsd.flappycow.util.Contract;
import edu.ucsd.flappycow.view.connectors.GameView;


public class GameActivity extends Activity implements IObserver<GameEvent> {

    @Override
    public void onUpdate(GameEvent event) {
// Ensure that the event is not null
        Contract.require(event != null, "event cannot be null");

        // Check if event.event_type is a valid GameEventEnum type
        boolean validEventType = false;
        for (Field field : GameEventEnum.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.getType() == int.class && field.getInt(null) == event.event_type) {
                    validEventType = true;
                    break;
                }
            } catch (IllegalAccessException e) {
                // Ignore, continue to next field
            }
        }

        Contract.require(validEventType, "event_type must be one of the defined types in GameEventEnum");

        switch (event.event_type) {
            case GameEventEnum.MUSIC_MANAGER_LOAD_EVENT:
                view.musicManagerLoad((MusicManagerLoadEvent) event);
                break;
            case GameEventEnum.MUSIC_MANAGER_PLAY_EVENT:
                view.musicManagerPlay((MusicManagerPlayEvent) event);
                break;

            case GameEventEnum.playerDeadFall_callViewDraw_EVENT:
                callViewDraw();
                break;
            case GameEventEnum.gameOver_pause_EVENT:
                pause();
                break;
            case GameEventEnum.gameOver_gameOver_EVENT:
                gameOver();
                break;
            case GameEventEnum.setupRevive_hideDialog_EVENT:
                view.gameOverDialog.hide();
                break;
            case GameEventEnum.SetupRevive_onSetupRevive_EVENT:
                onSetupRevive();
                break;
            case GameEventEnum.PLAYER_MOVE:
                this.playerMove();
                break;
            case GameEventEnum.PAUSEBUTTON_MOVE:
                this.pauseButtonMove();
                break;
            case GameEventEnum.RESUME:
                this.resume();
                break;
            case GameEventEnum.GAMEOVER_COUNTER:
                this.gameOverCounter++;
                break;
//            case GameEventEnum.CREATE_POWERUP:
//                this.createPowerUp();
//                break;
//            case GameEventEnum.INCREASE_POINTS:
//                this.increasePoints();
//                break;
            case GameEventEnum.GAMEOVER_OVERALL:
                this.gameOverOverAll();
                break;
            case GameEventEnum.INCREASE_COINS:
                this.increaseCoin();
                break;
            case GameEventEnum.DECREASE_POINTS:
                this.decreasePoints();
                break;
            case GameEventEnum.CHANGETO_SICK:
                this.changeToSick((ChangeToSickEvent)event);
                break;
            case GameEventEnum.CHANGETO_CAT:
                this.changeToCat((ChangeToNyanCatEvent)event);
                break;
            default:
                throw new RuntimeException();
        }

        // Ensure that the event was handled successfully
        Contract.ensure(true, "event handling must be successful");

    }

    /**
     * Name of the SharedPreference that saves the medals
     */
    public static final String coin_save = "coin_save";

    /**
     * Key that saves the medal
     */
    public static final String coin_key = "coin_key";

    private static final int GAMES_PER_AD = 3;
    /**
     * Counts number of played games
     */
    public static int gameOverCounter = 2;

    /**
     * Time interval (ms) you have to press the backbutton twice in to exit
     */
    private static final long DOUBLE_BACK_TIME = 1000;

    /**
     * Saves the time of the last backbutton press
     */
    private long backPressed;


    /**
     * The view that handles all kind of stuff
     */
    public GameView view;

    public GameModel model;

    public ModelViewMediator modelViewMediator;

    public int ad_loaded = 0;
    public int ad_condition_checked = 0;

    /**
     * The amount of collected coins
     */
    int coins;


    // timer event

    /**
     * Milliseconds for game timer tick
     */
    public static final long UPDATE_INTERVAL = 50;        // = 20 FPS
    volatile private boolean paused = true;
    private Timer timer = new Timer();  // t
    private TimerTask timerTask;        // t

    public void pause() {
        stopTimer();
        paused = true;
    }

    public void resume() {
        paused = false;
        startTimer();
    }

    private void startTimer() {
        setUpTimerTask();
        timer = new Timer();
        timer.schedule(timerTask, UPDATE_INTERVAL, UPDATE_INTERVAL);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    private void setUpTimerTask() {
        stopTimer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                model.checkPasses();
                model.checkOutOfRange();
                model.checkCollision();
                model.createObstacle();
                model.move();
                view.draw();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int heightInit = 2214;
        int widthInit = 1080;

        GameViewBuilder viewBuilder = new GameViewBuilder(this);

        viewBuilder.buildBackgroundView();
        viewBuilder.buildFrontgroundView();
        viewBuilder.buildPauseButtonView();
        viewBuilder.buildTutorial(heightInit, widthInit);

        viewBuilder.buildMusicManager();
        viewBuilder.buildadManager();
        viewBuilder.buildgameOverDialog();

        view = viewBuilder.getGameView();


        GameModelBuilder modelBuilder = new GameModelBuilder(this);
        modelBuilder.buildBackGround();
        modelBuilder.buildFrontGround();
        modelBuilder.buildAccomplishmentBox();
        modelBuilder.buildPauseButton(widthInit);
        modelBuilder.buildPlayer("Cow", heightInit, widthInit);
        model = modelBuilder.getGameModel();

        modelViewMediator = new ModelViewMediator(model, view, this);
        view.setMediator(modelViewMediator);
        model.setMediator(modelViewMediator);

        setContentView(view);
        loadCoins();

        if (gameOverCounter % GAMES_PER_AD == 100) {
            ad_condition_checked++;
            view.adManager.setupAd(this);
            ad_loaded++;
        }
    }
        private void loadCoins () {
            SharedPreferences saves = this.getSharedPreferences(coin_save, 0);
            // this.coins = saves.getInt(coin_key, 0);
            model.coins = saves.getInt(coin_key, 0);
        }

        /**
         * Pauses the view and the music
         */
        @Override
        protected void onPause () {
            pause();
            view.musicManager.musicPause();
            super.onPause();
        }

        /**
         * Resumes the view (but waits the view waits for a tap)
         * and starts the music if it should be running.
         * Also checks whether the Google Play Services are available.
         */
        @Override
        protected void onResume () {
            view.drawOnce();
            view.musicManager.musicResume();
            super.onResume();
        }

        /**
         * Prevent accidental exits by requiring a double press.
         */
        @Override
        public void onBackPressed () {
            if (System.currentTimeMillis() - backPressed < DOUBLE_BACK_TIME) {
                super.onBackPressed();
            } else {
                backPressed = System.currentTimeMillis();
                Toast.makeText(this, getResources().getString(R.string.on_back_press), Toast.LENGTH_LONG).show();
            }
        }

        /**
         * Sends the handler the command to show the GameOverDialog.
         * Because it needs an UI thread.
         */
        public void gameOver () {
            // TODO: change

            if (gameOverCounter % GAMES_PER_AD == 0) {
                handler.sendMessage(Message.obtain(handler, GameView.SHOW_AD));
            } else {
                handler.sendMessage(Message.obtain(handler, GameView.SHOW_GAME_OVER_DIALOG));
            }

        }


        // Model to View
        public void callViewDraw () {
            view.answerDraw();
        }

        public int getViewWidth () {
            return view.getWidth();
        }

        public int getViewHeight () {
            return view.getHeight();
        }

        public void onSetupRevive () {
            view.setupRevive();
        }


        // View to Model

        public void playerMove () {
            model.modelPlayerMove();
        }

        public void pauseButtonMove () {
            model.modelPauseButtonMove();
        }


        public boolean modelOnTouchEvent ( boolean tutorialIsShown, int x, int y){
//        return model.onTouchEvent(tutorialIsShown, paused, x, y);

            return this.onTouchEvent(tutorialIsShown, paused, x, y);
        }


        public boolean onTouchEvent ( boolean tutorialIsShown, boolean paused, int x, int y){
            if (tutorialIsShown) {
                // dismiss tutorial
                tutorialIsShown = false;
                this.resume();
                model.playerOnTap();
            } else if (paused) {
                this.resume();
            } else if (model.PauseButtonTouching(x, y)) {
                this.pause();
            } else {
                model.playerOnTap();
            }
            return tutorialIsShown;
        }

        private void createPowerUp(){
            model.createPowerUp();
        }

        private void increasePoints() {model.increasePoints();}

        private void gameOverOverAll(){
            model.gameOver();
        }

        private void increaseCoin(){
            model.increaseCoin();
        }

        private void decreasePoints(){
            model.decreasePoints();
        }
        private void changeToSick(ChangeToSickEvent event){
            model.changeToSick(event.player);
        }
        private void changeToCat(ChangeToNyanCatEvent event){
            model.changeToNyanCat(event.toast, event.player);
        }
    }
