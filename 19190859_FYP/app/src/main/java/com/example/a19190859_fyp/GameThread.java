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

