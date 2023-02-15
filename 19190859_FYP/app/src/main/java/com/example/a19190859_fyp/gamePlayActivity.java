package com.example.a19190859_fyp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class gamePlayActivity extends Activity {
    private gameView birdView;
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        difficulty = getIntent().getExtras().get("difficulty").toString();
        birdView = new gameView(this, difficulty);
        setContentView(birdView);

    }
}
