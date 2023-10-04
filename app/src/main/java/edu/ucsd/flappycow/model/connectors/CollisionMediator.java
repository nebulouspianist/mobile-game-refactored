package edu.ucsd.flappycow.model.connectors;

import java.util.List;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.gameData.GameEvent;
import edu.ucsd.flappycow.gameData.GameEventEnum;
import edu.ucsd.flappycow.gameData.GameOverOverall;
import edu.ucsd.flappycow.interfaces.IObserver;
import edu.ucsd.flappycow.interfaces.ISubject;
import edu.ucsd.flappycow.interfaces.SubjectImpl;
import edu.ucsd.flappycow.model.components.Coin;
import edu.ucsd.flappycow.model.components.Obstacle;
import edu.ucsd.flappycow.model.components.PlayableCharacter;
import edu.ucsd.flappycow.model.components.PowerUp;
import edu.ucsd.flappycow.model.components.Toast;
import edu.ucsd.flappycow.model.components.Virus;

public class CollisionMediator implements ISubject<GameEvent>, IObserver<GameEvent>  {
    private IsCollidingMediator isCollidingMediator;
    private OnCollisionMediator onCollisionMediator;
    public CollisionMediator(GameActivity gameActivity, GameModel gameModel){
        isCollidingMediator =  new IsCollidingMediator();
        onCollisionMediator = new OnCollisionMediator(gameActivity);
        register(gameModel);

    }
    public void checkCollision(GameActivity gameActivity, List<Obstacle> obstacles, PlayableCharacter player, List<PowerUp> powerUps, int viewHeight, int collisionTolerance) {
        for (Obstacle o : obstacles) {
            if(isCollidingMediator.isCollidingForObstacle(o, player, collisionTolerance)){
                onCollisionMediator.onCollisionForObstacle(gameActivity, o);

                subjImpl.notify(new GameOverOverall(GameEventEnum.GAMEOVER_OVERALL));
                //event
               // gameOver();
            }
        }
        for (int i = 0; i < powerUps.size(); i++) {
            //if-else
            PowerUp p = powerUps.get(i);
            if(isCollidingMediator.isColliding(p,player, collisionTolerance)){
                if(p instanceof Coin)  onCollisionMediator.onCollisionForCoin(gameActivity, (Coin)p);
                else if(p instanceof Virus) onCollisionMediator.onCollisionForVirus(player);
                else if(p instanceof Toast) onCollisionMediator.onCollisionForToast((Toast)p, player);
                powerUps.remove(i);
                i--;
            }
        }
        if (player.isTouchingEdge(viewHeight)){
                //modelViewMediator.getViewHeight())) {

            subjImpl.notify(new GameOverOverall(GameEventEnum.GAMEOVER_OVERALL));
            //event
           // gameOver();
        }
    }
    private SubjectImpl<GameEvent> subjImpl = new SubjectImpl();
    @Override
    public void register(IObserver<GameEvent> observer) { subjImpl.register(observer); }

    @Override
    public void remove(IObserver<GameEvent> observer) { subjImpl.remove(observer); }

    @Override
    public void onUpdate(GameEvent event) {
        subjImpl.notify(event);
    }
}
