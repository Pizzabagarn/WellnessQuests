package com.example.wellnessquest.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wellnessquest.R;
import com.example.wellnessquest.view.fragments.LoginFragment;

/**
 * Displays the login screen of the app by loading {@link LoginFragment}
 * into the activity layout. This activity is typically shown when the
 * app starts and no user session is active.
 *
 * @author Alexander Westman
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Called when the activity is created.
     * Sets the login layout and loads the LoginFragment if no saved state exists.
     *
     * @param savedInstanceState the previously saved state, or null if first launch
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }
}
