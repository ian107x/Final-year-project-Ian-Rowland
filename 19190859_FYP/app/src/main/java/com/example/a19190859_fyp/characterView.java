package com.example.a19190859_fyp;

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
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class characterView extends SurfaceView implements SurfaceHolder.Callback{

    //private int lifeNum;
    private playerSprite birdSprite;
    private boolean touch = false;
    private ArrayList<obstacle> enemies = new ArrayList<obstacle>();
    private double startTime;
    private double endTime;
    private Paint scoreBoard = new Paint();
    private Paint lifeCount = new Paint();
    enemyFactory ef = new enemyFactory();
    gameThread t;
    private infoDB db = new infoDB(getContext());
    private int minBirdY;
    private int maxBirdY;

    public characterView(Context context){
        super(context);
        getHolder().addCallback(this);
        t = new gameThread(this, getHolder());
        setFocusable(true);

        //quick note of recycle for bitmaps bird.recycle();

        minBirdY = getScreenHeight();
        maxBirdY = 0;
        scoreBoard.setColor(Color.RED);
        scoreBoard.setTextSize(50);
        scoreBoard.setTypeface(Typeface.DEFAULT);
        scoreBoard.setAntiAlias(true);

        lifeCount.setColor(Color.GREEN);
        lifeCount.setTextSize(50);
        lifeCount.setTypeface(Typeface.DEFAULT);
        lifeCount.setAntiAlias(true);

        startTime = System.currentTimeMillis();

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

    public boolean impactObstacle(obstacle e){
            if((birdSprite.xAxis < e.xAxis && e.xAxis < (birdSprite.xAxis + birdSprite.image.getWidth()))
                    && birdSprite.yAxis < e.yAxis
                    && e.yAxis < (birdSprite.yAxis + birdSprite.image.getHeight()))
            {
                return true;
            }
            return false;

    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }



    public boolean onTouchEvent(MotionEvent event){

        float x = event.getX();
        float y = event.getY();

        //define max jump height
        boolean perceivedControlTest;
        float prevInputTime = 0;
        float inputStart = 0;
        float inputend;
        float inputduration;
        float timeBetweenInputs = 0;
        float inputPressure = 0;

        int PCTRNG = (int)Math.floor(Math.random() *(10 - 1 + 1) + 0);

        if(PCTRNG == 3 || PCTRNG == 5 ||PCTRNG == 7 )
        {
            perceivedControlTest = true;
        }
        else
        {
            perceivedControlTest = false;
        }


        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {

            //birdSprite.maxJumpHeight = birdSprite.yAxis + birdSprite.image.getHeight();
            birdSprite.yAxis -= (birdSprite.birdVelocity * (birdSprite.image.getHeight() * 0.66));

            inputPressure = event.getPressure();
            inputStart = System.currentTimeMillis();;

            timeBetweenInputs = inputStart - prevInputTime;
            //send values to database


            if(perceivedControlTest)
            {
                touch = false;
            }
            else
            {
                touch = true;
                //birdSprite.maxJumpHeight = birdSprite.image.getHeight() * 5;
            }
            //check for perceived control

            //}
        }else if(event.getAction() == MotionEvent.ACTION_UP)
        {
            touch = false;
            inputend = System.currentTimeMillis();
            inputduration = inputend - inputStart;
            db.addInput(1, inputStart, inputduration, inputPressure, timeBetweenInputs);
            prevInputTime = inputend;
        }
        return super.onTouchEvent(event);
    }

    public void endGame(){
        endTime = System.currentTimeMillis();
        double totalTime = endTime - startTime;
        //Toast.makeText(getContext(), "Game Over" , Toast.LENGTH_SHORT).show();
        Intent gameOverIntent = new Intent(getContext(), gameOverActivity.class);
        gameOverIntent.putExtra("Score", birdSprite.gameScore);
        getContext().startActivity(gameOverIntent);
    }

    //theoretical method to create gameplay/level
    public void createLevel()
    {
        int birdWidth = getScreenWidth()/10;
        int birdHeight = birdWidth;
        birdSprite = new playerSprite(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.birdimage), birdWidth, birdHeight, false), 25, 25);
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
            if(enemies.get(i).xAxis < 0)
            {
                //recycle bitmap to free up memory
                enemies.set(i, terminateEnemy(enemies.get(i)));
                enemies.remove(i);
            }
        }

        //pre implementation logic for hitting the top or bottom of the screen

         if (birdSprite.yAxis > minBirdY)
         {
           birdSprite.yAxis = getScreenHeight()/3;
           birdSprite.life --;
           //lifeNum--;
         }
         if (birdSprite.yAxis < maxBirdY)
         {
           birdSprite.yAxis = getScreenHeight()/2;
           birdSprite.life --;
           //lifeNum--;
        }
         if(birdSprite.yAxis < birdSprite.maxJumpHeight)
         {
             birdSprite.maxJumpHeight = getScreenHeight();
         }

        /*if(lifeNum <=0) {
            endGame();
        }*/
        if(birdSprite.life <= 0)
        {
            endGame();
        }
        if(birdSprite.yAxis <= birdSprite.maxJumpHeight)
        {
            birdSprite.maxJumpHeight = getScreenHeight();
            //touch = false;
        }



    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        createLevel();
        t.setRunning(true);
        t.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

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
        birdSprite.moveSprite(touch);
        for(int i = 0; i < enemies.size(); i++)
        {
            enemies.get(i).moveSprite();
        }

    }

    public void generateEnemies()
    {
        obstacle enemy1;
        int enemyRNG = (int)Math.floor(Math.random() *(2 - 0 + 1) + 0);
        int yAxisRNG = (int)Math.floor(Math.random() *(getScreenHeight() - 0 + 1) + 0);
        int xAxisRNG = (int)Math.floor(Math.random() *(getScreenWidth() - (getScreenWidth()/2) + 1) + 0);
        if(enemyRNG == 0)
        {
            enemy1 = ef.createEnemy(this, "GREENENEMY", getScreenWidth(), yAxisRNG);

        }
        else if(enemyRNG == 1)
        {
            enemy1 = ef.createEnemy(this, "REDENEMY", getScreenWidth(), yAxisRNG);
        }
        else
        {
            enemy1 = ef.createEnemy(this, "BLUEENEMY", getScreenWidth(), yAxisRNG);
        }

        enemies.add(enemy1);
    }

    //terminateEnemy is used to recycle the bitmap associated with the enemy, and null the
    // enemy values so as to free up memory and remove enemy from the arraylist when it is no longer needed, and another enemy can be created
    public obstacle terminateEnemy(obstacle e)
    {
        e.image.recycle();
        //e.xAxis = getScreenWidth() + 300;
        return e;
    }
}
