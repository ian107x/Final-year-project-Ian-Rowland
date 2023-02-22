package com.example.a19190859_fyp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
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
    FileActions fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        returnToMainButton= (Button)findViewById(R.id.returntomainbutton);
        fa = new FileActions();

        q1= (Spinner) findViewById(R.id.q1list);
        q2= (Spinner) findViewById(R.id.q2list);
        q3= (Spinner) findViewById(R.id.q3list);

        returnToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String answer1 = q1.getSelectedItem().toString();
                String answer2 = q2.getSelectedItem().toString();
                String answer3 = q3.getSelectedItem().toString();
                //exportAnswers(answer1, answer2, answer3);

                File answersFile = fa.createFile(fa.answersFileName);
                String answersData = compileAnswers(answer1, answer2, answer3);
                fa.writeToFile(answersFile, answersData);

                String message = "Answers submitted. Returning to main menu";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                Intent returnToMainIntent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(returnToMainIntent);
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
