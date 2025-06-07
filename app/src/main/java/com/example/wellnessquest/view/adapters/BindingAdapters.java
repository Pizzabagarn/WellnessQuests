package com.example.wellnessquest.view.adapters;

import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;

import com.example.wellnessquest.R;

/**
 * BindingAdapters contains custom data binding methods used in XML layouts.
 * These adapters enable conditional styling and dynamic icon setting based on model data.
 *
 * @author Alexander Westman
 */
public class BindingAdapters {

    /**
     * Applies or removes a strikethrough effect on a TextView depending on whether a quest is completed.
     *
     * @param view the TextView to modify
     * @param complete true if the item is marked as complete, false otherwise
     */
    @BindingAdapter("strikeIfComplete")
    public static void strikeThrough(TextView view, boolean complete) {
        if (complete) {
            view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            view.setPaintFlags(view.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    /**
     * Sets an icon on an ImageView based on the quest category.
     *
     * @param view the ImageView to update
     * @param category the category string ("mind", "fitness", etc.)
     */
    @BindingAdapter("categoryIcon")
    public static void setCategoryIcon(ImageView view, String category) {
        if (category == null) return;

        switch (category.toLowerCase()) {
            case "mind":
                view.setImageResource(R.drawable.image_brain);
                break;
            case "fitness":
            default:
                view.setImageResource(R.drawable.image_strong_arm);
                break;
        }
    }
}
