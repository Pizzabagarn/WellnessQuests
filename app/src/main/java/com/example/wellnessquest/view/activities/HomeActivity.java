package com.example.wellnessquest.view.activities;

import android.os.Bundle;

import com.example.wellnessquest.databinding.ActivityHomeBinding;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.model.UserManager;
import com.example.wellnessquest.model.UserStorage;

public class HomeActivity extends BaseDrawerActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔗 Ladda in din layout i content_frame
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        drawerBinding.contentFrame.addView(binding.getRoot());

        // 👤 Sätt aktuell användare (LiveData uppdateras automatiskt)
        User user = UserManager.getInstance().getCurrentUser();
        if (user != null) {
            userViewModel.setUser(user);
        }

        // 👁‍🗨 Om du vill binda layouten (t.ex. för att visa LiveData i home)
        binding.setUserViewModel(userViewModel);
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new UserStorage(getApplicationContext()).updateLastActive();

        User user = UserManager.getInstance().getCurrentUser();
        if (user != null) {
            userViewModel.setUser(user);
        }
    }
}
