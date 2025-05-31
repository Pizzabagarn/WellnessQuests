package com.example.wellnessquest.view.adapters;

import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;

import com.example.wellnessquest.R;

public class BindingAdapters {


    @BindingAdapter("strikeIfComplete")
    public static void strikeThrough(TextView view, boolean complete) {
        if (complete) {
            view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            view.setPaintFlags(view.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }


    @BindingAdapter("categoryIcon")
    public static void setCategoryIcon(ImageView view, String category) {
        if (category == null) return;

        switch (category.toLowerCase()) {
            case "mind":
                view.setImageResource(R.drawable.image_brain); // byt till rätt drawable
                break;
            case "fitness":
            default:
                view.setImageResource(R.drawable.image_strong_arm); // byt till rätt drawable
                break;
        }
    }
}
