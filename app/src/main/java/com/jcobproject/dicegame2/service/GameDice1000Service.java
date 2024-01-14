package com.jcobproject.dicegame2.service;

import com.jcobproject.dicegame2.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameDice1000Service {

    private final DiceRollerService diceRollerService = MainActivity.getDiceRollerService();

    private static boolean RULE_WINNING_SCORE_EQUALS_1000 = false;

    private static boolean RULE_SCORE_MIN_50 = false;
    private static boolean RULE_INFINITE_REROLLS = false;

    public static void setRuleWinningScoreEquals1000(boolean ruleWinningScoreEquals1000) {
        RULE_WINNING_SCORE_EQUALS_1000 = ruleWinningScoreEquals1000;
    }

    public static void setRuleScoreMin50(boolean ruleScoreMin50) {
        RULE_SCORE_MIN_50 = ruleScoreMin50;
    }

    public static void setRuleInfiniteRerolls(boolean ruleInfiniteRerolls) {
        RULE_INFINITE_REROLLS = ruleInfiniteRerolls;
    }


    public void rollTheDices(ArrayList<Integer> playerScores){
        int[] rolledDices = diceRollerService.roll5D6();
        int score = calculateScore(rolledDices);
        if(RULE_SCORE_MIN_50)
            if (score > 50)
                playerScores.add(score);
            else
                playerScores.add(0);
        else
            playerScores.add(score);
        

    }

    public boolean checkScoreForWin(int score){
        if(RULE_WINNING_SCORE_EQUALS_1000){
            return winningConditionEqual(score);
        }else {
            return winningConditionOver(score);
        }
    }

    private boolean winningConditionEqual(int score){
        return score == 1000;
    }

    private boolean winningConditionOver(int score){
        return score >= 1000;
    }

    public int calculateScore(int[] result) {
        int score = 0;
        ArrayList<Integer> count = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0));
        for(int diceRoll : result) {
            count.set(diceRoll, count.get(diceRoll) + 1);
        }
        System.out.println(count);
        if(count.contains(5)){
            return 1000;
        }
        int[] sortedResult = Arrays.stream(result).sorted().toArray();
        if( Arrays.equals(sortedResult, new int[]{1, 2, 3, 4, 5}) || Arrays.equals(sortedResult, new int[]{2, 3, 4, 5, 6})){
            return 150;
        }
        int value = count.contains(4) ? count.indexOf(4) : 0;
        if (value == 1) {
            score += 200;
        } else {
            score += value * 20;
        }
        value = count.contains(3) ? count.indexOf(3) : 0;
        if (value == 1) {
            score += 100;
        } else {
            score += value * 10;
        }

        if(count.get(1) == 2){
            score+=20;
        } else if( count.get(5) == 2 ) {
            score+=10;
        }

        if(count.get(1) == 1){
            score+=10;
        } else if( count.get(5) == 1) {
            score+=5;
        }
        return score;
    }
}
