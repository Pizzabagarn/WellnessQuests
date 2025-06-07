package com.example.wellnessquest;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.example.wellnessquest.utils.SoundManager;

/**
 * MyApp initializes global application behavior including background music management
 * using activity lifecycle callbacks.
 *
 * When the first activity starts, background music is played. When all activities stop,
 * music is paused unless a suppression flag is set.
 *
 * @author Alexander Westman
 */

public class MyApp extends Application {

    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {}

            /**
             * Called when any activity is started. Plays background music
             * if this is the first activity and the app is moving to foreground.
             */
            @Override
            public void onActivityStarted(Activity activity) {
                if (++activityReferences == 1 && !isActivityChangingConfigurations) {
                    SoundManager.getInstance(activity).playBackgroundMusic();
                }
            }

            /**
             * Called when any activity is stopped. Stops music if no activities
             * are running and no suppression flag is active.
             */
            @Override
            public void onActivityStopped(Activity activity) {
                isActivityChangingConfigurations = activity.isChangingConfigurations();
                if (--activityReferences == 0 && !isActivityChangingConfigurations) {
                    if (!SoundManager.getInstance(activity).consumeSuppressPause()) {
                        SoundManager.getInstance(activity).stopBackgroundMusic();
                    }
                }
            }

            @Override public void onActivityResumed(Activity activity) {}
            @Override public void onActivityPaused(Activity activity) {}
            @Override public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}
            @Override public void onActivityDestroyed(Activity activity) {}
        });
    }
}
