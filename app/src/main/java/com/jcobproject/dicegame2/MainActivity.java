package com.jcobproject.dicegame2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import com.jcobproject.dicegame2.service.GameDice1000Service;
import com.jcobproject.dicegame2.viewElements.Fragment1000Game;

public class MainActivity extends AppCompatActivity {

    public GameDice1000Service gameDice1000Service = GameDice1000Service.getInstance();
    private CardView rulesCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rulesCard = findViewById(R.id.rulesCard);
        SwitchCompat switch1 = findViewById(R.id.switch1);
        SwitchCompat switch2 = findViewById(R.id.switch2);
        SwitchCompat switch3 = findViewById(R.id.switch3);
        SwitchCompat switch4 = findViewById(R.id.switch4);

        switch1.setOnCheckedChangeListener((buttonView, isChecked) ->
                gameDice1000Service.setRuleScoreMin50(isChecked)
        );

        switch2.setOnCheckedChangeListener((ButtonView, isChecked) ->
                gameDice1000Service.setRuleWinningScoreEquals1000(isChecked)
        );
        switch3.setOnCheckedChangeListener((ButtonView, isChecked) ->
                gameDice1000Service.setRuleInfiniteRerolls(isChecked)
        );
        switch4.setOnCheckedChangeListener((ButtonView, isChecked) ->
                gameDice1000Service.setJokerDice(isChecked)
        );

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragment1000Game())
                    .commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            rulesCard.setVisibility(rulesCard.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}