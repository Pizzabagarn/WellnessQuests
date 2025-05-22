package com.example.wellnessquest.view.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.model.UserManager;
import com.example.wellnessquest.view.fragments.QuestListFragment;
import com.example.wellnessquest.viewmodel.UserViewModel;

public class QuestActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 👇 Lägg in denna aktivitets layout inuti drawer-layoutens contentFrame
        getLayoutInflater().inflate(R.layout.activity_quest, drawerBinding.contentFrame, true);

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        User user = UserManager.getInstance().getCurrentUser();
        if (user != null) {
            userViewModel.setUser(user);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new QuestListFragment())
                    .commit();
        }
    }
}
