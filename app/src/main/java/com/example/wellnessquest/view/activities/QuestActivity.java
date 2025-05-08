package com.example.wellnessquest.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wellnessquest.R;
import com.example.wellnessquest.model.Quest;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.viewmodel.QuestViewModel;
import com.example.wellnessquest.view.adapters.QuestAdapter;
import com.example.wellnessquest.viewmodel.UserViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class QuestActivity extends AppCompatActivity {

    private QuestViewModel questViewModel;
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private CheckBox checkBoxFitness, checkBoxMind;
    private QuestAdapter adapter;
    private List<Quest> allQuests = new ArrayList<>();
    private List<Quest> filteredQuests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        recyclerView = findViewById(R.id.recyclerViewQuests);
        checkBoxFitness = findViewById(R.id.checkbox_fitness);
        checkBoxMind = findViewById(R.id.checkbox_mind);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ViewModels
        questViewModel = new ViewModelProvider(this).get(QuestViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Hämta användare från UserManager och sätt i båda ViewModels
        User currentUser = com.example.wellnessquest.model.UserManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            questViewModel.setUser(currentUser);
            userViewModel.setUser(currentUser);
        }

        // Quests
        allQuests = questViewModel.getCurrentQuests();
        if (allQuests == null) allQuests = new ArrayList<>();

        // Adapter
        adapter = new QuestAdapter(filteredQuests, this, quest -> {
            View dialogView = LayoutInflater.from(this).inflate(R.layout.quest_details_dialog, null);

            TextView title = dialogView.findViewById(R.id.dialogQuestTitle);
            TextView description = dialogView.findViewById(R.id.dialogQuestDescription);
            Button buttonComplete = dialogView.findViewById(R.id.buttonMarkCompleted);
            Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

            title.setText(quest.getTitle());
            description.setText(quest.getDescription());

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(dialogView)
                    .setCancelable(true)
                    .create();

            buttonCancel.setOnClickListener(v -> dialog.dismiss());

            buttonComplete.setOnClickListener(v -> {
                if (!quest.isComplete()) {
                    // 1. Markera quest som klart
                    currentUser.completeQuest(quest.getId());

                    // 2. Uppdatera båda ViewModels + UserManager
                    questViewModel.setUser(currentUser);
                    userViewModel.setUser(currentUser);
                    userViewModel.refreshUserLiveData();
                    com.example.wellnessquest.model.UserManager.getInstance().setCurrentUser(currentUser);

                    // 3. Uppdatera Firebase
                    FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(currentUser.getUid())
                            .set(currentUser);

                    // 4. Uppdatera UI
                    allQuests = questViewModel.getCurrentQuests();
                    filterQuests();
                }

                dialog.dismiss();
            });

            dialog.show();
        });

        recyclerView.setAdapter(adapter);
        filterQuests();

        checkBoxFitness.setOnCheckedChangeListener((buttonView, isChecked) -> filterQuests());
        checkBoxMind.setOnCheckedChangeListener((buttonView, isChecked) -> filterQuests());
    }

    private void filterQuests() {
        filteredQuests.clear();

        for (Quest quest : allQuests) {
            String cat = quest.getCategory().toLowerCase();
            boolean showFitness = checkBoxFitness.isChecked();
            boolean showMind = checkBoxMind.isChecked();

            if ((cat.equals("fitness") && showFitness) || (cat.equals("mind") && showMind)) {
                filteredQuests.add(quest);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
