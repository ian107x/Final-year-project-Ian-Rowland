package com.example.a19190859_fyp;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class gameThread extends Thread{

    characterView game;
    SurfaceHolder sHolder;
    Canvas gameCanvas;
    boolean threadRunning;

    public gameThread(characterView v, SurfaceHolder s)
    {
        super();
        this.game = v;
        this.sHolder = s;
    }

    @Override
    public void run(){
        while(threadRunning)
        {
            try
            {
                gameCanvas = this.sHolder.lockCanvas();
                synchronized (sHolder)
                {
                    this.game.updateView();
                    this.game.draw(gameCanvas);
                }

            } catch(Exception e){

            }
            finally {
                if(gameCanvas != null)
                {
                    try{
                        sHolder.unlockCanvasAndPost(gameCanvas);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        game.endGame();
    }

    public void setRunning(boolean running)
    {
        this.threadRunning = running;
    }




}
