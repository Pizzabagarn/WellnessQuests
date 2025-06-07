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
import com.example.wellnessquest.utils.SoundManager;
import com.example.wellnessquest.view.adapters.QuestAdapter;
import com.example.wellnessquest.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that displays a filtered list of quests in a RecyclerView.
 * <p>
 * Quests are fetched based on the user's current level, and marked as completed
 * if they exist in the user's completed quest list. The list can be filtered using
 * category-based checkboxes (Fitness and Mind).
 * </p>
 *
 * This fragment also handles quest click events, navigating to {@link QuestDetailsFragment}
 * and passing the selected quest.
 *
 * @author Mena Nasir
 * @author Alexander Westman
 */
public class QuestListFragment extends Fragment {

    /** View binding for this fragment */
    private FragmentQuestListBinding binding;

    /** Adapter for the RecyclerView */
    private QuestAdapter adapter;

    /** All quests available for the user's level */
    private List<Quest> allQuests = new ArrayList<>();

    /** Quests shown after applying filters */
    private List<Quest> filteredQuests = new ArrayList<>();

    /**
     * Inflates the layout for this fragment using view binding.
     *
     * @param inflater The LayoutInflater object
     * @param container The parent view group
     * @param savedInstanceState Previously saved state
     * @return The root view of the binding
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentQuestListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Called after the fragment's view has been created.
     * <p>
     * Sets up the RecyclerView, loads quests from the repository,
     * observes user data, and handles category filtering.
     * </p>
     *
     * @param view The fragment's root view
     * @param savedInstanceState Previously saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserViewModel userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        adapter = new QuestAdapter(filteredQuests, requireContext(), quest -> {

            SoundManager.getInstance(requireContext()).playQuestSound();
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

        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                return;
            }
            allQuests = QuestRepository.getLevel(user.getCurrentLevel()).getQuests();

            // Markera slutförda
            List<String> completedIds = user.getCompletedQuests();

            for (Quest quest : allQuests) {
                quest.setCompleted(completedIds != null && completedIds.contains(quest.getId()));
            }

            // Filtrera
            filterQuests();
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

    /**
     * Filters the list of quests based on the selected checkboxes.
     * Only quests matching the selected categories ("Fitness" and/or "Mind") are shown.
     */
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
