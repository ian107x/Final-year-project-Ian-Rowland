package com.example.a19190859_fyp;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread{

    private GameView game;
    private SurfaceHolder sHolder;
    public static Canvas gameCanvas;
    boolean threadRunning;

    public GameThread(GameView v, SurfaceHolder s)
    {
        super();
        this.game = v;
        this.sHolder = s;
    }


    //method to start execution of thread operations
    @Override
    public void run() {

        while (threadRunning) {
            //check if surfaceHolder is valid before doing any operations
            if(sHolder.getSurface().isValid())
            {
                gameCanvas = sHolder.lockCanvas();
                game.updateView();
                game.draw(gameCanvas);
                sHolder.unlockCanvasAndPost(gameCanvas);
            }

        }
    }

    //set running status of thread in order to start or stop it
        public void setRunning (boolean running)
        {
            this.threadRunning = running;
        }


    }

