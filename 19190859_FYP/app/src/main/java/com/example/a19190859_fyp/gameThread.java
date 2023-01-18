package com.example.a19190859_fyp;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class gameThread extends Thread{

    private characterView game;
    private SurfaceHolder sHolder;
    public static Canvas gameCanvas;
    boolean threadRunning;
    private int targetFPS = 30;
    private double averageFPS;

    public gameThread(characterView v, SurfaceHolder s)
    {
        super();
        this.game = v;
        this.sHolder = s;
    }


    @Override
    public void run() {

        while (threadRunning) {
            /*gameCanvas = null;
            {
                try {
                    gameCanvas = this.sHolder.lockCanvas();
                    synchronized (sHolder) {
                        this.game.updateView();
                        this.game.draw(gameCanvas);
                    }

                } catch (Exception e) {

                } finally {
                    if (gameCanvas != null) {
                        try {
                            sHolder.unlockCanvasAndPost(gameCanvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }*/
            if(sHolder.getSurface().isValid())
            {
                gameCanvas = sHolder.lockCanvas();
                game.updateView();
                game.draw(gameCanvas);
                sHolder.unlockCanvasAndPost(gameCanvas);
            }

        }
    }

        public void setRunning ( boolean running)
        {
            this.threadRunning = running;
        }


    }

