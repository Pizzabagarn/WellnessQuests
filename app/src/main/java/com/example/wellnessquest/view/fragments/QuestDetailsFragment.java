// QuestDetailsFragment.java
package com.example.wellnessquest.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.FragmentQuestDetailsBinding;
import com.example.wellnessquest.model.Quest;

/**
 * QuestDetailsFragment displays detailed information about a specific quest
 * and allows the user to proceed to the ProofFragment to verify its completion.
 *
 * @author Alexander Westman
 * @author Mena Nasir
 */
public class QuestDetailsFragment extends Fragment {

    private FragmentQuestDetailsBinding binding;
    private Quest quest;

    /**
     * Inflates the layout for this fragment using data binding.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuestDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Sets up the quest data and click listener for the verify button.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey("quest")) {
            quest = (Quest) getArguments().getSerializable("quest");
            binding.setQuest(quest);
        }

        binding.buttonVerify.setOnClickListener(v -> {
            // Navigate to ProofFragment with the current quest as argument
            ProofFragment proofFragment = new ProofFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("quest", quest);
            proofFragment.setArguments(bundle);

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            0,  // in-animation
                            0,  // out-animation
                            R.anim.fade_in,   // enter animation when returning
                            R.anim.fade_out   // exit animation when returning
                    )
                    .replace(R.id.fragment_container, proofFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
