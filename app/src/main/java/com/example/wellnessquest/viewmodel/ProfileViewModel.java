package com.example.wellnessquest.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wellnessquest.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileViewModel extends ViewModel {

    private final DatabaseReference profileRef = FirebaseDatabase.getInstance().getReference("users");

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> saveStatus = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getSaveStatus() {
        return saveStatus;
    }

    public void saveUser(String userId, User user) {
        profileRef.child(userId).setValue(user)
                .addOnSuccessListener(aVoid -> saveStatus.setValue("Saved successfully!"))
                .addOnFailureListener(e -> {
                    saveStatus.setValue("Failed to save: " + e.getMessage());
                    Log.e("Firebase", "Save failed", e);
                });
    }

    public LiveData<User> getUserProfile(String userId) {
        MutableLiveData<User> userData = new MutableLiveData<>();
        isLoading.setValue(true);

        profileRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userData.setValue(user);
                isLoading.setValue(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "Failed to read user", error.toException());
                isLoading.setValue(false);
            }
        });

        return userData;
    }
}
