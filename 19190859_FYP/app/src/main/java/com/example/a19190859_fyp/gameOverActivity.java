package com.example.a19190859_fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class gameOverActivity extends Activity {
    TextView gameOverText;
    Button restartGameButton;
    Button returnToMainButton;
    Button viewDataButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_portrait);

        gameOverText = (TextView) findViewById(R.id.gameOver);
        restartGameButton = (Button) findViewById(R.id.restartgame);
        returnToMainButton = (Button) findViewById(R.id.returntomain);
        viewDataButton = (Button) findViewById(R.id.viewdata);

        //Intent intent = getIntent();
        //String score = intent.getStringExtra(gamePlayActivity."Score");

        restartGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent restartGameIntent = new Intent(getBaseContext(),gamePlayActivity.class);
                startActivity(restartGameIntent);

            }
        });

        returnToMainButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent returnToMainIntent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(returnToMainIntent);

            }
        });

        viewDataButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent viewDataIntent = new Intent(getBaseContext(),viewDataActivity.class);
                startActivity(viewDataIntent);

            }
        });
    }

}
