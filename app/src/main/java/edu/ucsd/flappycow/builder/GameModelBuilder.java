package edu.ucsd.flappycow.builder;

import edu.ucsd.flappycow.model.components.AchievementBox;
import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.model.connectors.GameModel;
import edu.ucsd.flappycow.interfaces.AbstractGameModelBuilder;
import edu.ucsd.flappycow.model.components.Background;
import edu.ucsd.flappycow.model.components.Cow;
import edu.ucsd.flappycow.model.components.Frontground;
import edu.ucsd.flappycow.model.components.NyanCat;
import edu.ucsd.flappycow.model.components.PauseButton;

public class GameModelBuilder implements AbstractGameModelBuilder {

    private GameModel gameModel;

    private GameActivity gameActivity;

    public GameModelBuilder(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        gameModel = new GameModel(gameActivity);
    }

    @Override
    public void buildPlayer(String type, int heightInit, int widthInit) {
        switch (type) {
            case "Cow":
                gameModel.setPlayer(new Cow(gameActivity, heightInit, widthInit));
                break;
            case "Cat":
                gameModel.setPlayer(new NyanCat(gameActivity, heightInit, widthInit));
                break;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public void buildBackGround() {
        gameModel.setBackground(new Background(gameActivity));
    }

    @Override
    public void buildFrontGround() {
        gameModel.setFrontground(new Frontground(gameActivity));
    }

    @Override
    public void buildPauseButton(int widthInit) {
        gameModel.setPauseButton(new PauseButton(gameActivity, widthInit));
    }

    @Override
    public void buildAccomplishmentBox() {
        gameModel.setAccomplishmentBox(new AchievementBox());
    }

    @Override
    public GameModel getGameModel() {
        return gameModel;
    }
}
