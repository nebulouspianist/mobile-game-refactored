package edu.ucsd.flappycow.gameData;

import static edu.ucsd.flappycow.gameData.GameEventEnum.MUSIC_MANAGER_LOAD_EVENT;
import static edu.ucsd.flappycow.gameData.GameEventEnum.MUSIC_MANAGER_PLAY_EVENT;

import android.content.Context;

public class MusicManagerPlayEvent extends GameEvent {

    public int sprite_type;

    public float leftVolume;

    public float rightVolume;

    public int priority;

    public int loop;

    public float rate;

    public MusicManagerPlayEvent(int sprite_type, float leftVolume, float rightVolume,
                                 int priority, int loop, float rate) {
        this.event_type = MUSIC_MANAGER_PLAY_EVENT;
        this.sprite_type = sprite_type;
        this.leftVolume = leftVolume;
        this.rightVolume = rightVolume;
        this.priority = priority;
        this.loop = loop;
        this.rate = rate;
    }

}
