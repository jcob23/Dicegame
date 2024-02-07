package com.jcobproject.dicegame2.Entity;

import java.util.ArrayList;

public class Player {

    private String name;
    private int wins;
    private int games;
    private int totalPoints = 0;
    private int color;

    private int currentRollScore;

    private ArrayList<Integer> scores = new ArrayList<>();

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public int getCurrentRollScore() {
        return currentRollScore;
    }

    public void setCurrentRollScore(int currentRollScore) {
        this.currentRollScore = currentRollScore;
    }

    public void summarizeGame(boolean isWon){
        if(isWon){
            wins +=1;
        }
        games +=1;
        scores.clear();
        totalPoints = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
