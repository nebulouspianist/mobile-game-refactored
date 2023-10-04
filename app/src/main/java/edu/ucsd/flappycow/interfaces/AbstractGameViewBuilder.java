package edu.ucsd.flappycow.interfaces;

import edu.ucsd.flappycow.view.connectors.GameView;

public interface AbstractGameViewBuilder {

    void buildBackgroundView();
    void buildFrontgroundView();
    void buildPauseButtonView();
    void buildTutorial(int heightInit, int widthInit);


    void buildMusicManager();
    void buildadManager();
    void buildgameOverDialog();


    GameView getGameView();

}
