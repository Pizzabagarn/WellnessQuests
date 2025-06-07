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

/**
 * ProfileFragment handles avatar selection for the user profile in the WellnessQuest app.
 * <p>
 * The fragment allows users to browse and select between different avatar images
 * by clicking "next" and "previous" buttons. The selected avatar is shared with
 * {@link com.example.wellnessquest.view.activities.ProfileActivity} to be saved in the user profile.
 * </p>
 * <p>Avatars are represented by drawable resources and identified by corresponding names.</p>
 *
 * @author Gen
 */

public class ProfileFragment extends Fragment {

    /** Resource IDs for available avatar images */
    private int[] mAvatarResources = {
            R.drawable.avatar_black_hair,
            R.drawable.avatar_blond_hair,
            R.drawable.avatar_red_hair,
            R.drawable.avatar_brown_hair
    };

    /** String identifiers corresponding to the avatar images */
    private String[] mAvatarNames = {
            "avatar_black_hair",
            "avatar_blond_hair",
            "avatar_red_hair",
            "avatar_brown_hair"
    };

    private int mCurrentAvatarIndex = 0; // Index of the currently selected avatar
    private ImageView mAvatarImageView; // ImageView for displaying selected avatar

    /**
     * Inflates the layout for this fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views
     * @param container          If non-null, this is the parent view that the fragment's UI should attach to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return The root view of the fragment
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("ProfileFragment", "onCreateView called");


        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    /**
     * Called after the view has been created. Initializes UI components and sets up listeners.
     *
     * @param view               The view returned by {@link #onCreateView}
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     */
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

    /**
     * Cycles to the previous avatar in the list and updates the displayed image.
     */
    private void showPreviousAvatar() {
        mCurrentAvatarIndex = (mCurrentAvatarIndex - 1 + mAvatarResources.length) % mAvatarResources.length;
        updateAvatarImage();
    }

    /**
     * Cycles to the next avatar in the list and updates the displayed image.
     */
    private void showNextAvatar() {
        mCurrentAvatarIndex = (mCurrentAvatarIndex + 1) % mAvatarResources.length;
        updateAvatarImage();
    }

    /**
     * Updates the ImageView to show the currently selected avatar.
     */
    private void updateAvatarImage() {
        mAvatarImageView.setImageResource(mAvatarResources[mCurrentAvatarIndex]);
    }

    /**
     * Sets the avatar to match the provided name.
     *
     * @param avatarName The name of the avatar to select (e.g., "avatar_red_hair")
     */
    public void setSelectedAvatar(String avatarName) {
        for (int i = 0; i < mAvatarNames.length; i++) {
            if (mAvatarNames[i].equals(avatarName)) {
                mCurrentAvatarIndex = i;
                updateAvatarImage();
                break;
            }
        }
    }

    /**
     * Gets the name of the currently selected avatar.
     *
     * @return The selected avatar name (e.g., "avatar_blond_hair")
     */
    public String getSelectedAvatar() {
        return mAvatarNames[mCurrentAvatarIndex];
    }
}