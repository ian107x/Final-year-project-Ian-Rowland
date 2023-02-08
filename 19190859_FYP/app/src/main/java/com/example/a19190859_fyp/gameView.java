package com.example.a19190859_fyp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import java.util.ArrayList;

public class gameView extends SurfaceView implements SurfaceHolder.Callback{

    private playerSprite birdSprite;
    private boolean touch = false;
    private ArrayList<obstacle> enemies = new ArrayList<obstacle>();
    private ArrayList<PerceivedControlInfo> pctList = new ArrayList<>();
    //private double startTime;
    //private double endTime;
    private Paint scoreBoard = new Paint();
    private Paint lifeCount = new Paint();
    private enemyFactory ef = new enemyFactory();
    private gameThread t;
    boolean perceivedControlTest;
    private infoDB db = new infoDB(getContext());
    private final int bottomOfScreen;
    private final int topOfScreen;
    float prevInputTime = 0;
    float inputStart;
    float inputend;
    float inputduration;
    float timeBetweenInputs;
    float inputPressure;
    boolean nextInputTested;

    public gameView(Context context){
        super(context);
        getHolder().addCallback(this);
        t = new gameThread(this, getHolder());
        setFocusable(true);

        //quick note of recycle for bitmaps bird.recycle();

        bottomOfScreen = getScreenHeight();
        topOfScreen = 0;
        scoreBoard.setColor(Color.RED);
        scoreBoard.setTextSize(70);
        scoreBoard.setTypeface(Typeface.DEFAULT);
        scoreBoard.setAntiAlias(true);

        lifeCount.setColor(Color.GREEN);
        lifeCount.setTextSize(70);
        lifeCount.setTypeface(Typeface.DEFAULT);
        lifeCount.setAntiAlias(true);

        //startTime = System.currentTimeMillis();

    }

