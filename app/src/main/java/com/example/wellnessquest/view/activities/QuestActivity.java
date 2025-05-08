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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wellnessquest.R;
import com.example.wellnessquest.model.Quest;
import com.example.wellnessquest.viewmodel.QuestViewModel;
import com.example.wellnessquest.view.adapters.QuestAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class QuestActivity extends AppCompatActivity {

    private QuestViewModel viewModel;
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

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(QuestViewModel.class);

        allQuests = viewModel.getCurrentQuests();
        if (allQuests == null) allQuests = new ArrayList<>();

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
                    viewModel.getUser().completeQuest(quest.getId());
                    com.example.wellnessquest.model.UserManager.getInstance().setCurrentUser(viewModel.getUser());
                    allQuests = viewModel.getCurrentQuests();
                    filterQuests();

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String uid = viewModel.getUser().getUid();

                    db.collection("users").document(uid).set(viewModel.getUser())
                            .addOnSuccessListener(aVoid -> Log.d("FIREBASE", "Användare uppdaterad i Firebase"))
                            .addOnFailureListener(e -> Log.e("FIREBASE", "Misslyckades att uppdatera användaren", e));
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
