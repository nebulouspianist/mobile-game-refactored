package edu.ucsd.flappycow.model.connectors;

import static edu.ucsd.flappycow.gameData.SoundEnum.PASS_SOUND;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.presenter.MainActivity;
import edu.ucsd.flappycow.R;
import edu.ucsd.flappycow.gameData.GameEvent;
import edu.ucsd.flappycow.gameData.GameEventEnum;
import edu.ucsd.flappycow.gameData.IncreasePointsEvent;
import edu.ucsd.flappycow.gameData.MusicManagerLoadEvent;
import edu.ucsd.flappycow.gameData.MusicManagerPlayEvent;
import edu.ucsd.flappycow.interfaces.IObserver;
import edu.ucsd.flappycow.interfaces.ISubject;
import edu.ucsd.flappycow.interfaces.SubjectImpl;
import edu.ucsd.flappycow.model.components.Obstacle;

public class OnPassMediator implements ISubject<GameEvent> {
    private SubjectImpl<GameEvent> subjImpl = new SubjectImpl();
    private static final int SOUND_VOLUME_DIVIDER = 3;

    public OnPassMediator(PassMediator passMediator){
        register(passMediator);
    }

    public void onPassForObstacle(GameActivity gameActivity, Obstacle o) {
        subjImpl.notify(new MusicManagerLoadEvent(PASS_SOUND, gameActivity, R.raw.coin, 1));
        System.out.println("onPassForObstacle o.isAlreadyPassed " + o.isAlreadyPassed);
        if (!o.isAlreadyPassed) {
            System.out.println("onPassForObstacle o.isAlreadyPassed2 " + o.isAlreadyPassed);
            o.isAlreadyPassed = true;
            subjImpl.notify(new IncreasePointsEvent(GameEventEnum.INCREASE_POINTS));

            subjImpl.notify(new MusicManagerPlayEvent(
                    PASS_SOUND,
                    MainActivity.volume / SOUND_VOLUME_DIVIDER,
                    MainActivity.volume / SOUND_VOLUME_DIVIDER,
                    0, 0, 1));
        }
    }

    @Override
    public void register(IObserver<GameEvent> observer) {
        subjImpl.register(observer);
    }

    @Override
    public void remove(IObserver<GameEvent> observer) {
        subjImpl.remove(observer);
    }
}
