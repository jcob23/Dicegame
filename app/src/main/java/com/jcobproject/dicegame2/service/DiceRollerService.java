package com.jcobproject.dicegame2.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class DiceRollerService {

    private int rollD6Dice() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    public int[] roll5D6() {
        return IntStream.range(0, 5).map(a -> rollD6Dice()).toArray();
    }


}
