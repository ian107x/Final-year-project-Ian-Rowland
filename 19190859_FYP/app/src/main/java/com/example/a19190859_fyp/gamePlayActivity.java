package com.example.a19190859_fyp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class gamePlayActivity extends Activity {
    private gameView birdView;
    private Handler gameHandler = new Handler();
    private final static long Interval = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        birdView = new gameView(this);
        setContentView(birdView);

        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {
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
