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

    public LoginViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    /**
     * Called when the user clicks the "Log In" button
     */
    public void onLoginClicked() {
        String email = this.email.getValue();
        String pass = password.getValue();

        if (email == null || pass == null || email.isEmpty() || pass.isEmpty()) {
            showToast("Please enter both email and password");
            return;
        }

        // Try to sign in using Firebase Authentication
        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            // Fetch additional user data from Firestore
                            firestore.collection("users")
                                    .document(firebaseUser.getUid())
                                    .get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            User user = documentSnapshot.toObject(User.class);
                                            UserManager.getInstance().setCurrentUser(user);

                                            // TODO: Store this user globally if needed
                                            showToast("Login successful!");
                                            loginSuccess.setValue(true);
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
     * Called when the user clicks the "Register" button
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

        // Try to create the user in Firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            // Create the custom User object
                            User user = new User(email);

                            // Save to Firestore
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

    // Show a toast message to the UI
    private void showToast(String message) {
        toastMessage.setValue(message);
    }

    // Expose toast message
    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void clearToast() {
        toastMessage.setValue(null);
    }

    // Expose login success status
    public MutableLiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public void resetLoginSuccess() {
        loginSuccess.setValue(false);
    }

    // Expose navigation trigger for UI
    public MutableLiveData<Boolean> getNavigateToLogin() {
        return navigateToLogin;
    }

    public void resetNavigation() {
        navigateToLogin.setValue(false);
    }
}
