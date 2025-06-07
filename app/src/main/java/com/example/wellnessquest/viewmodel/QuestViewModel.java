package com.example.wellnessquest.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.wellnessquest.model.Quest;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.model.QuestRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * QuestViewModel provides quest data for the UI and handles logic
 * related to retrieving and displaying quests for the current user.
 *
 * @author Alexander Westman
 * @author Mena Nasir
 */
public class QuestViewModel extends AndroidViewModel {

    private User user;

    /**
     * Constructor that takes the application context.
     *
     * @param application The application context
     */
    public QuestViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Sets the current user object.
     *
     * @param user The current user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the current user object.
     *
     * @return The current user
     */
    public User getUser() {
        return user;
    }

    /**
     * Retrieves the list of quests for the user's current level,
     * marking those that have already been completed.
     *
     * @param user The user whose quests should be retrieved
     * @return A list of quests with completion status
     */
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
