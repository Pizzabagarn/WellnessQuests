package com.example.wellnessquest.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.wellnessquest.R;

public class SoundManager {
    private static SoundManager instance;
    private MediaPlayer musicPlayer;
    private boolean isMusicPlaying = false;
    private float volumeRight = 0.1f;
    private float volumeLeft = 0.1f;
    private float fxLeftVol = 0.2f;
    private float fxRightVol = 0.2f;
    private boolean suppressNextPause = false;
    private SoundPool soundPool;
    private int clickSoundId,levelUpSoundId, successSoundId,questSoundId, errorSoundId, coinSoundId;
    private boolean soundPoolLoaded = false;


    private SoundManager(Context context) {
        Context appContext = context.getApplicationContext();
        musicPlayer = MediaPlayer.create(appContext, R.raw.wellness_quest_tune);
        musicPlayer.setLooping(true);
        soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        soundPool.setOnLoadCompleteListener((sp, sampleId, status) -> soundPoolLoaded = true);


        successSoundId = soundPool.load(context, R.raw.quest_complete, 1);
        levelUpSoundId = soundPool.load(context, R.raw.level_up, 1);
        clickSoundId = soundPool.load(context, R.raw.button_click, 1);
        questSoundId = soundPool.load(context, R.raw.questlog_sound, 1);
        errorSoundId = soundPool.load(context, R.raw.error,1);

    }

    public static synchronized SoundManager getInstance(Context context) {
        if (instance == null) {
            instance = new SoundManager(context);
        }
        return instance;
    }

    public void playSuccess() {
        if (soundPoolLoaded) {
            soundPool.play(successSoundId, fxLeftVol, fxRightVol, 0, 0, 1);
        }
    }

    public void playError() {
        if (soundPoolLoaded) {
            soundPool.play(errorSoundId, 0.3f, 0.3f, 0, 0, 1);
        }
    }

    public void playLevelUp() {
        if (soundPoolLoaded) {
            soundPool.play(levelUpSoundId, fxLeftVol, fxRightVol, 0, 0, 1);
        }
    }

    public void playButtonClick() {
        if (soundPoolLoaded) {
            soundPool.play(clickSoundId, 0.5f, 0.5f, 0, 0, 1);
        }
    }

    public void playQuestSound() {
        if (soundPoolLoaded) {
            soundPool.play(questSoundId, fxLeftVol, fxRightVol, 0, 0, 1);
        }
    }

    public void suppressAutoPause() {
        suppressNextPause = true;
    }

    public boolean consumeSuppressPause() {
        boolean result = suppressNextPause;
        suppressNextPause = false;
        return result;
    }

    public void playBackgroundMusic() {
        if (!isMusicPlaying && musicPlayer != null) {
            musicPlayer.setVolume(volumeLeft, volumeRight); // t.ex. 30% volym på vänster och höger kanal
            musicPlayer.start();
            isMusicPlaying = true;
        }
    }

    public void stopBackgroundMusic() {
        if (musicPlayer != null && isMusicPlaying) {
            musicPlayer.pause();
            isMusicPlaying = false;
        }
    }

    public void release() {
        if (musicPlayer != null) {
            musicPlayer.release();
            musicPlayer = null;
        }
        instance = null;
    }
}
