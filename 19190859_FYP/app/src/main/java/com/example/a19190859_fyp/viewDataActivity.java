package com.example.a19190859_fyp;

import android.app.Activity;
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
    infoDB infoDB;
    List<String> databaseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        dataText = (TextView) findViewById(R.id.datatext);
        viewDataButton = (Button) findViewById(R.id.viewdatabutton);
        exportDataButton = (Button) findViewById(R.id.exportdatabutton);
        infoDB = new infoDB(getApplicationContext());

        databaseList = new ArrayList<String>();
        databaseList = Arrays.asList(infoDB.getAll());

        viewDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataText.setText((CharSequence) databaseList);
            }
        });

        exportDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDB.exportDB();
            }
        });

    }

}
