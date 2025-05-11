package com.example.wellnessquest.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.FragmentQuestListBinding;
import com.example.wellnessquest.model.Quest;
import com.example.wellnessquest.model.QuestRepository;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.view.adapters.QuestAdapter;
import com.example.wellnessquest.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class QuestListFragment extends Fragment {
    private FragmentQuestListBinding binding;
    private QuestAdapter adapter;
    private List<Quest> allQuests = new ArrayList<>();
    private List<Quest> filteredQuests = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentQuestListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hämta ViewModel
        UserViewModel userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // Initiera adapter
        adapter = new QuestAdapter(filteredQuests, requireContext(), quest -> {
            QuestDetailsFragment detailsFragment = new QuestDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("quest", quest);
            detailsFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        });


        binding.recyclerViewQuests.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewQuests.setAdapter(adapter);

        // Observera user
        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                Log.d("QuestDebug", "User is null");
                return;
            }

            Log.d("QuestDebug", "User level: " + user.getCurrentLevel());

            allQuests = QuestRepository.getLevel(user.getCurrentLevel()).getQuests();
            Log.d("QuestDebug", "Fetched quests count: " + allQuests.size());

            // Markera slutförda
            List<String> completedIds = new ArrayList<>();
            for (Quest completed : user.getCompletedQuests()) {
                completedIds.add(completed.getId());
            }
            for (Quest quest : allQuests) {
                quest.setCompleted(completedIds.contains(quest.getId()));
            }

            // Filtrera
            filterQuests();
            Log.d("QuestDebug", "Filtered quests count: " + filteredQuests.size());

            adapter.notifyDataSetChanged();
        });

        // CheckBox-filters
        binding.checkboxFitness.setOnCheckedChangeListener((button, isChecked) -> {
            filterQuests();
            adapter.notifyDataSetChanged();
        });
        binding.checkboxMind.setOnCheckedChangeListener((button, isChecked) -> {
            filterQuests();
            adapter.notifyDataSetChanged();
        });
    }

    private void filterQuests() {
        filteredQuests.clear();
        boolean showFitness = binding.checkboxFitness.isChecked();
        boolean showMind = binding.checkboxMind.isChecked();

        for (Quest quest : allQuests) {
            String category = quest.getCategory().toLowerCase();
            if ((category.equals("fitness") && showFitness) ||
                    (category.equals("mind") && showMind)) {
                filteredQuests.add(quest);
            }
        }
    }

}
