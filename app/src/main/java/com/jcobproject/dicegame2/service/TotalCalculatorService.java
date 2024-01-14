package com.jcobproject.dicegame2.service;

import java.util.ArrayList;

public class TotalCalculatorService {


    public int calculateTotal(ArrayList<Integer> playerResults){
        return playerResults.stream().reduce(Integer::sum).orElse(0);
    }


}
