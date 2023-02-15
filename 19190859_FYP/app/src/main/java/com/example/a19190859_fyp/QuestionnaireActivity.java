package com.example.a19190859_fyp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class QuestionnaireActivity extends AppCompatActivity {
    Button setQ1Button;
    Button setQ2Button;
    Button setQ3Button;
    Button setInfoButton;
    Button returnToMainButton;
    Spinner q1;
    Spinner q2;
    Spinner q3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        setQ1Button = (Button)findViewById(R.id.q1button);
        setQ2Button= (Button)findViewById(R.id.q2button);
        setQ3Button= (Button)findViewById(R.id.q3button);
        setInfoButton= (Button)findViewById(R.id.setinfobutton);
        returnToMainButton= (Button)findViewById(R.id.returntomainbutton);

        q1= (Spinner) findViewById(R.id.q1list);
        q2= (Spinner) findViewById(R.id.q2list);
        q3= (Spinner) findViewById(R.id.q3list);

        setQ1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer1 = q1.getSelectedItem().toString();
            }
        });

        setQ2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer2 = q2.getSelectedItem().toString();
            }
        });
        setQ3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer1 = q2.getSelectedItem().toString();

            }
        });
        setInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer1 = q1.getSelectedItem().toString();
                String answer2 = q2.getSelectedItem().toString();
                String answer3 = q3.getSelectedItem().toString();
                exportAnswers(answer1, answer2, answer3);

            }
        });
        returnToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnToMainIntent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(returnToMainIntent);
            }
        });

    }
    public void exportAnswers(String ans1, String ans2, String ans3)
    {
        String data = "";
        String title = "Was game enjoyed, How Much Control, Control gained or lost over time\n";
        data += title;
        String compiledAnswers = ans1 + ", " + ans2 + ", " + ans3;
        data += compiledAnswers;

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        try
        {
            File bfile = new File(dir, "answers.txt");
            FileWriter myWriter = new FileWriter(bfile);
            myWriter.write(data);

            myWriter.close();
        }
        catch (Exception exception){

        }
    }

}
