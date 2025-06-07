package com.example.wellnessquest.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wellnessquest.model.User;
import com.example.wellnessquest.model.UserManager;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * ProfileViewModel handles user profile data operations for the WellnessQuest app.
 * <p>
 * It manages communication with Firebase Firestore for retrieving and updating user data,
 * and exposes LiveData objects that the UI can observe for data updates, loading state,
 * and operation status.
 * </p>
 * <p>
 * This class follows the MVVM (Model-View-ViewModel) pattern to separate logic from UI components.
 * </p>
 *
 * @author Gen Félix Teramoto
 */
public class ProfileViewModel extends ViewModel {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> saveStatus = new MutableLiveData<>();

    /**
     * Fetches the user profile from Firestore using the user's unique ID.
     *
     * @param uid The user's Firebase Authentication UID
     * @return LiveData object representing the current user profile
     */
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

    /**
     * Saves the updated user profile to Firestore.
     * <p>
     * Only specific fields (name, age, purpose, and avatar) are updated. The user's
     * existing data is first retrieved before applying changes.
     * </p>
     *
     * @param uid         The user's Firebase Authentication UID
     * @param updatedData The {@link User} object containing updated profile information
     */
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


    /**
     * Returns a LiveData representing the current loading state.
     *
     * @return LiveData of type {@code Boolean}, true if loading is in progress
     */
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    /**
     * Returns a LiveData containing the latest save status message.
     *
     * @return LiveData of type {@code String} with a status or error message
     */
    public LiveData<String> getSaveStatus() {
        return saveStatus;
    }
}
