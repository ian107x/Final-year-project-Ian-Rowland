package com.example.a19190859_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button startGameButton;
    Button viewGameRulesButton;
    Button questionnaireButton;
    Button viewDataButton;
    FileActions fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGameButton = (Button) findViewById(R.id.startgamebutton);
        viewGameRulesButton = (Button) findViewById(R.id.gameRulesButton);
        questionnaireButton = (Button) findViewById(R.id.questionnaireButton);
        viewDataButton = (Button) findViewById(R.id.viewDataButton);

        fa = new FileActions();

        startGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                fa.setBlankInputsFile(fa.createFile(fa.inputsFileName + fa.fileExtension));
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

        questionnaireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent questionnaireIntent = new Intent(getBaseContext(),QuestionnaireActivity.class);
                startActivity(questionnaireIntent);
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