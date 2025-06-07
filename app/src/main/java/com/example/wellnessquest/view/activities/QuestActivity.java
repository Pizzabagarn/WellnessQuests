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

/**
 * QuestActivity displays the list of available quests.
 * It extends BaseDrawerActivity and loads the {@link QuestListFragment}.
 * The activity also initializes the {@link UserViewModel} and loads the user's data from Firebase.
 *
 * <p>If no saved instance state exists, the quest list fragment is inserted into the layout.</p>
 *
 * @author Lowisa Svensson Christell
 * @author Alexander Westman
 */
public class QuestActivity extends BaseDrawerActivity {

    /**
     * Called when the activity is created.
     * Loads the layout, fetches user data via ViewModel, and displays the quest list fragment.
     *
     * @param savedInstanceState the saved state from a previous instance, or null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the activity layout into the drawer's content frame
        getLayoutInflater().inflate(R.layout.activity_quest, drawerBinding.contentFrame, true);

        // Fetch current user UID from Firebase Authentication and load user data into ViewModel
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.loadUser(uid);

        // Optional fallback/debug block if needed for local user object (commented out)
        /*
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        User user = UserManager.getInstance().getCurrentUser();
        if (user != null) {
            userViewModel.setUser(user);
        }
        */

        // Load the QuestListFragment into the layout if this is the first creation
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new QuestListFragment())
                    .commit();
        }
    }
}
