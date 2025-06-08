package com.example.wellnessquest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.wellnessquest.model.Quest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * TagInitializer is responsible for automatically generating and caching visual tags
 * associated with each quest using ML Kit’s image labeling.
 *
 * <p>During the app’s startup, each quest is linked to three representative images.
 * These images are automatically fetched using an external Python script that queries
 * image search engines based on the objective of the quest (e.g., "go for a walk",
 * "do yoga", "drink water"). The purpose is to simulate the types of photos a user
 * might submit as proof.</p>
 *
 * <p>The images are analyzed once using ML Kit on the device at startup, and the
 * extracted tags are stored locally using SharedPreferences. These tags are then
 * merged with the quest’s predefined valid tags and used during quest verification
 * to determine whether a user's uploaded image matches the expected content.</p>
 * @author Alexander Westman
 */

public class TagInitializer {


    /**
     * Minimum confidence threshold for an image label to be considered valid.
     */
    private static final float MIN_CONFIDENCE_THRESHOLD = 0.4f;

    private static final String PREF_NAME = "tag_analysis_prefs";

    /**
     * Callback interface for signaling when tag initialization is complete or if an error occurs.
     */
    public interface InitCallback {
        /**
         * Called when tag initialization completes successfully.
         */
        void onDone();

        /**
         * Called when an error occurs during tag initialization.
         *
         * @param e the thrown exception
         */
        void onError(Exception e);
    }

    /**
     * Clears all previously analyzed ML Kit tags from SharedPreferences.
     * Triggers reprocessing the next time {@link #initializeIfNeeded(Context, InitCallback)} is called.
     *
     * @param context application context
     */
    public static void reset(Context context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit().clear().apply();
    }

    /**
     * Initializes and analyzes image tags using ML Kit if not already done.
     * Loads quest image mappings from a JSON file in assets, performs image
     * labeling using ML Kit, and stores the results in SharedPreferences.
     *
     * @param context  application context
     * @param callback callback to signal success or error
     */
    public static void initializeIfNeeded(Context context, InitCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        if (prefs.getBoolean("mlkit_done", false)) {
            callback.onDone();
            return;
        }

        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(context.getAssets().open("quest_images.json")));
                Type mapType = new TypeToken<Map<String, List<String>>>() {}.getType();
                Map<String, List<String>> imageMap = new Gson().fromJson(reader, mapType);

                ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
                SharedPreferences.Editor editor = prefs.edit();

                for (Map.Entry<String, List<String>> entry : imageMap.entrySet()) {
                    String questId = entry.getKey();
                    List<String> imagePaths = entry.getValue();
                    List<String> allTags = new ArrayList<>();

                    for (String path : imagePaths) {
                        try {
                            if (prefs.contains(path + "_done")) {
                                continue;
                            }

                            InputStream inputStream = context.getAssets().open(path);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            InputImage image = InputImage.fromBitmap(bitmap, 0);

                            CountDownLatch latch = new CountDownLatch(1);

                            labeler.process(image)
                                    .addOnSuccessListener(labels -> {
                                        for (ImageLabel label : labels) {
                                            if (label.getConfidence() > MIN_CONFIDENCE_THRESHOLD) {
                                                String tag = label.getText().toLowerCase();
                                                if (!allTags.contains(tag)) {
                                                    allTags.add(tag);
                                                }
                                            }
                                        }
                                        editor.putBoolean(path + "_done", true);
                                        latch.countDown();
                                    })
                                    .addOnFailureListener(e -> {
                                        latch.countDown();
                                    });

                            latch.await();

                        } catch (Exception e) {
                            // Continue processing remaining images
                        }
                    }

                    editor.putString(questId + "_mltags", new Gson().toJson(allTags));
                }

                editor.putBoolean("mlkit_done", true);
                editor.apply();
                callback.onDone();

            } catch (Exception e) {
                callback.onError(e);
            }
        }).start();
    }

    /**
     * Retrieves a merged list of tags for the given quest.
     * Combines predefined tags from the {@link Quest} with previously
     * stored ML Kit-generated tags for the same quest.
     *
     * @param context the application context
     * @param quest   the quest for which tags should be fetched
     * @return a combined list of tags used for verification
     */
    public static List<String> getMergedTags(Context context, Quest quest) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Type listType = new TypeToken<List<String>>() {}.getType();
        List<String> mlTags = new Gson().fromJson(prefs.getString(quest.getId() + "_mltags", "[]"), listType);

        List<String> merged = new ArrayList<>(quest.getValidTags());
        for (String tag : mlTags) {
            if (!merged.contains(tag)) {
                merged.add(tag);
            }
        }

        return merged;
    }
}
