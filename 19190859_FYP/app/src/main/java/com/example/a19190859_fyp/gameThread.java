package com.example.a19190859_fyp;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class gameThread extends Thread{

    private gameView game;
    private SurfaceHolder sHolder;
    public static Canvas gameCanvas;
    boolean threadRunning;

    public gameThread(gameView v, SurfaceHolder s)
    {
        super();
        this.game = v;
        this.sHolder = s;
    }


    @Override
    public void run() {

        while (threadRunning) {
            if(sHolder.getSurface().isValid())
            {
                gameCanvas = sHolder.lockCanvas();
                game.updateView();
                game.draw(gameCanvas);
                sHolder.unlockCanvasAndPost(gameCanvas);
            }

        }
    }

        public void setRunning (boolean running)
        {
            this.threadRunning = running;
        }


    }

