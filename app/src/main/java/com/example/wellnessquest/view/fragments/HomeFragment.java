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

    public class HomeFragment extends Fragment {

        private TextView welcomeText;
        private ImageView avatarImage;


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_home, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            welcomeText = view.findViewById(R.id.home_welcome_text);
            avatarImage = view.findViewById(R.id.home_avatar_image);

        }

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


