package com.example.a19190859_fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GameOverActivity extends Activity {
    TextView gameOverText;
    TextView scoreText;
    TextView scoreTitle;
    Button answerQuestionsButton;
    private String score;
    private String inputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        gameOverText = (TextView) findViewById(R.id.gameOver);
        answerQuestionsButton = (Button) findViewById(R.id.startquestions);

        scoreTitle = (TextView) findViewById(R.id.scoreTitle);
        scoreText = (TextView) findViewById(R.id.scoreNumber);
        score = getIntent().getExtras().get("score").toString();
        scoreText.setText(score);

        inputs = getIntent().getExtras().get("inputs").toString();


        answerQuestionsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent questionnaireIntent = new Intent(getBaseContext(),QuestionnaireActivity.class);
                questionnaireIntent.putExtra("inputs", inputs);
                questionnaireIntent.putExtra("score", score);
                startActivity(questionnaireIntent);

            }
        });
    }

}
