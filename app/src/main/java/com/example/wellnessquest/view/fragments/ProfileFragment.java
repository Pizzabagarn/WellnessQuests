package com.example.wellnessquest.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wellnessquest.R;

public class ProfileFragment extends Fragment {

    private int[] mAvatarResources = {
            R.drawable.avatar_black_hair,
            R.drawable.avatar_blond_hair,
            R.drawable.avatar_red_hair,
            R.drawable.avatar_brown_hair
    };

    private String[] mAvatarNames = {
            "avatar_black_hair",
            "avatar_blond_hair",
            "avatar_red_hair",
            "avatar_brown_hair"
    };

    private int mCurrentAvatarIndex = 0;
    private ImageView mAvatarImageView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("ProfileFragment", "onCreateView called");


        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("ProfileFragment", "onViewCreated called");

        mAvatarImageView = view.findViewById(R.id.imageView_avatar);
        ImageButton buttonPrevious = view.findViewById(R.id.button_previous_avatar);
        ImageButton buttonNext = view.findViewById(R.id.button_next_avatar);

        buttonPrevious.setOnClickListener(v -> showPreviousAvatar());
        buttonNext.setOnClickListener(v -> showNextAvatar());

        updateAvatarImage();
    }

    private void showPreviousAvatar() {
        mCurrentAvatarIndex = (mCurrentAvatarIndex - 1 + mAvatarResources.length) % mAvatarResources.length;
        updateAvatarImage();
    }

    private void showNextAvatar() {
        mCurrentAvatarIndex = (mCurrentAvatarIndex + 1) % mAvatarResources.length;
        updateAvatarImage();
    }

    private void updateAvatarImage() {
        mAvatarImageView.setImageResource(mAvatarResources[mCurrentAvatarIndex]);
    }

    public void setSelectedAvatar(String avatarName) {
        for (int i = 0; i < mAvatarNames.length; i++) {
            if (mAvatarNames[i].equals(avatarName)) {
                mCurrentAvatarIndex = i;
                updateAvatarImage();
                break;
            }
        }
    }

    public String getSelectedAvatar() {
        return mAvatarNames[mCurrentAvatarIndex];
    }
}