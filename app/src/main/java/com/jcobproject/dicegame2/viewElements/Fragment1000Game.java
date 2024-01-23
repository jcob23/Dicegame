package com.jcobproject.dicegame2.viewElements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jcobproject.dicegame2.R;
import com.jcobproject.dicegame2.service.GameDice1000Service;

import java.util.ArrayList;

public class Fragment1000Game extends Fragment {


    private final GameDice1000Service gameDice1000Service = GameDice1000Service.getInstance();

    private final ArrayList<Integer> firstPlayerScores = new ArrayList<>();
    private final ArrayList<Integer> secondPlayerScores = new ArrayList<>();



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scorebord_1000game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewFirstPlayer = view.findViewById(R.id.listFirstPlayer);
        DiceResultListAdapter listAdapterFirstPlayer = new DiceResultListAdapter(firstPlayerScores);
        recyclerViewFirstPlayer.setAdapter(listAdapterFirstPlayer);
        recyclerViewFirstPlayer.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton rollBtnFirstPLayer = view.findViewById(R.id.rollBtnFirstPlayer);
        FloatingActionButton rollBtnSecondPlayer = view.findViewById(R.id.rollBtnSecondPlayer);

        TextView firstPlayerTotal = view.findViewById(R.id.player_total_first);
        TextView secondPlayerTotal = view.findViewById(R.id.player_total_second);

        rollBtnFirstPLayer.setOnClickListener(v -> {
            gameDice1000Service.rollTheDices(firstPlayerScores);
            swapPlayersUI(listAdapterFirstPlayer, firstPlayerScores, rollBtnFirstPLayer, rollBtnSecondPlayer);
            boolean isWinningScore = gameDice1000Service.checkScoreForWin(firstPlayerScores, firstPlayerTotal);
        });


        RecyclerView recyclerViewSecondPlayer = view.findViewById(R.id.listSecondPlayer);
        DiceResultListAdapter listAdapterSecondPlayer = new DiceResultListAdapter(secondPlayerScores);
        recyclerViewSecondPlayer.setAdapter(listAdapterSecondPlayer);
        recyclerViewSecondPlayer.setLayoutManager(new LinearLayoutManager(getContext()));


        rollBtnSecondPlayer.setOnClickListener(v -> {
            gameDice1000Service.rollTheDices(secondPlayerScores);
            swapPlayersUI(listAdapterFirstPlayer, firstPlayerScores, rollBtnSecondPlayer, rollBtnFirstPLayer);
            boolean isWinningScore = gameDice1000Service.checkScoreForWin(secondPlayerScores, secondPlayerTotal);

        });
        TextView playerNameFirst = view.findViewById(R.id.player_name_first);
        TextView playerNameSecond = view.findViewById(R.id.player_name_second);



    }

    private void swapPlayersUI(DiceResultListAdapter adapter, ArrayList<Integer> scores, FloatingActionButton visibleBtn, FloatingActionButton invisibleBtn) {
        adapter.notifyItemInserted(scores.size());
        visibleBtn.setVisibility(View.GONE);
        invisibleBtn.setVisibility(View.VISIBLE);
    }



}
