package com.example.a19190859_fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SetDifficultyActivity extends AppCompatActivity {

    Button easyGameButton;
    Button normalGameButton;
    Button hardGameButton;
    TextView introductionText;
    String difficulty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_difficulty);

        easyGameButton = (Button) findViewById(R.id.easy);
        normalGameButton = (Button)findViewById(R.id.normal);
        hardGameButton = (Button) findViewById(R.id.hard);
        introductionText = (TextView) findViewById(R.id.introductiontext);

        easyGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty = "easy";
                setDifficulty(difficulty);

            }
        });

        normalGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty = "normal";
                setDifficulty(difficulty);
            }
        });

        hardGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty = "hard";
                setDifficulty(difficulty);
            }
        });

    }

    public void setDifficulty(String x)
    {
        //conditional to ensure that invalid strings can never be sent to gameplay activity
        if(x != "easy" && x != "normal"  && x != "hard")
        {
            x = "normal";
        }
        Intent startGameIntent = new Intent(getBaseContext(), gamePlayActivity.class);
        startGameIntent.putExtra("difficulty", x);
        startActivity(startGameIntent);
    }

}