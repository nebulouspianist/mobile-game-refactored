package edu.ucsd.flappycow.presenter;

import edu.ucsd.flappycow.R;
import edu.ucsd.flappycow.model.connectors.GameModel;
import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.view.connectors.GameView;

public class ModelViewMediator {


    public GameModel model;

    public GameView view;

    public GameActivity gameActivity;

    public ModelViewMediator(GameModel gameModel, GameView gameView, GameActivity gameActivity){

        model = gameModel;
        view = gameView;

        this.gameActivity = gameActivity;

    }


    //model to view

    public int getViewWidth() {
        return view.getWidth();
    }

    public int getViewHeight() {
        return view.getHeight();
    }

    public void onSetupRevive() {
        view.setupRevive();
    }


    //view to model

    public int getScoreTextMetrics() {
        return model.getScoreTextMetrics();
    }

    public boolean playerIsDead() { return model.modelPlayerIsDead(); }





    public int getAchievementboxPoints (){
        return model.accomplishmentBox.points;
    }

    public int getCoins(){
        return model.coins;
    }

    public int getBackgroundX(){
        return model.getBackgroundX();
    }

    public int getBackgroundY(){
        return model.getBackgroundY();
    }

    public int getFrontGroundX(){
        return model.getFrontgroundX();
    }

    public int getFrontGroundY(){
        return model.getFrontgroundY();
    }

    public int getPauseButtonX(){
        return model.getPauseButtonX();
    }

    public int getPauseButtonY(){
        return model.getPauseButtonY();
    }




    //View to Activity

    public boolean ActivityOnTouchEvent(boolean tutorialIsShown, int x, int y){
       return gameActivity.modelOnTouchEvent(
                tutorialIsShown, x, y);

    }

    public String getResourceString(){
        return gameActivity.getResources().getString(R.string.onscreen_coin_text);
    }
}
