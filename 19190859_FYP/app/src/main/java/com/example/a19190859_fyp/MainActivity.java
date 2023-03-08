package com.example.a19190859_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button startGameButton;
    Button viewGameRulesButton;
    Button viewDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGameButton = (Button) findViewById(R.id.startgamebutton);
        viewGameRulesButton = (Button) findViewById(R.id.gameRulesButton);
        viewDataButton = (Button) findViewById(R.id.viewDataButton);

        startGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startGameIntent = new Intent(getBaseContext(), SetDifficultyActivity.class);
                startActivity(startGameIntent);
            }
        });

        viewGameRulesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent gameRulesIntent = new Intent(getBaseContext(), GameRulesActivity.class);
                startActivity(gameRulesIntent);
            }
        });

        viewDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewDataIntent = new Intent(getBaseContext(),ViewDataActivity.class);
                startActivity(viewDataIntent);
            }
        });
    }
}