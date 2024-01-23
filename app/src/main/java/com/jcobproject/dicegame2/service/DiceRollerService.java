package com.jcobproject.dicegame2.service;

import java.util.Random;
import java.util.stream.IntStream;

public class DiceRollerService {

    private final static DiceRollerService diceRollerService = new DiceRollerService();

    public static DiceRollerService getInstance(){
        return diceRollerService;
    }

    private DiceRollerService() {
    }

    private int rollD6Dice() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    public int[] roll5D6() {
        return IntStream.range(0, 5).map(a -> rollD6Dice()).toArray();
    }


}
