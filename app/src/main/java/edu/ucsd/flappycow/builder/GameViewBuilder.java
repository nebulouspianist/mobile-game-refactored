package edu.ucsd.flappycow.builder;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.view.components.GameOverDialog;
import edu.ucsd.flappycow.view.connectors.GameView;
import edu.ucsd.flappycow.interfaces.AbstractGameViewBuilder;
import edu.ucsd.flappycow.model.components.Tutorial;
import edu.ucsd.flappycow.view.components.draw.BackgroundView;
import edu.ucsd.flappycow.view.components.draw.FrontgroundView;
import edu.ucsd.flappycow.view.components.draw.PauseButtonView;
import edu.ucsd.flappycow.view.components.AdManager;
import edu.ucsd.flappycow.view.components.MusicManager;

public class GameViewBuilder implements AbstractGameViewBuilder {

    private GameView view;
    private GameActivity gameActivity;

    public GameViewBuilder (GameActivity gameActivity ){
        this.gameActivity = gameActivity;

        view = new GameView(gameActivity);
    }

    @Override
    public void buildBackgroundView() {

        view.setBackgroundView(new BackgroundView(gameActivity));
    }

    @Override
    public void buildFrontgroundView() {

        view.setFrontgroundView(new FrontgroundView(gameActivity));

    }


    @Override
    public void buildPauseButtonView() {

        view.setPauseButtonView(new PauseButtonView(gameActivity));

    }

    @Override
    public void buildTutorial(int heightInit, int widthInit) {

        view.setTutorial(new Tutorial(gameActivity, heightInit, widthInit));

    }

    @Override
    public void buildMusicManager() {
        view.setMusicManager(new MusicManager(gameActivity));
    }

    @Override
    public void buildadManager() {
        view.setAdManager(new AdManager());
    }

    @Override
    public void buildgameOverDialog() {
        view.setGameOverDialog(new GameOverDialog(gameActivity));
    }

    @Override
    public GameView getGameView() {
        return view;
    }


}
