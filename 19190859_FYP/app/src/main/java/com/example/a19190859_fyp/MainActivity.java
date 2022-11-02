package com.example.a19190859_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button startGameButton;
    Button startViewDataButton;
    TextView introductionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGameButton = (Button) findViewById(R.id.startgamebutton);
        startViewDataButton = (Button) findViewById(R.id.viewdatabutton);
        introductionText = (TextView) findViewById(R.id.introductiontext);

        startGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startGameIntent = new Intent(getBaseContext(), gamePlayActivity.class);
                startActivity(startGameIntent);
            }
        });

        startViewDataButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent viewDataIntent = new Intent(getBaseContext(), viewDataActivity.class);
                startActivity(viewDataIntent);
            }
        });
    }
}