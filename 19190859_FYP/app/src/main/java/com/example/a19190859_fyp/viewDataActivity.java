package com.example.a19190859_fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class viewDataActivity extends Activity{

    TextView dataText;
    Button viewDataButton;
    Button exportDataButton;
    Button returnToMainButton;
    infoDB infoDB;
    ArrayList<String> databaseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        dataText = (TextView) findViewById(R.id.datatext);
        viewDataButton = (Button) findViewById(R.id.viewdatabutton);
        exportDataButton = (Button) findViewById(R.id.exportdatabutton);
        returnToMainButton = (Button) findViewById(R.id.returntomain);
        infoDB = new infoDB(getApplicationContext());

        databaseList = infoDB.getAll();

        viewDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dataText.setText(String.valueOf(databaseList.length));
                print(databaseList);
            }
        });

        exportDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDB.exportDB();
                exportDataButton.setText("dingdong");
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
