package com.example.a19190859_fyp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class gamePlayActivity extends Activity {
    private characterView birdView;
    private Handler gameHandler = new Handler();
    private final static long Interval = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        birdView = new characterView(this);
        setContentView(birdView);

        //Timer timer = new Timer();
        /*timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        birdView.invalidate();
                    }
                });
            }
        }, 0 , Interval);*/
    }
}
