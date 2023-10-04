package edu.ucsd.flappycow.gameData;

import static edu.ucsd.flappycow.gameData.GameEventEnum.MUSIC_MANAGER_LOAD_EVENT;

import android.content.Context;

public class MusicManagerLoadEvent extends GameEvent {
    public Context context;
    public int sprite_type;  // 1: coin, 2: obstacle collision, 3: obstacle pass
    public int resId;
    public int priority;

    public MusicManagerLoadEvent(int sprite_type, Context context, int resId, int priority) {
        super();
        this.event_type = MUSIC_MANAGER_LOAD_EVENT;
        this.sprite_type = sprite_type;
        this.context = context;
        this.resId = resId;
        this.priority = priority;
    }

}
