package com.example.wellnessquest.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.wellnessquest.model.Quest;
import com.example.wellnessquest.model.User;
import java.util.ArrayList;
import java.util.List;

public class QuestViewModel extends AndroidViewModel {

    private User user;

    public QuestViewModel(@NonNull Application application) {
        super(application);
        // Du kan lägga till initkod här senare om du vill
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    /*
    public List<Quest> getCurrentQuests() {
        if (user != null) {
            return user.getCurrentQuests();
        } else {
            return new ArrayList<>();
        }
    }

     */

    public List<Quest> getCurrentQuests() {
        if (user == null) {
            // Tillfälligt testdata
            List<Quest> dummy = new ArrayList<>();
            dummy.add(new Quest("q1", "Meditera i 5 min", "Sätt dig tyst och andas i fem minuter", "Mind", true, 10, 1));
            dummy.add(new Quest("q2", "Gå 2000 steg", "Ta en kort promenad idag", "Fitness", false, 15, 1));
            dummy.add(new Quest("q3", "Skriv dagbok", "Reflektera över din dag", "Mind", false, 10, 2));
            dummy.add(new Quest("q4", "Gör 20 squats", "Snabb styrkeövning hemma", "Fitness", false, 20, 2));
            return dummy;
        }
        return user.getCurrentQuests();
    }

}

