package edu.ucsd.flappycow.model.connectors;

import java.util.List;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.gameData.CreatePowerUpEvent;
import edu.ucsd.flappycow.gameData.GameEvent;
import edu.ucsd.flappycow.gameData.GameEventEnum;
import edu.ucsd.flappycow.interfaces.IObserver;
import edu.ucsd.flappycow.interfaces.ISubject;
import edu.ucsd.flappycow.interfaces.SubjectImpl;
import edu.ucsd.flappycow.model.components.Obstacle;
import edu.ucsd.flappycow.model.components.PlayableCharacter;

public class PassMediator implements ISubject<GameEvent>, IObserver<GameEvent> {
    private IsPassedMediator isPassedMediator;
    private OnPassMediator onPassMediator;
    public PassMediator(GameModel gameModel){
        isPassedMediator =  new IsPassedMediator();
        onPassMediator = new OnPassMediator(this);
//        register(gameActivity);
        register(gameModel);
    }
    public void checkPasses(GameActivity gameActivity, List<Obstacle> obstacles, PlayableCharacter player){
        for (Obstacle o : obstacles) {
            if (isPassedMediator.isPassed(o,player)) {
                if (!o.isAlreadyPassed) {
                    System.out.println("PassMediator o.isAlreadyPassed " + o.isAlreadyPassed);
                    onPassMediator.onPassForObstacle(gameActivity, o);
                    subjImpl.notify(new CreatePowerUpEvent(GameEventEnum.CREATE_POWERUP));
                }
            }
        }
    }
    private SubjectImpl<GameEvent> subjImpl = new SubjectImpl();
    @Override
    public void register(IObserver<GameEvent> observer) { subjImpl.register(observer); }

    @Override
    public void remove(IObserver<GameEvent> observer) { subjImpl.remove(observer); }

    @Override
    public void onUpdate(GameEvent data) {
        switch (data.event_type) {
            case GameEventEnum.INCREASE_POINTS:
            case GameEventEnum.MUSIC_MANAGER_LOAD_EVENT:
            case GameEventEnum.MUSIC_MANAGER_PLAY_EVENT:
                subjImpl.notify(data);
                break;
            default:
                throw new RuntimeException();
        }
    }

}
