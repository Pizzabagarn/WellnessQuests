package com.example.wellnessquest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.wellnessquest.model.User;

public class UserViewModel extends ViewModel {

    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    // Sätt användare
    public void setUser(User user) {
        userLiveData.setValue(user);
    }

    // Hämta LiveData
    public LiveData<User> getUser() {
        return userLiveData;
    }

    // Direkt åtkomst till User-objektet
    public User getCurrentUser() {
        return userLiveData.getValue();
    }

    // Databinding för visning av coins
    public LiveData<String> getCoinsText() {
        return Transformations.map(userLiveData, user -> {
            if (user != null) {
                return "Coins: " + user.getCoins();
            } else {
                return "Coins: 0";
            }
        });
    }

    // Lägg till coins och uppdatera LiveData
    public void addCoins(int coins) {
        User user = userLiveData.getValue();
        if (user != null) {
            user.earnCoins(coins);
            refreshUserLiveData(); // Tvingar databinding att uppdateras
        }
    }

    // Uppdaterar LiveData med nytt User-objekt (för databinding)
    public void refreshUserLiveData() {
        User current = userLiveData.getValue();
        if (current != null) {
            User updated = new User(current.getEmail());
            updated.setUid(current.getUid());
            updated.setCoins(current.getCoins());
            updated.setLevel(current.getLevel());
            updated.setCurrentQuests(current.getCurrentQuests());
            updated.setCompletedQuests(current.getCompletedQuests());

            userLiveData.setValue(updated);
        }
    }
}
