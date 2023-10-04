package edu.ucsd.flappycow.gameData;

import edu.ucsd.flappycow.model.components.PlayableCharacter;
import edu.ucsd.flappycow.model.components.Toast;

public class ChangeToNyanCatEvent extends GameEvent{
    public Toast toast;
    public PlayableCharacter player;
    public ChangeToNyanCatEvent(int type, Toast toast, PlayableCharacter player) {
        this.event_type = type;
        this.toast = toast;
        this.player = player;
    }
}
