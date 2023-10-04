package edu.ucsd.flappycow.model.connectors;

import static edu.ucsd.flappycow.gameData.SoundEnum.COIN_SOUND;
import static edu.ucsd.flappycow.gameData.SoundEnum.COLLIDE_SOUND;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.presenter.MainActivity;
import edu.ucsd.flappycow.R;
import edu.ucsd.flappycow.gameData.ChangeToNyanCatEvent;
import edu.ucsd.flappycow.gameData.ChangeToSickEvent;
import edu.ucsd.flappycow.gameData.DecreasePointsEvent;
import edu.ucsd.flappycow.gameData.GameEvent;
import edu.ucsd.flappycow.gameData.GameEventEnum;
import edu.ucsd.flappycow.gameData.IncreaseCoinsEvent;
import edu.ucsd.flappycow.gameData.MusicManagerLoadEvent;
import edu.ucsd.flappycow.gameData.MusicManagerPlayEvent;
import edu.ucsd.flappycow.interfaces.IObserver;
import edu.ucsd.flappycow.interfaces.ISubject;
import edu.ucsd.flappycow.interfaces.SubjectImpl;
import edu.ucsd.flappycow.model.components.Coin;
import edu.ucsd.flappycow.model.components.Obstacle;
import edu.ucsd.flappycow.model.components.PlayableCharacter;
import edu.ucsd.flappycow.model.components.Toast;

public class OnCollisionMediator implements ISubject<GameEvent> {
    private SubjectImpl<GameEvent> subjImpl = new SubjectImpl();
    private static final int SOUND_VOLUME_DIVIDER = 3;
    public OnCollisionMediator(GameActivity gameActivity){
        register(gameActivity);
    }
    public void onCollisionForObstacle(GameActivity gameActivity, Obstacle o) {
        subjImpl.notify(new MusicManagerLoadEvent(COLLIDE_SOUND, gameActivity, R.raw.coin, 1));
        subjImpl.notify(new MusicManagerPlayEvent(
                COLLIDE_SOUND,
                MainActivity.volume / SOUND_VOLUME_DIVIDER,
                MainActivity.volume / SOUND_VOLUME_DIVIDER,
                0, 0, 1));
    }

    public void onCollisionForCoin(GameActivity gameActivity, Coin c){
        subjImpl.notify(new MusicManagerLoadEvent(COIN_SOUND, gameActivity, R.raw.coin, 1));
        subjImpl.notify(new MusicManagerPlayEvent(
                COIN_SOUND,
                MainActivity.volume,
                MainActivity.volume,
                0, 0, 1));

       // increaseCoin();
        subjImpl.notify(new IncreaseCoinsEvent(GameEventEnum.INCREASE_COINS));
    }

    public void onCollisionForVirus(PlayableCharacter player){
        //decreasePoints();
        //changeToSick(player);
        subjImpl.notify(new DecreasePointsEvent(GameEventEnum.DECREASE_POINTS));
        subjImpl.notify(new ChangeToSickEvent(GameEventEnum.CHANGETO_SICK, player));
    }

    public void onCollisionForToast(Toast t, PlayableCharacter player){
        //changeToNyanCat(t, player);
        subjImpl.notify(new ChangeToNyanCatEvent(GameEventEnum.CHANGETO_CAT, t, player));
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
