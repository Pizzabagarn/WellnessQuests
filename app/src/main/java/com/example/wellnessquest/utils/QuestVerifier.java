package com.example.wellnessquest.utils;

import android.content.Context;
import android.net.Uri;

import com.example.wellnessquest.model.Quest;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.IOException;
import java.util.List;

public class QuestVerifier {

    public interface VerificationCallback {
        void onVerified();
        void onFailed();
        void onMismatch(List<ImageLabel> labels);
        void onError(Exception e);
    }

    public static void verify(Context context, Uri imageUri, Quest quest, String description, VerificationCallback callback) {
        try {
            InputImage image = InputImage.fromFilePath(context, imageUri);
            ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);

            labeler.process(image)
                    .addOnSuccessListener(labels -> {
                        List<String> validTags = quest.getValidTags();
                        boolean matchFound = false;

                        for (ImageLabel label : labels) {
                            String tag = label.getText().toLowerCase();
                            float confidence = label.getConfidence();

                            if (confidence > 0.6f) {
                                for (String valid : validTags) {
                                    if (tag.contains(valid)) {
                                        matchFound = true;
                                        break;
                                    }
                                }
                            }

                            if (matchFound) break;
                        }

                        if (matchFound) {
                            callback.onVerified();
                        } else {
                            callback.onMismatch(labels);
                        }
                    })
                    .addOnFailureListener(callback::onError);

        } catch (IOException e) {
            callback.onError(e);
        }
    }
}
