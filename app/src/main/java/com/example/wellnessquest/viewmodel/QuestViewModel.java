package com.example.wellnessquest.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.wellnessquest.model.Quest;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.model.QuestRepository;

import java.util.ArrayList;
import java.util.List;

public class QuestViewModel extends AndroidViewModel {

    private User user;

    public QuestViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public List<Quest> getCurrentQuests(User user) {
        if (user == null) return new ArrayList<>();

        List<Quest> allQuests = QuestRepository.getLevel(user.getCurrentLevel()).getQuests();
        List<String> completedIds = user.getCompletedQuests();

        for (Quest quest : allQuests) {
            quest.setCompleted(completedIds != null && completedIds.contains(quest.getId()));
        }

        return allQuests;
    }
}
