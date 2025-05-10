package com.example.wellnessquest.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wellnessquest.databinding.ItemQuestBinding;
import com.example.wellnessquest.model.Quest;

import java.util.List;

public class QuestAdapter extends RecyclerView.Adapter<QuestAdapter.QuestViewHolder> {

    private final List<Quest> questList;
    private final Context context;
    private final OnQuestClickListener listener;

    public interface OnQuestClickListener {
        void onQuestClick(Quest quest);
    }

    public QuestAdapter(List<Quest> questList, Context context, OnQuestClickListener listener) {
        this.questList = questList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemQuestBinding binding = ItemQuestBinding.inflate(inflater, parent, false);
        return new QuestViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestViewHolder holder, int position) {
        Quest quest = questList.get(position);
        holder.binding.setQuest(quest);  // Kopplar datan till layouten
        holder.binding.executePendingBindings();

        // Klick-hantering
        holder.binding.getRoot().setOnClickListener(v -> listener.onQuestClick(quest));
    }

    @Override
    public int getItemCount() {
        return questList.size();
    }

    public static class QuestViewHolder extends RecyclerView.ViewHolder {
        ItemQuestBinding binding;

        public QuestViewHolder(ItemQuestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
