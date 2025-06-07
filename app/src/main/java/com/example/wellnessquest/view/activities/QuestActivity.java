package com.example.wellnessquest.view.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.model.UserManager;
import com.example.wellnessquest.view.fragments.QuestListFragment;
import com.example.wellnessquest.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class QuestActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getLayoutInflater().inflate(R.layout.activity_quest, drawerBinding.contentFrame, true);

        //Lowisa gjort detta kodblock
        //Attempts to sign in a user with the provided email and password using Firebase Authentication.
        // * If authentication succeeds, the method fetches the user's additional data from Firestore,
        // * sets the UID, and stores the user in the singleton
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.loadUser(uid); // 🔁 ladda uppdaterad användardata

        /* DEBUGGING - kollar om allt funkar om detta kodblock byts mot det ovan
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        User user = UserManager.getInstance().getCurrentUser();
        if (user != null) {
            userViewModel.setUser(user);
        } */

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new QuestListFragment())
                    .commit();
        }
    }
}
