/**
 * GameView
 * Probably the most important class for the game
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.view.connectors;

import static edu.ucsd.flappycow.presenter.GameActivity.UPDATE_INTERVAL;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.view.components.GameOverDialog;
import edu.ucsd.flappycow.presenter.ModelViewMediator;
import edu.ucsd.flappycow.gameData.GameEvent;
import edu.ucsd.flappycow.gameData.MusicManagerLoadEvent;
import edu.ucsd.flappycow.gameData.MusicManagerPlayEvent;
import edu.ucsd.flappycow.gameData.GameEventEnum;
import edu.ucsd.flappycow.gameData.GameOverPlusEvent;
import edu.ucsd.flappycow.gameData.PauseButtonMoveEvent;
import edu.ucsd.flappycow.gameData.PlayerMoveEvent;
import edu.ucsd.flappycow.gameData.ResumeEvent;
import edu.ucsd.flappycow.interfaces.IObserver;
import edu.ucsd.flappycow.interfaces.ISubject;
import edu.ucsd.flappycow.interfaces.SubjectImpl;

import edu.ucsd.flappycow.model.components.Obstacle;
import edu.ucsd.flappycow.model.components.PowerUp;
import edu.ucsd.flappycow.model.components.Tutorial;
import edu.ucsd.flappycow.view.components.draw.BackgroundView;
import edu.ucsd.flappycow.view.components.draw.FrontgroundView;
import edu.ucsd.flappycow.view.components.draw.PauseButtonView;
import edu.ucsd.flappycow.view.components.AdManager;
import edu.ucsd.flappycow.view.components.MusicManager;

public class GameView extends SurfaceView implements ISubject<GameEvent> {

    private SubjectImpl<GameEvent> subjImpl = new SubjectImpl();
    @Override
    public void register(IObserver<GameEvent> observer) { subjImpl.register(observer); }

    @Override
    public void remove(IObserver<GameEvent> observer) { subjImpl.remove(observer); }


    public static MusicManager musicManager;

    public AdManager adManager;
    /**
     * The dialog displayed when the game is over
     */
    public GameOverDialog gameOverDialog;

    /**
     * To do UI things from different threads
     */
    public static MyHandler handler;
    public static final int SHOW_GAME_OVER_DIALOG = 0;
    public static final int SHOW_TOAST = 1;
    public static final int SHOW_AD = 2;

    /** The surfaceholder needed for the canvas drawing */
    private SurfaceHolder holder;

    private GameActivity gameActivity;

    private Tutorial tutorial;

    private BackgroundView backgroundView;
    private FrontgroundView frontgroundView;

    private PauseButtonView pauseButtonView;

    ModelViewMediator modelViewMediator;
    private boolean tutorialIsShown = true;

    public static void setMusicManager(MusicManager musicManager) {
        GameView.musicManager = musicManager;
    }

    public void setAdManager(AdManager adManager) {
        this.adManager = adManager;
    }

    public void setGameOverDialog(GameOverDialog gameOverDialog) {
        this.gameOverDialog = gameOverDialog;
    }



    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    public void setBackgroundView(BackgroundView backgroundView) {
        this.backgroundView = backgroundView;
    }

    public void setFrontgroundView(FrontgroundView frontgroundView) {
        this.frontgroundView = frontgroundView;
    }

    public void setPauseButtonView(PauseButtonView pauseButtonView) {
        this.pauseButtonView = pauseButtonView;
    }

    public GameView(Context context) {
        super(context);
        this.gameActivity = (GameActivity) context;
        setFocusable(true);
        System.out.println("[***DBG***]: GameView Construct this.getHeight" + this.getHeight());
        holder = getHolder();
        int heightInit = 2214;
        int widthInit = 1000;

        // register observer
        register(gameActivity);



//        backgroundView = new BackgroundView(gameActivity);
//        frontgroundView = new FrontgroundView(gameActivity);
//        pauseButtonView = new PauseButtonView(gameActivity);
//        tutorial = new Tutorial(gameActivity, heightInit, widthInit);

        // view
//        musicManager = new MusicManager(context);
//        adManager = new AdManager();
//        gameOverDialog = new GameOverDialog(gameActivity);
        handler = new MyHandler(gameActivity);
    }

    public void musicManagerLoad(MusicManagerLoadEvent event) {
        musicManager.load(event);
    }

    public void musicManagerPlay(MusicManagerPlayEvent event) {
        musicManager.play(event);
    }


    public void setMediator(ModelViewMediator modelViewMediator){
        this.modelViewMediator = modelViewMediator;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
        // Just to remove the stupid warning
    }

    public void handleTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN  // Only for "touchdowns"
                && !modelViewMediator.playerIsDead()) { // No support for dead players
            tutorialIsShown = modelViewMediator.ActivityOnTouchEvent(tutorialIsShown, (int)event.getX(), (int)event.getY());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouchEvent(event);
        return true;
    }

    public boolean isTutorialShown() {
        return tutorialIsShown;
    }

    private Canvas getCanvas() {
        Canvas canvas;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas = holder.lockHardwareCanvas();
        } else {
            canvas = holder.lockCanvas();
        }

        return canvas;
    }

    /**
     * Draw Tutorial
     */
    public void showTutorial() {


        PlayerMoveEvent playerMove = new PlayerMoveEvent();
        playerMove.event_type = GameEventEnum.PLAYER_MOVE;

        subjImpl.notify(playerMove);

//        gameActivity.playerMove();



//        player.move();


        PauseButtonMoveEvent pauseButtonMove = new PauseButtonMoveEvent();
        pauseButtonMove.event_type = GameEventEnum.PAUSEBUTTON_MOVE;

        subjImpl.notify(pauseButtonMove);



//        gameActivity.pauseButtonMove();
//        pauseButton.move();

        while (!holder.getSurface().isValid()) {
            /*wait*/
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Canvas canvas = getCanvas();
        drawCanvas(canvas, true);
        tutorial.move();
        tutorial.draw(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    public void drawOnce() {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                if (tutorialIsShown) {
                    showTutorial();
                } else {
                    draw();
                }
            }
        })).start();
    }

    public void setupRevive() {
        for (int i = 0; i < 6; ++i) {
            while (!holder.getSurface().isValid()) {/*wait*/}
            Canvas canvas = getCanvas();
            drawCanvas(canvas, i % 2 == 0);
            holder.unlockCanvasAndPost(canvas);
            // sleep
            try {
                Thread.sleep(UPDATE_INTERVAL * 6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        ResumeEvent resumeEvent = new ResumeEvent();
        resumeEvent.event_type = GameEventEnum.RESUME;

        subjImpl.notify(resumeEvent);
//        gameActivity.resume();
    }

    /**
     * Draws all gameobjects on the surface
     */
    public void draw() {
        while (!holder.getSurface().isValid()) {
            /*wait*/
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Canvas canvas = getCanvas();

        drawCanvas(canvas, true);

        holder.unlockCanvasAndPost(canvas);
    }

    public void answerDraw() {
        draw();
    }

    /**
     * Draws everything normal,
     * except the player will only be drawn, when the parameter is true
     *
     * @param drawPlayer
     */
    private void drawCanvas(Canvas canvas, boolean drawPlayer) {



        int backGroundX = modelViewMediator.getBackgroundX();
        int backGroundY = modelViewMediator.getBackgroundY();


        backgroundView.draw(canvas, backGroundX, backGroundY);


        for (Obstacle r : modelViewMediator.model.obstacles) {
            r.draw(canvas);

        }
        for (PowerUp p : modelViewMediator.model.powerUps) {
            p.draw(canvas);
        }
        if (drawPlayer) {
            modelViewMediator.model.getPlayer().draw(canvas);
        }



        int frontGroundX = modelViewMediator.getFrontGroundX();
        int frontGroundY = modelViewMediator.getFrontGroundY();

        frontgroundView.draw(canvas, frontGroundX,frontGroundY);



        int pauseButtonX = modelViewMediator.getPauseButtonX();
        int pauseButtonY = modelViewMediator.getPauseButtonY();


        pauseButtonView.draw(canvas, pauseButtonX, pauseButtonY);

        // Score Text
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(modelViewMediator.getScoreTextMetrics());

        canvas.drawText(modelViewMediator.getResourceString() + " " + modelViewMediator.getAchievementboxPoints()
                + " / " + modelViewMediator.getResourceString() + " " + modelViewMediator.getCoins(),
            0, modelViewMediator.getScoreTextMetrics(), paint);

    }

    public GameActivity getGameActivity() {
        return this.gameActivity;
    }


    /**
     * Shows the GameOverDialog when a message with code 0 is received.
     */
    public class MyHandler extends Handler {
        private final GameActivity gameActivity;

        public MyHandler(GameActivity gameActivity) {
            this.gameActivity = gameActivity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_GAME_OVER_DIALOG:
                    showGameOverDialog();
                    break;
                case SHOW_TOAST:
                    android.widget.Toast.makeText(gameActivity, msg.arg1, Toast.LENGTH_SHORT).show();
                    break;
                case SHOW_AD:
                    showAdIfAvailable();
                    break;
            }
        }

        private void showAdIfAvailable() {
            if (adManager.interstitial == null) {
                showGameOverDialog();
            } else {
                adManager.interstitial.show(gameActivity);
            }
        }

        private void showGameOverDialog() {


            GameOverPlusEvent plus = new GameOverPlusEvent();
            plus.event_type = GameEventEnum.GAMEOVER_COUNTER;


            subjImpl.notify(plus);

//            ++GameActivity.gameOverCounter;
            gameOverDialog.init();
            gameOverDialog.show();
        }
    }

}
