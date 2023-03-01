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
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    private PlayerSprite birdSprite;
    private ArrayList<Obstacle> enemies = new ArrayList<Obstacle>();
    private ArrayList<PerceivedControlInfo> pctList = new ArrayList<>();
    private Paint scoreBoard = new Paint();
    private Paint lifeCount = new Paint();
    private EnemyFactory ef = new EnemyFactory();
    private GameThread t;
    private boolean perceivedControlTest;
    private final int bottomOfScreen;
    private final int topOfScreen;
    private int maxEnemies;
    private double inputStart;
    private double inputend;
    private double inputduration;
    private double timeBetweenInputs;
    private double inputPressure;
    private boolean nextInputTested;
    private int startTime;
    private double previousAccel;
    private double accelDelta;
    private double inputAccel;
    private FileActions fa;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float accel_x;
    private float accel_y;
    private float accel_z;


    public GameView(Context context, String difficulty)
    {
        super(context);
        getHolder().addCallback(this);
        t = new GameThread(this, getHolder());
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
        fa = new FileActions();


        startTime = (int) System.currentTimeMillis()/1000;

        //ensures that time between inputs for first input of the game is not set to input start time
        inputend = 0;

        scoreBoard.setColor(Color.GREEN);
        scoreBoard.setTextSize(70);
        scoreBoard.setTypeface(Typeface.DEFAULT);
        scoreBoard.setAntiAlias(true);

        lifeCount.setColor(Color.GREEN);
        lifeCount.setTextSize(70);
        lifeCount.setTypeface(Typeface.DEFAULT);
        lifeCount.setAntiAlias(true);

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void draw(Canvas canvas) {

        super.draw(canvas);
        if(canvas!=null)
        {
            canvas.drawRGB(30, 150, 140);
            canvas.drawText("Score:  " + birdSprite.getScore(), 20, 60, scoreBoard);
            canvas.drawText("Life: " + birdSprite.getLife(), 20, 120, lifeCount);
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
        if(birdSprite.getLife() <= 0)
        {
            t.setRunning(false);
            setAlpha(0);
            endGame();
        }
    }

    public boolean impactObstacle(Obstacle e)
    {
        int left1 = birdSprite.getxAxis();
        int top1 = birdSprite.getyAxis();
        int right1 = left1 + birdSprite.getWidth();
        int bottom1 = top1 + birdSprite.getHeight();

        int left2 = e.getxAxis();
        int top2 = e.getyAxis();
        int right2 = left2 + e.getWidth();
        int bottom2 = top2 + e.getHeight();
        if(left1 < right2 && right1 > left2 && top1 < bottom2 && bottom1 > top2)
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

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            inputAccel = accelDelta;

            int PCTRNG = (int)Math.floor(Math.random() *(10 - 1 + 1) + 0);

                if(PCTRNG <=3)
                {
                    perceivedControlTest = true;

                }
                else
                {
                    perceivedControlTest = false;
                }

            //get input pressure
            inputPressure = event.getPressure();

            //get start time for input relative to game start time
            inputStart = (((int)System.currentTimeMillis())/1000.0) - startTime;

            //value for inputend at this point is the end time for the previous input
            timeBetweenInputs = inputStart - inputend;

            if(!perceivedControlTest)
            {
                birdSprite.setJumpPeak();
            }
        }

        //set up values to be recorded to object list upon the users finger being lifted
        else if((event.getAction() == MotionEvent.ACTION_UP) || event.getAction() == MotionEvent.ACTION_CANCEL)
        {
            //set new inputend value
            inputend = (((int)System.currentTimeMillis())/1000.0) - startTime;
            inputduration = (inputend - inputStart);
            boolean thisInputTested = nextInputTested;

            PerceivedControlInfo pct = new PerceivedControlInfo(thisInputTested, inputPressure, inputduration, timeBetweenInputs, inputStart, inputAccel, accel_x, accel_y, accel_z);
            pctList.add(pct);
            //prevInputTime = inputend;
            nextInputTested = perceivedControlTest;
        }
        return true;
    }

    public void endGame()
    {
        String fileData = "";
        try
        {
            //create new file to write input data to
            File inputs = fa.createFile(fa.inputsFileName + fa.fileExtension);

            //check if there is contents in the file before writing to it.
            //if there are contents, add those contents to data string first
            if(fa.checkForEmptyFile(inputs))
            {
                fileData += "PCT" + ", " + "input time" + ", " + "input duration" + ", " + "input pressure" + ", " +
                        " time since previous input" + ", " + "Accelerometer" + "accel_x" + "accel_y" + "accel_z" + "\n";
            }
            else
            {
                fileData += fa.compileFileIntoString(inputs);

            }
            for(int i = 0; i < pctList.size(); i++)
            {
                PerceivedControlInfo pctInstance = pctList.get(i);
                fileData += pctInstance.getTested() + ", " + pctInstance.getInputTime() + ", " + pctInstance.getInputDuration() + ", " +
                        pctInstance.getInputPressure() + ", " + pctInstance.getTimeBetweenInputs() + ", "+ pctInstance.getAccel() +
                        pctInstance.getAccelX() + pctInstance.getAccelY() + pctInstance.getAccelZ() + "\n";
            }
            fa.writeToFile(inputs, fileData);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
        gameOverIntent.putExtra("score", birdSprite.getScore());
        gameOverIntent.putExtra("inputs", fileData);
        ((Activity) getContext()).finish();
        getContext().startActivity(gameOverIntent);
    }

    //method to create gameplay/level
    public void createLevel()
    {
        //set dimensions for bird
        int birdWidth = getScreenWidth()/10;
        int birdHeight = getScreenHeight()/15;

        birdSprite = new PlayerSprite(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.birdimage), birdWidth, birdHeight, false), birdWidth, 25);
        //birdSprite.maxJumpHeight = getScreenHeight();
        birdSprite.startFalling();

        for(int i = 0; i < maxEnemies; i++)
        {
            generateEnemies();
        }

    }

    //alternative implementation for game logic - prototyping and experimentation phase
    public void gameLogic()
    {
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
                enemies.get(i).setyAxis(-(enemies.get(i).getHeight()));
                impacted = true;

            }
            //check if enemies have left the screen
            if(enemies.get(i).getxAxis() < -(enemies.get(i).getWidth()))
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
         if ((birdSprite.getyAxis() > bottomOfScreen) || (birdSprite.getyAxis() < topOfScreen))
         {
           //place bird at the mid point of the screen immediately if it hits the top or bottom of the screen
           birdSprite.setyAxis(bottomOfScreen/2);
           birdSprite.loseLife(1);
         }
    }

    //start the game upon creation of the surfaceView
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder)
    {
        createLevel();
        t.setRunning(true);
        t.start();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder)
    {
        sensorManager.unregisterListener(this);
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
        Obstacle enemy1;
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
    public void terminate(Obstacle e)
    {
        e.image.recycle();
        enemies.remove(e);
    }

    private void startAccelerometerSensor(SensorEvent event)
    {
        float values[] = event.values;

        accel_x = values[0];
        accel_y = values[1];
        accel_z = values[2];

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