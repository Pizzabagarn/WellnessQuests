package com.example.wellnessquest.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wellnessquest.R;
import com.example.wellnessquest.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

    /**
     * HomeFragment displays a personalized welcome message and avatar for the current user.
     * <p>
     * It fetches user data from Firebase Firestore when the fragment resumes, and dynamically
     * updates the UI based on the user's name, purpose, and selected avatar.
     * </p>
     *
     * <p>
     * This fragment is part of the home screen functionality in the WellnessQuest application.
     * </p>
     *
     * @author Gen
     */
    public class HomeFragment extends Fragment {

        private TextView welcomeText;
        private ImageView avatarImage;

        /**
         * Inflates the layout for the fragment.
         *
         * @param inflater           The LayoutInflater used to inflate views
         * @param container          The parent view that this fragment's UI should be attached to
         * @param savedInstanceState The previously saved instance state, if any
         * @return The root view for this fragment
         */
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_home, container, false);
        }

        /**
         * Called after the view is created. Initializes UI components.
         *
         * @param view               The root view of the fragment
         * @param savedInstanceState The previously saved instance state, if any
         */
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            welcomeText = view.findViewById(R.id.home_welcome_text);
            avatarImage = view.findViewById(R.id.home_avatar_image);

        }

        /**
         * Called when the fragment becomes visible again.
         * Fetches the latest user data from Firestore and updates the welcome message and avatar.
         */
        @Override
        public void onResume() {
            super.onResume();

            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseFirestore.getInstance().collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (snapshot.exists()) {
                            User user = snapshot.toObject(User.class);
                            if (user != null) {
                                String name = user.getName() != null ? user.getName() : "";
                                String purpose = user.getPurpose() != null ? user.getPurpose() : "";
                                String welcomeMessage = "Welcome back " + name + "!\n\n"
                                        + "Your stated purpose is: " + purpose;
                                welcomeText.setText(welcomeMessage);

                                String avatarName = user.getAvatar() != null ? user.getAvatar() : "avatar_black_hair";
                                int avatarResId = getAvatarResourceId(avatarName);
                                avatarImage.setImageResource(avatarResId);
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        welcomeText.setText("Welcome back! (failed to load profile)");
                    });
        }


        /**
         * Maps an avatar name string to its corresponding drawable resource ID.
         *
         * @param avatarName The name of the avatar (e.g., "avatar_red_hair")
         * @return The resource ID of the corresponding drawable
         */
        private int getAvatarResourceId(String avatarName) {
            switch (avatarName) {
                case "avatar_black_hair":
                    return R.drawable.avatar_black_hair;
                case "avatar_blond_hair":
                    return R.drawable.avatar_blond_hair;
                case "avatar_red_hair":
                    return R.drawable.avatar_red_hair;
                case "avatar_brown_hair":
                    return R.drawable.avatar_brown_hair;
                default:
                    return R.drawable.avatar_black_hair; // fallback
            }
        }
    }


