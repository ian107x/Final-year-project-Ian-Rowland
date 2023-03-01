package com.example.a19190859_fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewDataActivity extends Activity{

    TextView dataText;
    Button viewDataButton;
    Button exportDataButton;
    Button returnToMainButton;
    InfoDB db;
    EditText et;
    ArrayList<String> databaseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        dataText = (TextView) findViewById(R.id.datatext);
        viewDataButton = (Button) findViewById(R.id.viewdatabutton);
        exportDataButton = (Button) findViewById(R.id.exportdatabutton);
        returnToMainButton = (Button) findViewById(R.id.returntomain);
        et = (EditText) findViewById(R.id.edittext);
        db = new InfoDB(getApplicationContext());
        databaseList = db.getAll();


        viewDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dataText.setText(String.valueOf(compileDBFromList(databaseList)));
                print(databaseList);
                //dataText.setText(String.valueOf(compileDBFromList(db.getAnswersByID(Integer.parseInt(et.getText().toString())))));
            }
        });

        exportDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!et.getText().toString().contentEquals(""))
                {
                    dataText.setText(String.valueOf(compileDBFromList(db.getAnswersByID(Integer.parseInt(et.getText().toString())))));
                }
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
    public String compileDBFromList(String[] dbList)
    {
        String returnDB = "";
        for(int i = 0; i < dbList.length; i++)
        {
            returnDB += dbList[i];
        }
        return returnDB;
    }

    public void print(ArrayList<String> content)
    {
        String list = "";
        dataText.setText("");
        for(int i=0; i<content.size(); i++){
            dataText.append(content.get(i));
            list += content.get(i);
        }
        dataText.setText(list);
    }




}