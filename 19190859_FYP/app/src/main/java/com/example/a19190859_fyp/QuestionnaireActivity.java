package com.example.a19190859_fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class QuestionnaireActivity extends AppCompatActivity {

    Button returnToMainButton;
    Spinner q1;
    Spinner q2;
    Spinner q3;
    Spinner q4;
    Spinner q5;
    EditText q6;
    Spinner q7;
    FileActions fa;
    InfoDB db;
    private String inputs;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        returnToMainButton= (Button)findViewById(R.id.returntomainbutton);
        fa = new FileActions();
        db = new InfoDB(getApplicationContext());

        q1= (Spinner) findViewById(R.id.q1list);
        q2= (Spinner) findViewById(R.id.q2list);
        q3= (Spinner) findViewById(R.id.q3list);
        q4 = (Spinner) findViewById(R.id.q4list);
        q5 = (Spinner) findViewById(R.id.q5list);
        q6 = (EditText) findViewById(R.id.q6text);
        q7 = (Spinner) findViewById(R.id.q7list);

        inputs = getIntent().getExtras().get("inputs").toString();
        score = getIntent().getExtras().get("score").toString();


        //submit answers to questionnaire, and return to main activity
        returnToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!q6.getText().toString().contentEquals("")) {
                    //pull answers from drop down lists
                    String answer1 = q1.getSelectedItem().toString();
                    String answer2 = q2.getSelectedItem().toString();
                    String answer3 = q3.getSelectedItem().toString();
                    String answer4 = q4.getSelectedItem().toString();
                    String answer5 = q5.getSelectedItem().toString();
                    String answer6 = q6.getText().toString();
                    String answer7 = q7.getSelectedItem().toString();

                    db.addAnswers(answer1, answer2, answer3, answer4, answer5, answer6, answer7, score);

                    File inputsFile = fa.createFile(fa.inputsFileName + (db.getAll().size() - 1) + fa.fileExtension);
                    fa.writeToFile(inputsFile, inputs);

                    String message = "Answers submitted. Returning to main menu. You're user id is: " +  (db.getAll().size() - 1);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    //return to main menu when questionnaire is submitted
                    Intent returnToMainIntent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(returnToMainIntent);
                }else
                {
                    String blankmsg = "please provide age";
                    Toast.makeText(getApplicationContext(), blankmsg, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
