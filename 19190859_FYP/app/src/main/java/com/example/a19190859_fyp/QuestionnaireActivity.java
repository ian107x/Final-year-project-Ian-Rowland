package com.example.a19190859_fyp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class QuestionnaireActivity extends AppCompatActivity {

    Button returnToMainButton;
    Spinner q1;
    Spinner q2;
    Spinner q3;
    Spinner q4;
    Spinner q5;
    EditText q6;
    FileActions fa;
    InfoDB db;
    private String inputs;

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

        inputs = getIntent().getExtras().get("inputs").toString();


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


                    //create file and write answers to it
                    File answersFile = fa.createFile(fa.answersFileName + fa.fileExtension);
                    String answersData = compileAnswers(answer1, answer2, answer3);
                    fa.writeToFile(answersFile, answersData);

                    db.addAnswers(answer1, answer2, answer3, answer4, answer5, answer6);

                    //File inputsFile = fa.createFile(fa.inputsFileName + (db.getAll().size() - 1) + fa.fileExtension);
                    //fa.writeToFile(inputsFile, inputs);

                    String message = "Answers submitted. Returning to main menu";
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
    public String compileAnswers(String ans1, String ans2, String ans3)
    {
        String data = "";
        String title = "Was game enjoyed, How Much Control, Control gained or lost over time\n";
        data += title;
        String compiledAnswers = ans1 + ", " + ans2 + ", " + ans3;
        data += compiledAnswers;
        return data;
    }

}
