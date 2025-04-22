package com.example.wellnessquest.view.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wellnessquest.R;
import com.example.wellnessquest.Model.Quest;

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_quest, parent, false);
        return new QuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestViewHolder holder, int position) {
        Quest quest = questList.get(position);

        holder.title.setText(quest.getTitle());
        holder.coins.setText(String.valueOf(quest.getRewardCoins()));

        // Stryk över klarade quests
        if (quest.isComplete()) {
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.title.setPaintFlags(holder.title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        // Visa ikon beroende på kategori (just nu bara en ikon, kan byggas ut)
        if (quest.getCategory().equalsIgnoreCase("Mind")) {
            holder.icon.setImageResource(R.drawable.image_brain); // temporär
        } else {
            holder.icon.setImageResource(R.drawable.image_strong_arm); //temporär
        }

        switch (quest.getId()) {
            case "q1":
                holder.image.setImageResource(R.drawable.demo_quest);
                break;
            case "q2":
                holder.image.setImageResource(R.drawable.quest_icon);
                break;
            default:
                holder.image.setImageResource(R.drawable.quest_container); // fallback-bild
                break;
        }


        // Klick på quest
        holder.itemView.setOnClickListener(v -> listener.onQuestClick(quest));
    }

    @Override
    public int getItemCount() {
        return questList.size();
    }

    public static class QuestViewHolder extends RecyclerView.ViewHolder {
        ImageView icon, image;
        TextView title, coins;


        public QuestViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.questIcon);
            title = itemView.findViewById(R.id.questTitle);
            coins = itemView.findViewById(R.id.questCoins);

        }
    }
}

