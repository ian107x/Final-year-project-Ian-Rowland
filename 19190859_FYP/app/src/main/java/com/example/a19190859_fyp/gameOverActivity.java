package com.example.a19190859_fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class gameOverActivity extends Activity {
    TextView gameOverText;
    TextView scoreText;
    TextView scoreTitle;
    Button restartGameButton;
    Button answerQuestionsButton;

    private String score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        gameOverText = (TextView) findViewById(R.id.gameOver);
        restartGameButton = (Button) findViewById(R.id.restartgame);
        answerQuestionsButton = (Button) findViewById(R.id.startquestions);

        scoreTitle = (TextView) findViewById(R.id.scoreTitle);
        scoreText = (TextView) findViewById(R.id.scoreNumber);
        score = getIntent().getExtras().get("score").toString();
        scoreText.setText(score);

        restartGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent restartGameIntent = new Intent(getBaseContext(),SetDifficultyActivity.class);
                startActivity(restartGameIntent);

            }
        });

        answerQuestionsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent questionnaireIntent = new Intent(getBaseContext(),QuestionnaireActivity.class);
                startActivity(questionnaireIntent);

            }
        });
    }

}
