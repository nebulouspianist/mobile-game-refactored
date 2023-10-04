package edu.ucsd.flappycow.gameData;

import edu.ucsd.flappycow.model.components.PlayableCharacter;

public class ChangeToSickEvent extends GameEvent{
    public PlayableCharacter player;
    public ChangeToSickEvent(int type, PlayableCharacter player) {
        this.event_type = type;
        this.player = player;
    }
}