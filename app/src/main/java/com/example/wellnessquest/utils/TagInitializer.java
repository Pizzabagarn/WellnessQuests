package com.example.wellnessquest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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

public class TagInitializer {

    private static final String PREF_NAME = "tag_analysis_prefs";

    // Callback-interface för att signalera färdigställande eller fel
    public interface InitCallback {
        void onDone();
        void onError(Exception e);
    }

    // Rensa tidigare analyserade MLKit-taggar
    public static void reset(Context context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit().clear().apply();
        Log.d("TagInit", "🔄 SharedPreferences rensades – ny initialisering kommer ske.");
    }

    // Kör bara MLKit-inläsningen om det inte redan är gjort
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
                            // Hoppa över om bilden redan analyserats
                            if (prefs.contains(path + "_done")) {
                                Log.d("TagInit", "⏩ Hoppar över (redan analyserad): " + path);
                                continue;
                            }

                            InputStream inputStream = context.getAssets().open(path);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            InputImage image = InputImage.fromBitmap(bitmap, 0);

                            Log.d("TagInit", "🚀 Väntar på MLKit för: " + path);

                            CountDownLatch latch = new CountDownLatch(1);

                            labeler.process(image)
                                    .addOnSuccessListener(labels -> {
                                        for (ImageLabel label : labels) {
                                            if (label.getConfidence() > 0.6f) {
                                                String tag = label.getText().toLowerCase();
                                                if (!allTags.contains(tag)) {
                                                    allTags.add(tag);
                                                    Log.d("TagInit", "🔍 Tag för " + questId + ": " + tag);
                                                }
                                            }
                                        }
                                        // ✅ Markera att denna bild är klar
                                        editor.putBoolean(path + "_done", true);
                                        latch.countDown();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("TagInit", "💥 MLKit-fel för bild: " + path, e);
                                        latch.countDown();
                                    });

                            latch.await();

                        } catch (Exception e) {
                            Log.e("TagInit", "💥 Fel på bild: " + path, e);
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


    // Hämta sammanslagna taggar: de fördefinierade + de MLKit har genererat
    public static List<String> getMergedTags(Context context, Quest quest) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Type listType = new TypeToken<List<String>>(){}.getType();
        List<String> mlTags = new Gson().fromJson(prefs.getString(quest.getId() + "_mltags", "[]"), listType);

        List<String> merged = new ArrayList<>(quest.getValidTags());
        for (String tag : mlTags) {
            if (!merged.contains(tag)) {
                merged.add(tag);
            }
        }

        Log.d("TagInit", "🔄 Merged tags for " + quest.getId() + ": " + merged);
        return merged;
    }
}
