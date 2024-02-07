package com.jcobproject.dicegame2.viewElements;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jcobproject.dicegame2.Entity.Player;
import com.jcobproject.dicegame2.R;
import com.jcobproject.dicegame2.service.DiceRollerService;
import com.jcobproject.dicegame2.service.GameDice1000Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Fragment1000Game extends Fragment {


    private final GameDice1000Service gameDice1000Service = GameDice1000Service.getInstance();
    private final DiceRollerService diceRollerService = DiceRollerService.getInstance();

    private final Player firstPlayer = new Player();
    private final Player secondPlayer = new Player();

    private DiceResultListAdapter listAdapterFirstPlayer;
    private DiceResultListAdapter listAdapterSecondPlayer;

    private TextView firstPlayerTotal;
    private TextView secondPlayerTotal;

    private int currentColor = R.color.green;

    private FloatingActionButton rollBtnFirstPLayer;
    private FloatingActionButton rollBtnSecondPlayer;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scorebord_1000game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewFirstPlayer = view.findViewById(R.id.listFirstPlayer);

        listAdapterFirstPlayer = new DiceResultListAdapter(firstPlayer.getScores());
        recyclerViewFirstPlayer.setAdapter(listAdapterFirstPlayer);
        recyclerViewFirstPlayer.setLayoutManager(new LinearLayoutManager(getContext()));

        rollBtnFirstPLayer = view.findViewById(R.id.rollBtnFirstPlayer);
        rollBtnSecondPlayer = view.findViewById(R.id.rollBtnSecondPlayer);

        firstPlayerTotal = view.findViewById(R.id.player_total_first);
        secondPlayerTotal = view.findViewById(R.id.player_total_second);

        rollBtnFirstPLayer.setOnClickListener(v -> {
            showDiceRollDialog(firstPlayer, firstPlayerTotal, listAdapterFirstPlayer);
        });


        RecyclerView recyclerViewSecondPlayer = view.findViewById(R.id.listSecondPlayer);

        listAdapterSecondPlayer = new DiceResultListAdapter(secondPlayer.getScores());
        recyclerViewSecondPlayer.setAdapter(listAdapterSecondPlayer);
        recyclerViewSecondPlayer.setLayoutManager(new LinearLayoutManager(getContext()));

        rollBtnSecondPlayer.setOnClickListener(v -> {
            showDiceRollDialog(secondPlayer, secondPlayerTotal, listAdapterSecondPlayer);
        });
        TextView playerNameFirst = view.findViewById(R.id.player_name_first);
        TextView playerNameSecond = view.findViewById(R.id.player_name_second);

        playerNameFirst.setVisibility(View.INVISIBLE);
        playerNameSecond.setVisibility(View.INVISIBLE);


    }
    private void showDiceRollDialog(Player player,TextView playerTotal, RecyclerView.Adapter<? extends DiceResultListAdapter.ViewHolder> adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_diceroll, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        ImageView dice1 = dialogView.findViewById(R.id.dice1);
        ImageView dice2 = dialogView.findViewById(R.id.dice2);
        ImageView dice3 = dialogView.findViewById(R.id.dice3);
        ImageView dice4 = dialogView.findViewById(R.id.dice4);
        ImageView dice5 = dialogView.findViewById(R.id.dice5);
        Button rerollBtn = dialogView.findViewById(R.id.btnReroll);

        ImageView[] diceViews = new ImageView[]{dice1, dice2, dice3, dice4, dice5};
        for (ImageView diceView : diceViews) {
            diceView.setActivated(false);
            diceView.setOnClickListener(v -> {
                diceView.setActivated(!diceView.isActivated());
                diceView.setBackgroundResource(diceView.isActivated() ? currentColor : R.color.brown);
                setRerollButtonVisibility(rerollBtn, diceViews);
            });
        }

        int[] dices = diceRollerService.roll5D6();
        setUpDices(dices, diceViews);
        TextView currentScore = dialogView.findViewById(R.id.score);
        player.setCurrentRollScore(gameDice1000Service.calculateScore(dices));
        currentScore.setText("Score: " + player.getCurrentRollScore());


        Button acceptScoreBtn = dialogView.findViewById(R.id.btnAccept);
        acceptScoreBtn.setOnClickListener(v -> {
            dialog.dismiss();
            gameDice1000Service.addResultToPlayerScore(player.getScores(), player.getCurrentRollScore());
            boolean isWinningScore = gameDice1000Service.checkScoreForWin(player);

            if (isWinningScore){
                player.summarizeGame(true);
                showWinningDialog(player.getName());
            }
            adapter.notifyDataSetChanged();
            playerTotal.setText("" + player.getTotalPoints());
            swapPlayersUI(rollBtnFirstPLayer, rollBtnSecondPlayer);
        });

        rerollBtn.setOnClickListener(v -> {
            for (int i = 0; i < diceViews.length; i++) {
                ImageView diceView = diceViews[i];
                if (diceView.isActivated()) {
                    dices[i] = DiceRollerService.rollD6Dice();
                    diceView.setActivated(false);
                    diceView.setBackgroundResource(R.color.brown);
                }
            }
            setUpDices(dices, diceViews);
            player.setCurrentRollScore(gameDice1000Service.calculateScore(dices));
            currentScore.setText("Score: " + player.getCurrentRollScore());

        });

        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        dialog.getWindow().setDimAmount(0f);
        dialog.show();
    }


    private void setUpDices(int[] diceValues, ImageView[] dicesView) {
        for (int i = 0; i < dicesView.length; i++) {
            int diceValue = diceValues[i];
            ImageView imageView = dicesView[i];
            imageView.setImageResource(DiceRollerService.getDiceDrawable(diceValue));
        }
    }
    private void setRerollButtonVisibility(Button rerollBtn, ImageView[] diceViews) {
        Optional<ImageView> anyActivated = Arrays.stream(diceViews).filter(View::isActivated).findAny();
        rerollBtn.setVisibility(!anyActivated.isPresent() ? View.GONE : View.VISIBLE);
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

    private void clearData() {
        firstPlayerTotal.setText("Total 1");
        secondPlayerTotal.setText("Total 2");
        listAdapterFirstPlayer.notifyDataSetChanged();
        listAdapterSecondPlayer.notifyDataSetChanged();
    }

    private void swapPlayersUI(FloatingActionButton btnPlayerOne, FloatingActionButton btnPlayerTwo) {
        currentColor = currentColor == R.color.green ? R.color.blue : R.color.green;
        btnPlayerOne.setVisibility(btnPlayerOne.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        btnPlayerTwo.setVisibility(btnPlayerTwo.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }


}
