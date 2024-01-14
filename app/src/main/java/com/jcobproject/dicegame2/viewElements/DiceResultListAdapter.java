package com.jcobproject.dicegame2.viewElements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jcobproject.dicegame2.R;

import java.util.ArrayList;

public class DiceResultListAdapter extends RecyclerView.Adapter<DiceResultListAdapter.ViewHolder> {

    private final ArrayList<Integer> playerScores;

    public DiceResultListAdapter(ArrayList<Integer> playersResults) {
        this.playerScores = playersResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Integer diceValue = playerScores.get(position);
        holder.text.setText(String.valueOf(diceValue));

    }

    @Override
    public int getItemCount() {
        return playerScores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }

    }


}
