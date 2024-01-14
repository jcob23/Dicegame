package com.jcobproject.dicegame2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jcobproject.dicegame2.service.DiceRollerService;
import com.jcobproject.dicegame2.service.TotalCalculatorService;
import com.jcobproject.dicegame2.viewElements.Fragment1000Game;

public class MainActivity extends AppCompatActivity {

    private final static DiceRollerService diceRollerService = new DiceRollerService();

    private final static TotalCalculatorService totalCalculatorService = new TotalCalculatorService();

    public static DiceRollerService getDiceRollerService() {
        return diceRollerService;
    }

    public static TotalCalculatorService getTotalCalculatorService() {
        return totalCalculatorService;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragment1000Game())
                    .commit();

        }

    }
}