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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class gameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    private playerSprite birdSprite;
    private ArrayList<obstacle> enemies = new ArrayList<obstacle>();
    private ArrayList<PerceivedControlInfo> pctList = new ArrayList<>();
    private Paint scoreBoard = new Paint();
    private Paint lifeCount = new Paint();
    private enemyFactory ef = new enemyFactory();
    private gameThread t;
    boolean perceivedControlTest;
    private infoDB db = new infoDB(getContext());
    private final int bottomOfScreen;
    private final int topOfScreen;
    private int maxEnemies;
    double prevInputTime = 0;
    double inputStart;
    double inputend;
    double inputduration;
    double timeBetweenInputs;
    double inputPressure;
    boolean nextInputTested;
    int startTime;
    String diff;
    double previousAccel;
    double accelDelta;
    double inputAccel;
    FileActions fa;

    public gameView(Context context, String difficulty)
    {
        super(context);
        getHolder().addCallback(this);
        t = new gameThread(this, getHolder());
        setFocusable(true);
        bottomOfScreen = getScreenHeight();
        topOfScreen = 0;
        if(Objects.equals(difficulty, "easy"))
        {
            maxEnemies = 3;
        }
        else if(Objects.equals(difficulty, "hard"))
        {
            maxEnemies = 7;
        }
        else
        {
            maxEnemies = 5;
        }
        diff = difficulty;
        fa = new FileActions();

        startTime = (int) System.currentTimeMillis();

        scoreBoard.setColor(Color.RED);
        scoreBoard.setTextSize(70);
        scoreBoard.setTypeface(Typeface.DEFAULT);
        scoreBoard.setAntiAlias(true);

        lifeCount.setColor(Color.GREEN);
        lifeCount.setTextSize(70);
        lifeCount.setTypeface(Typeface.DEFAULT);
        lifeCount.setAntiAlias(true);
    }

    public void draw(Canvas canvas) {

        super.draw(canvas);
        if(canvas!=null)
        {
            canvas.drawRGB(0, 100, 110);
            canvas.drawText("Score:  " + birdSprite.gameScore, 20, 60, scoreBoard);
            canvas.drawText("Life: " + birdSprite.life, 20, 120, lifeCount);
            birdSprite.draw(canvas);
            for(int i = 0; i < enemies.size(); i++)
            {
                enemies.get(i).draw(canvas);
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


        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            inputAccel = accelDelta;

            int PCTRNG = (int)Math.floor(Math.random() *(10 - 1 + 1) + 0);

                if(PCTRNG <=3)
                {
                    perceivedControlTest = true;
                    nextInputTested = true;

                }
                else
                {
                    perceivedControlTest = false;
                    nextInputTested = false;
                }

            inputPressure = event.getPressure();
            inputStart = ((int)System.currentTimeMillis())/1000.0;

            //value for inputend at this point is the end time for the previous input
            timeBetweenInputs = inputStart - inputend;
            //send values to database

            if(perceivedControlTest)
            {

            }
            else
            {
                birdSprite.setJumpPeak();
            }
            //return true;

        }
        //set up values to be recorded to object list upon the users finger being lifted
        else if((event.getAction() == MotionEvent.ACTION_UP) || event.getAction() == MotionEvent.ACTION_CANCEL)
        {
            //set new inputend value
            inputend = ((int)System.currentTimeMillis())/1000.0;
            inputduration = (inputend - inputStart);
            PerceivedControlInfo pct = new PerceivedControlInfo(thisInputTested, inputPressure, inputduration, timeBetweenInputs, inputStart, inputAccel);
            pctList.add(pct);
            //db.addInput(thisInputTested, inputStart,inputduration,inputPressure, timeBetweenInputs);
            prevInputTime = inputend;
        }
        return true;
        //return super.onTouchEvent(event);
    }

    public void endGame()
    {
        String fileData = "";
        try
        {
            //create new file to write input data to
            File inputs = fa.createFile(fa.inputsFileName);
            FileWriter myWriter = new FileWriter(inputs);

            if(fa.checkForEmptyFile(inputs))
            {
                fileData += "PCT" + ", " + "input time" + ", " + "input duration" + ", " + "input pressure" + ", " + " time since previous input" + "Accelerometer\n";
            }
            else
            {
                fileData += fa.compileFileIntoString(inputs);

            }
            for(int i = 0; i < pctList.size(); i++)
            {
                PerceivedControlInfo pctInstance = pctList.get(i);
                fileData += pctInstance.tested + ", " + pctInstance.inputTime + ", " + pctInstance.inputDuration + ", " +
                        pctInstance.inputPressure + ", " + pctInstance.timeBetweenInputs + ", "+ pctInstance.acceleration + "\n";
            }

            myWriter.write(fileData);
           /* myWriter.write("PCT" + ", " + "input time" + ", " + "input duration" + ", " + "input pressure" + ", " + " time since previous input" + "Accelerometer\n");
            for (int i = 0; i < pctList.size(); i++)
            {
                PerceivedControlInfo pctInstance = pctList.get(i);
                myWriter.write(pctInstance.tested + ", " + pctInstance.inputTime + ", " + pctInstance.inputDuration + ", " +
                        pctInstance.inputPressure + ", " + pctInstance.timeBetweenInputs + ", "+ pctInstance.acceleration + "\n" );
            }*/
            myWriter.close();
        }
        catch (Exception exception)
        {

        }

        Intent gameOverIntent = new Intent(getContext(), gameOverActivity.class);
        gameOverIntent.putExtra("score", birdSprite.gameScore);
        ((Activity) getContext()).finish();
        getContext().startActivity(gameOverIntent);
    }

    //method to create gameplay/level
    public void createLevel()
    {
        //set dimensions for bird
        int birdWidth = getScreenWidth()/10;
        int birdHeight = getScreenHeight()/15;
        birdSprite = new playerSprite(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.birdimage), birdWidth, birdHeight, false), birdWidth, 25);
        birdSprite.maxJumpHeight = getScreenHeight();
        for(int i = 0; i < maxEnemies; i++)
        {
            generateEnemies();
        }

    }

    //alternative implementation for game logic - prototyping and experimentation phase
    public void gameLogic()
    {
        //checkForScoreIncrement(birdSprite);
        if(enemies.size() < maxEnemies)
        {
            generateEnemies();
        }

        for(int i = 0; i < enemies.size(); i++)
        {
            boolean impacted = false;
            boolean offScreened = false;
            //check if obstacle has impacted the bird, and remove obstacle from the screen if it has impacted
            if(impactObstacle(enemies.get(i)))
            {
                enemies.get(i).interact(birdSprite);
                enemies.get(i).yAxis = -(enemies.get(i).image.getHeight());
                //terminate(enemies.get(i));
                impacted = true;

            }
            //check if enemies have left the screen
            if(enemies.get(i).xAxis < -(enemies.get(i).image.getWidth()))
            {
                offScreened = true;
            }
            if(impacted)
            {
                terminate(enemies.get(i));
            }
            else if(offScreened)
            {
                enemies.get(i).boostScore(birdSprite);
                terminate(enemies.get(i));
            }

        }

        /* check to see if the bird has impacted or passed the top or bottom boundaries of the screen */
         if ((birdSprite.yAxis > bottomOfScreen) || (birdSprite.yAxis < topOfScreen))
         {
           birdSprite.yAxis = getScreenHeight()/2;
           birdSprite.life--;
         }

         if (birdSprite.yAxis < topOfScreen)
         {
           birdSprite.yAxis = getScreenHeight()/2;
           birdSprite.life--;
         }

         if(birdSprite.yAxis <= birdSprite.maxJumpHeight)
         {
             birdSprite.maxJumpHeight = bottomOfScreen;
         }



    }

    //start the game upon creation of the surfaceView
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

    //execute code to update view elements
    public void updateView()
    {
        gameLogic();
        birdSprite.moveSprite();
        for(int i = 0; i < enemies.size(); i++)
        {
            enemies.get(i).moveSprite();
        }

    }

    /*
    * method to generate enemies randomly for enemy type
    * */
    public void generateEnemies()
    {
        obstacle enemy1;
        String enemyType;
        //create randomised ints that allow for random generation of various enemy types
        int enemyRNG = (int)Math.floor(Math.random() *(2 - 0 + 1) + 0);

        //create randomised ints that allow for random placement of obstacle objects on the y axis
        int yAxisRNG = (int)Math.floor(Math.random() *(getScreenHeight() - 0 + 1) + 0);
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

    //terminateEnemy is used to recycle the bitmap associated with the enemy,
    // so as to free up memory and remove enemy from the arraylist when it is no longer needed, and another enemy can be created
    public void terminate(obstacle e)
    {
        e.image.recycle();
        enemies.remove(e);
    }

    private void startAccelerometerSensor(SensorEvent event)
    {
        float values[] = event.values;

        float accel_x = values[0];
        float accel_y = values[1];
        float accel_z = values[2];

        double acceleration = Math.sqrt((accel_x * accel_x) + (accel_y * accel_y) + (accel_z * accel_z));
        accelDelta = acceleration - previousAccel;
        previousAccel = acceleration;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        startAccelerometerSensor(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
