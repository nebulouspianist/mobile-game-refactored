package edu.ucsd.flappycow.interfaces;

import edu.ucsd.flappycow.model.connectors.GameModel;

public interface AbstractGameModelBuilder {

    void buildPlayer(String type, int heightInit, int widthInit);

    void buildBackGround();

    void buildFrontGround();

    void buildPauseButton(int widthInit);

    void buildAccomplishmentBox();

    GameModel getGameModel();
}
