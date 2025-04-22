package com.example.wellnessquest.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wellnessquest.R;

import android.widget.CheckBox;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wellnessquest.model.Quest;
import com.example.wellnessquest.viewmodel.QuestViewModel;
import com.example.wellnessquest.view.adapters.QuestAdapter;

import java.util.ArrayList;
import java.util.List;

public class QuestActivity extends AppCompatActivity {

    private QuestViewModel viewModel;
    private RecyclerView recyclerView;
    private CheckBox checkBoxFitness, checkBoxMind;
    private QuestAdapter adapter;
    private List<Quest> allQuests = new ArrayList<>();
    private List<Quest> filteredQuests = new ArrayList<>(); // för filtrering

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        // Initiera vyer
        recyclerView = findViewById(R.id.recyclerViewQuests);
        checkBoxFitness = findViewById(R.id.checkbox_fitness);
        checkBoxMind = findViewById(R.id.checkbox_mind);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Hämta ViewModel
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(QuestViewModel.class);


        // Hämta quests från användaren
        allQuests = viewModel.getCurrentQuests();
        if (allQuests == null) allQuests = new ArrayList<>();

        // Initiera adapter
        adapter = new QuestAdapter(filteredQuests, this, quest -> {
            // Klick på ett quest → visa popup
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle(quest.getTitle());
            builder.setMessage(quest.getDescription());
            builder.setPositiveButton("OK", null);
            builder.show();
        });

        recyclerView.setAdapter(adapter);

        // Sätt filter default
        filterQuests();

        // När man klickar i/ur checkboxar
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
