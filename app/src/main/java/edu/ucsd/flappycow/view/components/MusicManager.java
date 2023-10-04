package edu.ucsd.flappycow.view.components;

import static edu.ucsd.flappycow.gameData.SoundEnum.COIN_SOUND;
import static edu.ucsd.flappycow.gameData.SoundEnum.COLLIDE_SOUND;
import static edu.ucsd.flappycow.gameData.SoundEnum.PASS_SOUND;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import edu.ucsd.flappycow.presenter.MainActivity;
import edu.ucsd.flappycow.R;
import edu.ucsd.flappycow.gameData.MusicManagerLoadEvent;
import edu.ucsd.flappycow.gameData.MusicManagerPlayEvent;

public class MusicManager {
    public int coinSound = -1;
    public int collideSound = -1;
    public int passSound = -1;
    /**
     * Will play things like mooing
     */
    public SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    /**
     * Whether the music should play or not
     */
    public boolean musicShouldPlay = false;
    /**
     * Will play songs like:
     * nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan
     * nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan
     * nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan
     * nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan
     * Does someone know the second verse ???
     */
    public MediaPlayer musicPlayer = null;

    public MusicManager(Context context) {
        initMusicPlayer(context);
    }

    public void initMusicPlayer(Context context) {
        if (musicPlayer == null) {
            // to avoid unnecessary reinitialisation
            musicPlayer = MediaPlayer.create(context, R.raw.nyan_cat_theme);
            if (musicPlayer == null) {
                return;
            }
            musicPlayer.setLooping(true);
            musicPlayer.setVolume(MainActivity.volume, MainActivity.volume);
        }
        musicPlayer.seekTo(0);    // Reset song to position 0
    }

    public void load(MusicManagerLoadEvent event) {
        if (event.sprite_type == COIN_SOUND && coinSound == -1) {
            coinSound = soundPool.load(event.context, event.resId, event.priority);
        }
        else if (event.sprite_type == PASS_SOUND && passSound == -1) {
            passSound = soundPool.load(event.context, event.resId, event.priority);
        }
        else if (event.sprite_type == COLLIDE_SOUND && collideSound == -1) {
            collideSound = soundPool.load(event.context, event.resId, event.priority);
        }
    }

    public void play(MusicManagerPlayEvent event) {
        if (event.sprite_type == COIN_SOUND) {
            assert coinSound != -1;
            soundPool.play(coinSound, event.leftVolume, event.rightVolume, event.priority, event.loop, event.rate);
        }
        else if (event.sprite_type == PASS_SOUND) {
            assert passSound != -1;
            soundPool.play(passSound, event.leftVolume, event.rightVolume, event.priority, event.loop, event.rate);
        }
        else if (event.sprite_type == COLLIDE_SOUND) {
            assert collideSound != -1;
            soundPool.play(collideSound, event.leftVolume, event.rightVolume, event.priority, event.loop, event.rate);
        }
    }

    public void musicPause() {
        if (musicPlayer != null && musicPlayer.isPlaying()) {
            musicPlayer.pause();
        }
    }

    public void musicResume() {
        if (musicPlayer != null && musicShouldPlay) {
            musicPlayer.start();
        }
    }
}