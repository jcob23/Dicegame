package com.jcobproject.dicegame2.service;

import com.jcobproject.dicegame2.Entity.Player;

import java.util.ArrayList;
import java.util.Arrays;


public class GameDice1000Service {

    private final static GameDice1000Service gameDice1000Service = new GameDice1000Service();

    private final DiceRollerService diceRollerService = DiceRollerService.getInstance();
    private boolean RULE_WINNING_SCORE_EQUALS_1000 = false;
    private boolean RULE_SCORE_MIN_50;
    private boolean RULE_INFINITE_REROLLS = false;
    private boolean JOKER_DICE = false;

    private GameDice1000Service() {
    }

    public static GameDice1000Service getInstance() {
        return gameDice1000Service;
    }




    public void addResultToPlayerScore(ArrayList<Integer> playerScores, int score){
        if (RULE_SCORE_MIN_50) {
            if (score > 50) {
                playerScores.add(score);
            } else {
                playerScores.add(0);
            }
        } else {
            playerScores.add(score);
        }
    }

    public boolean checkScoreForWin(Player player) {
        int total = calculateTotal(player.getScores());
        player.setTotalPoints(total);
        if (RULE_WINNING_SCORE_EQUALS_1000) {
            return winningConditionEqual(total);
        } else {
            return winningConditionOver(total);
        }

    }
    private int calculateTotal(ArrayList<Integer> playerResults) {
        return playerResults.stream().reduce(Integer::sum).orElse(0);
    }

    private boolean winningConditionEqual(int score) {
        return score == 1000;
    }

    private boolean winningConditionOver(int score) {
        return score >= 1000;
    }

    public int calculateScore(int[] result) {
        int score = 0;
        ArrayList<Integer> count = new ArrayList<>();
        for (int i = 0; i < result.length + 2; i++) {
            count.add(0);
        }
        for (int diceRoll : result) {
            count.set(diceRoll, count.get(diceRoll) + 1);
        }
        System.out.println(count);
        if (count.contains(5)) {
            return 1000;
        }
        int[] sortedResult = Arrays.stream(result).sorted().toArray();
        if (Arrays.equals(sortedResult, new int[]{1, 2, 3, 4, 5}) || Arrays.equals(sortedResult, new int[]{2, 3, 4, 5, 6})) {
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

        if (count.get(1) == 2) {
            score += 20;
        } else if (count.get(5) == 2) {
            score += 10;
        }

        if (count.get(1) == 1) {
            score += 10;
        } else if (count.get(5) == 1) {
            score += 5;
        }
        return score;
    }



    public boolean isRuleWinningScoreEquals1000() {
        return RULE_WINNING_SCORE_EQUALS_1000;
    }

    public void setRuleWinningScoreEquals1000(boolean ruleWinningScoreEquals1000) {
        RULE_WINNING_SCORE_EQUALS_1000 = ruleWinningScoreEquals1000;
    }

    public boolean isRuleScoreMin50() {
        return RULE_SCORE_MIN_50;
    }

    public void setRuleScoreMin50(boolean ruleScoreMin50) {
        RULE_SCORE_MIN_50 = ruleScoreMin50;
    }

    public boolean isRuleInfiniteRerolls() {
        return RULE_INFINITE_REROLLS;
    }

    public void setRuleInfiniteRerolls(boolean ruleInfiniteRerolls) {
        RULE_INFINITE_REROLLS = ruleInfiniteRerolls;
    }

    public boolean isJokerDice() {
        return JOKER_DICE;
    }

    public void setJokerDice(boolean jokerDice) {
        JOKER_DICE = jokerDice;
    }
}
