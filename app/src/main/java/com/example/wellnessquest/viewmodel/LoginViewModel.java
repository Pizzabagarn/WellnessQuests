package com.example.wellnessquest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.wellnessquest.model.User;
import com.example.wellnessquest.model.UserManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * LoginViewModel manages user login and registration logic using Firebase Authentication
 * and Firestore. It exposes observable fields and LiveData for databinding and UI updates.
 *
 * @author Alexander Westman
 */
public class LoginViewModel extends AndroidViewModel {

    // Fields bound to input fields in the UI
    public MutableLiveData<String> email = new MutableLiveData<>("");
    public MutableLiveData<String> password = new MutableLiveData<>("");
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>("");

    // Observables to communicate with the UI
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToLogin = new MutableLiveData<>();

    // Firebase instances
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firestore;

    /**
     * Constructor initializes FirebaseAuth and Firestore instances.
     *
     * @param application The application context.
     */
    public LoginViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    /**
     * Called when the user clicks the "Log In" button. Authenticates using Firebase
     * and fetches user data from Firestore on success.
     */
    public void onLoginClicked() {
        String email = this.email.getValue();
        String pass = password.getValue();

        if (email == null || pass == null || email.isEmpty() || pass.isEmpty()) {
            showToast("Please enter both email and password");
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            firestore.collection("users")
                                    .document(firebaseUser.getUid())
                                    .get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            User user = documentSnapshot.toObject(User.class);

                                            if (user != null) {
                                                user.setUid(firebaseUser.getUid());
                                                UserManager.getInstance().setCurrentUser(user);

                                                showToast("Login successful!");
                                                loginSuccess.setValue(true);
                                            } else {
                                                showToast("Failed to parse user data.");
                                            }
                                        } else {
                                            showToast("User data not found.");
                                        }
                                    })
                                    .addOnFailureListener(e -> showToast("Error loading user data."));
                        }
                    } else {
                        showToast("Login failed: " + task.getException().getMessage());
                    }
                });
    }

    /**
     * Called when the user clicks the "Register" button. Validates input,
     * creates a new Firebase user, and saves a User object to Firestore.
     */
    public void onRegisterClicked() {
        String email = this.email.getValue();
        String pass = password.getValue();
        String confirm = confirmPassword.getValue();

        if (email == null || pass == null || confirm == null ||
                email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            showToast("Please fill in all fields");
            return;
        }

        if (!pass.equals(confirm)) {
            showToast("Passwords do not match");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            User user = new User(email);
                            user.setUid(firebaseUser.getUid());

                            firestore.collection("users")
                                    .document(firebaseUser.getUid())
                                    .set(user)
                                    .addOnSuccessListener(aVoid -> {
                                        showToast("Account created!");
                                        navigateToLogin.setValue(true);
                                    })
                                    .addOnFailureListener(e -> showToast("Failed to save user data."));
                        }
                    } else {
                        showToast("Registration failed: " + task.getException().getMessage());
                    }
                });
    }

    /**
     * Displays a toast message to the UI.
     *
     * @param message The message to be shown.
     */
    private void showToast(String message) {
        toastMessage.setValue(message);
    }

    /**
     * Returns observable for toast messages.
     */
    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    /**
     * Clears the toast message value.
     */
    public void clearToast() {
        toastMessage.setValue(null);
    }

    /**
     * Returns observable for login success flag.
     */
    public MutableLiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    /**
     * Resets the login success flag.
     */
    public void resetLoginSuccess() {
        loginSuccess.setValue(false);
    }

    /**
     * Returns observable for triggering navigation to login screen.
     */
    public MutableLiveData<Boolean> getNavigateToLogin() {
        return navigateToLogin;
    }

    /**
     * Resets navigation trigger flag.
     */
    public void resetNavigation() {
        navigateToLogin.setValue(false);
    }
}
