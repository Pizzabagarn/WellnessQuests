package com.example.wellnessquest.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.wellnessquest.R;

/**
 * Singleton class responsible for handling all audio playback in the WellnessQuest app.
 * Includes background music and sound effects such as quest completion, button clicks, errors, etc.
 * Uses {@link MediaPlayer} for music and {@link SoundPool} for sound effects.
 *
 * Access via {@link #getInstance(Context)}.
 *
 * @author Alexander Westman
 */
public class SoundManager {

    // === Constants ===
    private static final float MUSIC_VOLUME_LEFT = 0.1f;
    private static final float MUSIC_VOLUME_RIGHT = 0.1f;
    private static final float FX_VOLUME_LEFT = 0.2f;
    private static final float FX_VOLUME_RIGHT = 0.2f;
    private static final float ERROR_VOLUME = 0.3f;
    private static final float CLICK_VOLUME = 0.5f;

    // === Singleton Instance ===
    private static SoundManager instance;

    // === Media & Sound ===
    private MediaPlayer musicPlayer;
    private SoundPool soundPool;
    private boolean soundPoolLoaded = false;

    private int clickSoundId, levelUpSoundId, successSoundId, questSoundId, errorSoundId, coinSoundId;

    private boolean isMusicPlaying = false;
    private boolean suppressNextPause = false;

    /**
     * Private constructor. Initializes media player and loads sound effects.
     *
     * @param context application context
     */
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
        errorSoundId = soundPool.load(context, R.raw.error, 1);
        // coinSoundId can be added here if needed
    }

    /**
     * Returns the singleton instance of the SoundManager.
     *
     * @param context context used to initialize if needed
     * @return the SoundManager instance
     */
    public static synchronized SoundManager getInstance(Context context) {
        if (instance == null) {
            instance = new SoundManager(context);
        }
        return instance;
    }

    /**
     * Plays the quest completion sound.
     */
    public void playSuccess() {
        if (soundPoolLoaded) {
            soundPool.play(successSoundId, FX_VOLUME_LEFT, FX_VOLUME_RIGHT, 0, 0, 1);
        }
    }

    /**
     * Plays an error sound with slightly higher volume.
     */
    public void playError() {
        if (soundPoolLoaded) {
            soundPool.play(errorSoundId, ERROR_VOLUME, ERROR_VOLUME, 0, 0, 1);
        }
    }

    /**
     * Plays the level-up sound.
     */
    public void playLevelUp() {
        if (soundPoolLoaded) {
            soundPool.play(levelUpSoundId, FX_VOLUME_LEFT, FX_VOLUME_RIGHT, 0, 0, 1);
        }
    }

    /**
     * Plays the button click sound.
     */
    public void playButtonClick() {
        if (soundPoolLoaded) {
            soundPool.play(clickSoundId, CLICK_VOLUME, CLICK_VOLUME, 0, 0, 1);
        }
    }

    /**
     * Plays the quest log open sound.
     */
    public void playQuestSound() {
        if (soundPoolLoaded) {
            soundPool.play(questSoundId, FX_VOLUME_LEFT, FX_VOLUME_RIGHT, 0, 0, 1);
        }
    }

    /**
     * Prevents background music from being paused once (e.g. during temporary transitions).
     */
    public void suppressAutoPause() {
        suppressNextPause = true;
    }

    /**
     * Checks and resets the pause suppression flag.
     *
     * @return true if the next pause should be skipped
     */
    public boolean consumeSuppressPause() {
        boolean result = suppressNextPause;
        suppressNextPause = false;
        return result;
    }

    /**
     * Starts background music playback if not already playing.
     */
    public void playBackgroundMusic() {
        if (!isMusicPlaying && musicPlayer != null) {
            musicPlayer.setVolume(MUSIC_VOLUME_LEFT, MUSIC_VOLUME_RIGHT);
            musicPlayer.start();
            isMusicPlaying = true;
        }
    }

    /**
     * Pauses the background music if currently playing.
     */
    public void stopBackgroundMusic() {
        if (musicPlayer != null && isMusicPlaying) {
            musicPlayer.pause();
            isMusicPlaying = false;
        }
    }

    /**
     * Releases all media resources and resets the singleton instance.
     */
    public void release() {
        if (musicPlayer != null) {
            musicPlayer.release();
            musicPlayer = null;
        }
        instance = null;
    }
}
