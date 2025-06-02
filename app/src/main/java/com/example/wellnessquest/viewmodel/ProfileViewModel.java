package com.example.wellnessquest.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wellnessquest.model.User;
import com.example.wellnessquest.model.UserManager;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileViewModel extends ViewModel {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> saveStatus = new MutableLiveData<>();

    public LiveData<User> getUserProfile(String uid) {
        isLoading.setValue(true);
        firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        User fetchedUser = snapshot.toObject(User.class);
                        user.setValue(fetchedUser);
                    }
                    isLoading.setValue(false);
                })
                .addOnFailureListener(e -> {
                    saveStatus.setValue("Failed to load user: " + e.getMessage());
                    isLoading.setValue(false);
                });
        return user;
    }

    public void saveUser(String uid, User updatedData) {
        isLoading.setValue(true);

        firestore.collection("users").document(uid).get()
                .addOnSuccessListener(snapshot -> {
                    User snapshotUser = snapshot.toObject(User.class);

                    if (snapshotUser != null) {
                        // ✅ Uppdatera enbart relevanta profilfält
                        snapshotUser.setName(updatedData.getName());
                        snapshotUser.setAge(updatedData.getAge());
                        snapshotUser.setPurpose(updatedData.getPurpose());
                        snapshotUser.setAvatar(updatedData.getAvatar());

                        firestore.collection("users").document(uid)
                                .set(snapshotUser)
                                .addOnSuccessListener(aVoid -> {
                                    UserManager.getInstance().setCurrentUser(snapshotUser);
                                    saveStatus.setValue("Profile saved!");
                                    isLoading.setValue(false);
                                })
                                .addOnFailureListener(e -> {
                                    saveStatus.setValue("Error saving profile: " + e.getMessage());
                                    isLoading.setValue(false);
                                });
                    } else {
                        saveStatus.setValue("No existing user data to update");
                        isLoading.setValue(false);
                    }
                })
                .addOnFailureListener(e -> {
                    saveStatus.setValue("Error loading existing user: " + e.getMessage());
                    isLoading.setValue(false);
                });
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getSaveStatus() {
        return saveStatus;
    }
}
