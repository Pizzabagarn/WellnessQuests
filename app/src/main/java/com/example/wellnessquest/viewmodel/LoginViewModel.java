package com.example.wellnessquest.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.wellnessquest.Model.User;
import com.example.wellnessquest.Model.UserStorage;

public class LoginViewModel extends AndroidViewModel {

    public MutableLiveData<String> username = new MutableLiveData<>("");
    public MutableLiveData<String> password = new MutableLiveData<>("");
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>("");

    private final MutableLiveData<Boolean> navigateToLogin = new MutableLiveData<>();
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();

    private final UserStorage userStorage;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userStorage = new UserStorage(application.getApplicationContext());
    }

    public void onRegisterClicked() {
        String user = username.getValue();
        String pass = password.getValue();
        String confirm = confirmPassword.getValue();

        if (user == null || pass == null || confirm == null || user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            toastMessage.setValue("Please fill in all fields");
            return;
        }

        if (!pass.equals(confirm)) {
            toastMessage.setValue("Passwords do not match");
            return;
        }

        if (userStorage.userExists(user)) {
            toastMessage.setValue("Username already exists");
            return;
        }

        User newUser = new User(user, pass);
        userStorage.addUser(newUser);
        toastMessage.setValue("Account created!");
        navigateToLogin.setValue(true); // Trigger navigation
    }

    public void onLoginClicked() {
        String user = username.getValue();
        String pass = password.getValue();

        if (user == null || pass == null || user.isEmpty() || pass.isEmpty()) {
            showToast("Please fill in all fields");
            return;
        }

        if (!userStorage.userExists(user)) {
            showToast("User does not exist");
            return;
        }

        if (!userStorage.validateCredentials(user, pass)) {
            showToast("Incorrect password");
            return;
        }

        User loggedInUser = userStorage.findUserByUsername(user);
        userStorage.saveCurrentUser(loggedInUser);

        showToast("Login successful!");
        loginSuccess.setValue(true);
    }

    private void showToast(String message) {
        toastMessage.setValue(message);
    }

    public void clearToast() {
        toastMessage.setValue(null);
    }

    public void resetNavigation() {
        navigateToLogin.setValue(false);
    }

    public LiveData<Boolean> getNavigateToLogin() {
        return navigateToLogin;
    }

    public void resetLoginSuccess() {
        loginSuccess.setValue(false);
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }
}
