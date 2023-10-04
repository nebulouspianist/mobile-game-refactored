package edu.ucsd.flappycow.gameData;

public class GameEventEnum {
    public static final int COLLECT_COIN_EVENT = 1;  // example

    public static final int MODEL_ONTOUCH = 3;

    public static final int PLAYER_MOVE = 4;

    public static final int PAUSEBUTTON_MOVE = 5;

    public static final int RESUME = 6;

    public static final int GAMEOVER_COUNTER = 7;

    // model methods
    public static final int playerDeadFall_callViewDraw_EVENT = 101;

    public static final int gameOver_pause_EVENT = 102;

    public static final int gameOver_gameOver_EVENT = 103;

    public static final int setupRevive_hideDialog_EVENT = 104;

    public static final int SetupRevive_onSetupRevive_EVENT = 105;

    // onCollisionForObstacle
    public static final int MUSIC_MANAGER_LOAD_EVENT = 108;
//    public static final int onTouchEvent_Resume_EVENT = 106; // 6

    public static final int MUSIC_MANAGER_PLAY_EVENT = 119;

    // public static final int onPassForObstacle_musicManagerLoad_EVENT = 110;   // 108

    // public static final int onPassForObstacle_musicManagerPlay_EVENT = 111;   // 109
    public static final int CREATE_POWERUP = 120;
    public static final  int INCREASE_POINTS = 121;
    public static final int INCREASE_COINS = 122;
    public static final int DECREASE_POINTS = 123;
    public static final int CHANGETO_SICK = 124;
    public static final int CHANGETO_CAT = 125;
    public static final int GAMEOVER_OVERALL = 126;



}
