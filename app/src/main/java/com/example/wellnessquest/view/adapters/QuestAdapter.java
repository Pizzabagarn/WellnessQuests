package com.example.wellnessquest.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wellnessquest.databinding.ItemQuestBinding;
import com.example.wellnessquest.model.Quest;

import java.util.List;

/**
 * Adapter for displaying a list of {@link Quest} objects in a RecyclerView.
 * <p>
 * Each quest item is bound to a layout using data binding.
 * The adapter also handles click events via the {@link OnQuestClickListener} interface.
 * </p>
 *
 * This adapter is typically used in screens like the quest log.
 *
 * @author Mena Nasir
 */
public class QuestAdapter extends RecyclerView.Adapter<QuestAdapter.QuestViewHolder> {

    /** List of quests to be displayed */
    private final List<Quest> questList;

    /** Context in which the adapter is used */
    private final Context context;

    /** Listener for handling quest click events */
    private final OnQuestClickListener listener;

    /**
     * Interface for handling clicks on individual quest items.
     */
    public interface OnQuestClickListener {
        /**
         * Triggered when a quest item is clicked.
         *
         * @param quest The clicked quest
         */
        void onQuestClick(Quest quest);
    }

    /**
     * Constructs a new QuestAdapter.
     *
     * @param questList List of quests to be displayed
     * @param context   The current context
     * @param listener  Listener for quest click events
     */
    public QuestAdapter(List<Quest> questList, Context context, OnQuestClickListener listener) {
        this.questList = questList;
        this.context = context;
        this.listener = listener;
    }

    /**
     * Inflates the view for a single quest item and returns a ViewHolder.
     *
     * @param parent   The parent view group
     * @param viewType The type of the view (unused)
     * @return A new instance of {@link QuestViewHolder}
     */
    @NonNull
    @Override
    public QuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemQuestBinding binding = ItemQuestBinding.inflate(inflater, parent, false);
        return new QuestViewHolder(binding);
    }

    /**
     * Binds a quest to the provided ViewHolder.
     *
     * @param holder   The ViewHolder to bind data to
     * @param position The position of the quest in the list
     */
    @Override
    public void onBindViewHolder(@NonNull QuestViewHolder holder, int position) {
        Quest quest = questList.get(position);
        holder.binding.setQuest(quest);  // Kopplar datan till layouten
        holder.binding.executePendingBindings();
        holder.binding.getRoot().setOnClickListener(v -> listener.onQuestClick(quest));
    }

    /**
     * Returns the total number of quests in the list.
     *
     * @return Number of quests
     */
    @Override
    public int getItemCount() {
        return questList.size();
    }

    /**
     * ViewHolder class for holding references to the layout of a single quest item.
     */
    public static class QuestViewHolder extends RecyclerView.ViewHolder {

        /** Binding reference to the quest layout */
        ItemQuestBinding binding;

        /**
         * Constructs a new QuestViewHolder.
         *
         * @param binding The binding for the quest layout
         */
        public QuestViewHolder(ItemQuestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
