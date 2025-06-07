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

/**
 * Utility class responsible for verifying a quest based on an image and description
 * using ML Kit's image labeling.
 *
 * <p>The verification compares image labels with valid tags associated with a quest.
 * If a match is found with sufficient confidence, the quest is considered verified.</p>
 *
 * <p>Results are reported asynchronously through the {@link VerificationCallback} interface.</p>
 *
 * @author Alexander Westman
 */
public class QuestVerifier {

    /**
     * Minimum confidence threshold for an image label to be considered valid.
     */
    private static final float MIN_CONFIDENCE_THRESHOLD = 0.4f;

    /**
     * Callback interface to handle the result of the quest verification process.
     */
    public interface VerificationCallback {
        /**
         * Called when the image was successfully verified against the quest's valid tags.
         */
        void onVerified();

        /**
         * Called when the verification failed for an unspecified reason.
         */
        void onFailed();

        /**
         * Called when the image was processed but no valid tag matches were found.
         *
         * @param labels list of detected image labels
         */
        void onMismatch(List<ImageLabel> labels);

        /**
         * Called when an error occurs during image processing or verification.
         *
         * @param e the exception thrown
         */
        void onError(Exception e);
    }

    /**
     * Verifies a quest by analyzing an image and comparing its labels with the quest's valid tags.
     *
     * <p>If a label with confidence above the threshold matches any valid tag,
     * the quest is marked as verified.</p>
     *
     * @param context     the application context
     * @param imageUri    URI of the image to be verified
     * @param quest       the quest being verified
     * @param description user-provided description (currently unused)
     * @param callback    the callback to handle the result of verification
     */
    public static void verify(Context context, Uri imageUri, Quest quest, String description, VerificationCallback callback) {
        try {
            InputImage image = InputImage.fromFilePath(context, imageUri);
            ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);

            labeler.process(image)
                    .addOnSuccessListener(labels -> {
                        List<String> validTags = TagInitializer.getMergedTags(context, quest);

                        boolean matchFound = false;

                        for (ImageLabel label : labels) {
                            String tag = label.getText().toLowerCase();
                            float confidence = label.getConfidence();

                            if (confidence > MIN_CONFIDENCE_THRESHOLD) {
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
