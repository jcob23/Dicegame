package com.jcobproject.dicegame2.service;

import com.jcobproject.dicegame2.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class DiceRollerService {

    private final static DiceRollerService diceRollerService = new DiceRollerService();

    private static final Map<Integer, Integer> mapD6 = new HashMap<>();

    static {
        mapD6.put(1,R.drawable.d1);
        mapD6.put(2,R.drawable.d2);
        mapD6.put(3,R.drawable.d3);
        mapD6.put(4,R.drawable.d4);
        mapD6.put(5,R.drawable.d5);
        mapD6.put(6,R.drawable.d6);
    }


    public static DiceRollerService getInstance(){
        return diceRollerService;
    }

    private DiceRollerService() {
    }

    public static int rollD6Dice() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    public int[] roll5D6() {
        return IntStream.range(0, 5).map(a -> rollD6Dice()).toArray();
    }

    public static int getDiceDrawable(int diceValue){
        return mapD6.get(diceValue);
    }


}
