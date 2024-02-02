package com.jcobproject.dicegame2.viewElements;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private DiceResultListAdapter listAdapterFirstPlayer;
    private DiceResultListAdapter listAdapterSecondPlayer;

    private TextView firstPlayerTotal;
    private TextView secondPlayerTotal;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scorebord_1000game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewFirstPlayer = view.findViewById(R.id.listFirstPlayer);

        listAdapterFirstPlayer = new DiceResultListAdapter(firstPlayerScores);
        recyclerViewFirstPlayer.setAdapter(listAdapterFirstPlayer);
        recyclerViewFirstPlayer.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton rollBtnFirstPLayer = view.findViewById(R.id.rollBtnFirstPlayer);
        FloatingActionButton rollBtnSecondPlayer = view.findViewById(R.id.rollBtnSecondPlayer);


        firstPlayerTotal = view.findViewById(R.id.player_total_first);
        secondPlayerTotal = view.findViewById(R.id.player_total_second);

        rollBtnFirstPLayer.setOnClickListener(v -> {
            gameDice1000Service.rollTheDices(firstPlayerScores);
            swapPlayersUI(listAdapterFirstPlayer, firstPlayerScores, rollBtnFirstPLayer, rollBtnSecondPlayer);
            boolean isWinningScore = gameDice1000Service.checkScoreForWin(firstPlayerScores, firstPlayerTotal);
            if (isWinningScore)
                showWinningDialog("1");
        });


        RecyclerView recyclerViewSecondPlayer = view.findViewById(R.id.listSecondPlayer);

        listAdapterSecondPlayer = new DiceResultListAdapter(secondPlayerScores);
        recyclerViewSecondPlayer.setAdapter(listAdapterSecondPlayer);
        recyclerViewSecondPlayer.setLayoutManager(new LinearLayoutManager(getContext()));


        rollBtnSecondPlayer.setOnClickListener(v -> {
            gameDice1000Service.rollTheDices(secondPlayerScores);
            swapPlayersUI(listAdapterSecondPlayer, secondPlayerScores, rollBtnSecondPlayer, rollBtnFirstPLayer);
            boolean isWinningScore = gameDice1000Service.checkScoreForWin(secondPlayerScores, secondPlayerTotal);
            if (isWinningScore)
                showWinningDialog("2");
        });
        TextView playerNameFirst = view.findViewById(R.id.player_name_first);
        TextView playerNameSecond = view.findViewById(R.id.player_name_second);

        playerNameFirst.setVisibility(View.INVISIBLE);
        playerNameSecond.setVisibility(View.INVISIBLE);


    }

    private void showWinningDialog(String playerName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_win, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        TextView textView = dialogView.findViewById(R.id.text);
        textView.setText("Gracz " + playerName + " Wygrał!");

        Button restartGame = dialogView.findViewById(R.id.restartBtn);
        restartGame.setOnClickListener(v -> {
            clearData();
            dialog.dismiss();


        });

        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        dialog.getWindow().setDimAmount(0f);
        dialog.show();


    }

    private void clearData(){
        firstPlayerScores.clear();
        secondPlayerScores.clear();
        firstPlayerTotal.setText("Total 1");
        secondPlayerTotal.setText("Total 2");
        listAdapterFirstPlayer.notifyDataSetChanged();
        listAdapterSecondPlayer.notifyDataSetChanged();
    }

    private void swapPlayersUI(DiceResultListAdapter adapter, ArrayList<Integer> scores, FloatingActionButton visibleBtn, FloatingActionButton invisibleBtn) {
        adapter.notifyItemInserted(scores.size());
        visibleBtn.setVisibility(View.GONE);
        invisibleBtn.setVisibility(View.VISIBLE);
    }


}
