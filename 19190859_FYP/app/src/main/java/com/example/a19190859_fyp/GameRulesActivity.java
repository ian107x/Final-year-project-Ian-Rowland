package com.example.a19190859_fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameRulesActivity extends AppCompatActivity {

    Button returnToMainButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rules);

        returnToMainButton = (Button) findViewById(R.id.returntomainButton);


        returnToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnToMainIntent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(returnToMainIntent);
            }
        });

    }


}