    public void draw(Canvas canvas) {

        super.draw(canvas);
        if(canvas!=null)
        {
            canvas.drawRGB(0, 100, 110);
            //canvas.drawColor(Color.BLUE);
            canvas.drawText("Score: " + birdSprite.gameScore, 20, 60, scoreBoard);
            canvas.drawText("Life: " + birdSprite.life, 20, 120, lifeCount);
            birdSprite.draw(canvas);


            for(int i = 0; i < enemies.size(); i++)
            {
                enemies.get(i).draw(canvas);
                //alt way to draw bitmap of sprite class
                // canvas.drawBitmap(enemies.get(i).image, enemies.get(i).xAxis, enemies.get(i).yAxis, null);
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        if(birdSprite.life <= 0)
        {
            t.setRunning(false);
            setAlpha(0);
            endGame();
        }
    }

    /*public boolean impactEnemyCheck(int x, int y)
    {
        for(int i = 0; i <= enemies.size(); i++) {
            if (birdXaxis < x && x < (birdXaxis + birdSprite.image.getWidth()) && birdYaxis < y && y < (birdYaxis + birdSprite.image.getHeight())) {
                return true;
            }//else{
            return false;
            //}
        }return true;
    }*/

    public boolean impactObstacle(obstacle e)
    {
            if((birdSprite.xAxis < e.xAxis
                    && e.xAxis < (birdSprite.xAxis + birdSprite.image.getWidth()))
                    && birdSprite.yAxis < e.yAxis
                    && e.yAxis < (birdSprite.yAxis + birdSprite.image.getHeight()))
            {
                return true;
            }
            return false;

    }

    public static int getScreenWidth()
    {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight()
    {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }



    public boolean onTouchEvent(MotionEvent event)
    {

        float x = event.getX();
        float y = event.getY();
        boolean thisInputTested = perceivedControlTest;

        //define max jump height

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {

            int PCTRNG = (int)Math.floor(Math.random() *(10 - 1 + 1) + 0);

            /*if(thisInputTested)
            {

            }
            else
            {*/
                if(PCTRNG == 3 || PCTRNG == 5)
                {
                    perceivedControlTest = true;
                    nextInputTested = true;

                }
                else
                {
                    perceivedControlTest = false;
                    nextInputTested = false;
                }
            //}

            inputPressure = event.getPressure();
            inputStart = System.currentTimeMillis();;

            //value for inputend at this point is the end time for the previous input
            timeBetweenInputs = inputStart - inputend;
            //send values to database

            if(perceivedControlTest)
            {
                touch = false;
            }
            else
            {
                touch = true;
                birdSprite.setJumpPeak();
                //birdSprite.yAxis -= (birdSprite.birdVelocity * birdSprite.image.getHeight());
                //birdSprite.maxJumpHeight = birdSprite.yAxis + (birdSprite.image.getHeight() * 50);
            }

        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
        {
            touch = false;
            //set new inputend value
            inputend = System.currentTimeMillis();
            inputduration = (inputend - inputStart)/1000;
            //db.addInput(perceivedControlTest, inputStart, inputduration, inputPressure, timeBetweenInputs);
            PerceivedControlInfo pct = new PerceivedControlInfo(thisInputTested, inputPressure, inputduration, timeBetweenInputs, inputStart);
            pctList.add(pct);
            prevInputTime = inputend;
        }
        return super.onTouchEvent(event);
    }



    public void endGame()
    {
        /*Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < pctList.size(); i++)
                {
                    PerceivedControlInfo pctInstance = pctList.get(i);
                    db.addInput(pctInstance.tested, pctInstance.inputTime, pctInstance.inputDuration, pctInstance.inputPressure, pctInstance.timeBetweenInputs);
                }
                Intent gameOverIntent = new Intent(getContext(), gameOverActivity.class);
                getContext().startActivity(gameOverIntent);
            }
        });*/
        for(int i = 0; i < pctList.size(); i++)
        {
            PerceivedControlInfo pctInstance = pctList.get(i);
            db.addInput(pctInstance.tested, pctInstance.inputTime, pctInstance.inputDuration, pctInstance.inputPressure, pctInstance.timeBetweenInputs);
        }

        Intent gameOverIntent = new Intent(getContext(), gameOverActivity.class);
        //gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        gameOverIntent.putExtra("score", birdSprite.gameScore);
        ((Activity) getContext()).finish();
        getContext().startActivity(gameOverIntent);


    }

    //theoretical method to create gameplay/level
    public void createLevel()
    {
        int birdWidth = getScreenWidth()/10;
        int birdHeight = getScreenHeight()/15;
        birdSprite = new playerSprite(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.birdimage), birdWidth, birdHeight, false), 25, 25);
        birdSprite.maxJumpHeight = getScreenHeight();
        for(int i = 0; i < 5; i++)
        {
            generateEnemies();
        }

    }

    //alternative implementation for game logic - prototyping and experimentation phase
    public void gameLogic()
    {
        if(enemies.size() < 5)
        {
            generateEnemies();
        }
        for(int i = 0; i < enemies.size(); i++)
        {
            if(impactObstacle(enemies.get(i)))
            {
                //lifeNum--;
                enemies.get(i).interact(birdSprite);
                enemies.get(i).xAxis = -200;
            }
            if(enemies.get(i).xAxis < -200)
            {
                //recycle bitmap to free up memory
                //enemies.set(i, terminateEnemy(enemies.get(i)));
                //enemies.remove(i);

                terminate(enemies.get(i));
            }
        }

        //pre implementation logic for hitting the top or bottom of the screen

         if (birdSprite.yAxis > bottomOfScreen)
         {
           birdSprite.yAxis = getScreenHeight()/3;
           birdSprite.life--;
           //lifeNum--;
         }

         if (birdSprite.yAxis < topOfScreen)
         {
           birdSprite.yAxis = getScreenHeight()/2;
           birdSprite.life--;
           //lifeNum--;
         }

        /*if(lifeNum <=0) {
            endGame();
        }*/

        /*if(birdSprite.life <= 0)
        {
            endGame();
        }*/
        if(birdSprite.yAxis <= birdSprite.maxJumpHeight)
        {
            birdSprite.maxJumpHeight = bottomOfScreen;
            //touch = false;
        }



    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder)
    {

        createLevel();
        t.setRunning(true);
        t.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder)
    {
        t.setRunning(false);
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void updateView()
    {
        gameLogic();
        birdSprite.moveSprite();
        for(int i = 0; i < enemies.size(); i++)
        {
            enemies.get(i).moveSprite();
        }

    }

    public void generateEnemies()
    {
        obstacle enemy1;
        String enemyType;
        //create randomised ints that allow for random generation of various enemy types
        int enemyRNG = (int)Math.floor(Math.random() *(2 - 0 + 1) + 0);

        //create randomised ints that allow for random placement of obstacle objects on the y axis
        int yAxisRNG = (int)Math.floor(Math.random() *(getScreenHeight() - 0 + 1) + 0);
        int xAxisRNG = (int)Math.floor(Math.random() *(getScreenWidth() - (getScreenWidth()/2) + 1) + 0);
        if(enemyRNG == 0)
        {
            enemyType = "GREENENEMY";

        }
        else if(enemyRNG == 1)
        {
            enemyType = "REDENEMY";
        }
        else
        {
            enemyType = "BLUEENEMY";
        }

        enemy1 = ef.createEnemy(this, enemyType, getScreenWidth(), yAxisRNG);

        enemies.add(enemy1);
    }

    //terminateEnemy is used to recycle the bitmap associated with the enemy, and null the
    // enemy values so as to free up memory and remove enemy from the arraylist when it is no longer needed, and another enemy can be created
    public obstacle terminateEnemy(obstacle e)
    {
        e.image = null;
        e.image.recycle();
        //enemies.remove(e);
        //e.xAxis = getScreenWidth() + 300;
        return e;
    }

    public void terminate(obstacle e)
    {
        e.image.recycle();
        enemies.remove(e);
    }
}
