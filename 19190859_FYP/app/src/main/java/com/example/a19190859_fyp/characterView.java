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

    private Bitmap bird;
    private Bitmap enemy;

    private int birdXaxis = 10;
    private int birdYaxis;
    public int speed = 10;
    private int lifeNum;


    private playerSprite birdSprite;

    private boolean touch = false;

    private int canvasWidth, canvasHeight;
    private ArrayList<obstacle> enemies = new ArrayList<obstacle>();

    public int enemyX, enemyY = 20;
    public static int enemySpeed = 20;
    private Paint enemyPaint = new Paint();

    private int score;

    private double startTime;
    private double endTime;

    private Bitmap gameBackground;
    private Paint scoreBoard = new Paint();
    private Paint lifeCount = new Paint();
    private Bitmap life[] = new Bitmap[2];
    enemyFactory ef = new enemyFactory();
    gameThread t;
    private infoDB db = new infoDB(getContext());

    private int minBirdY;
    private int maxBirdY;

    public characterView(Context context){
        super(context);

        //quick note of recycle for bitmaps bird.recycle();

        gameBackground = BitmapFactory.decodeResource(getResources(), R.drawable.bg1);
        gameBackground= Bitmap.createScaledBitmap(gameBackground, getScreenWidth(), getScreenHeight(), false);

        enemy = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        enemy = Bitmap.createScaledBitmap(enemy, 200, 200, false);

        enemyPaint.setColor(Color.BLUE);
        enemyPaint.setAntiAlias(false);

        scoreBoard.setColor(Color.BLUE);
        scoreBoard.setTextSize(50);
        scoreBoard.setTypeface(Typeface.DEFAULT);
        scoreBoard.setAntiAlias(true);

        lifeCount.setColor(Color.GREEN);
        lifeCount.setTextSize(50);
        lifeCount.setTypeface(Typeface.DEFAULT);
        lifeCount.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.life_crystal);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.life_crystal_grayed);
        birdYaxis = 500;
        score = 0;
        lifeNum = 5;
        startTime = System.currentTimeMillis();
        obstacle e = ef.createEnemy(this, "GREENENEMY", enemyX, enemyY);

        t = new gameThread(this, getHolder());

    }

    //public void draw(Canvas canvas) {
//
  //      super.draw(canvas);
    //}

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvasWidth = getScreenWidth();
        canvasHeight = getScreenHeight();
        //canvas.drawBitmap(gameBackground, 0, 0, null);
        canvas.drawBitmap(gameBackground, canvasWidth, canvasHeight, null);


        //int minBirdY = bird.getHeight();
        //int maxBirdY = canvasHeight - bird.getHeight() * 3;

        birdYaxis = birdXaxis + speed;

        if(birdYaxis < minBirdY){
            birdYaxis = minBirdY;
        }
        if(birdYaxis > maxBirdY){
            birdYaxis = maxBirdY;
        }

        speed = speed + 10;


        if(touch){
            birdYaxis = birdYaxis - (birdSprite.birdVelocity * 10);
            canvas.drawBitmap(bird, birdXaxis, birdYaxis, null);
            touch = false;
            //speed = -20;
        }else{
            canvas.drawBitmap(bird, birdXaxis, birdYaxis, null);
        }

        enemyX = enemyX - enemySpeed;
        if(impactEnemyCheck(enemyX, enemyY)){
            lifeNum = lifeNum - 1;
            //birdXaxis = birdXaxis - 100;
            enemyX = -100;

            if (lifeNum == 0){
                endGame();
            }
        }

        if(enemyX < 0){
            enemyX = canvasWidth + 21;
            enemyY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }
        canvas.drawCircle(enemyX, enemyY, 20, enemyPaint);

        canvas.drawText("Score: " + score, 20, 60, scoreBoard);
        canvas.drawText("Life: " + lifeNum, 20, 120, lifeCount);
    }

    public boolean impactEnemyCheck(int x, int y)
    {
        for(int i = 0; i <= enemies.size(); i++) {
            if (birdXaxis < x && x < (birdXaxis + bird.getWidth()) && birdYaxis < y && y < (birdYaxis + bird.getHeight())) {
                return true;
            }//else{
            return false;
            //}
        }return true;
    }

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

        boolean perceivedControlTest;
        float prevInputTime;
        float inputStart;
        float inputend;
        float inputduration;
        float timeBetweenInputs;

        int PCTRNG = (int)Math.floor(Math.random() *(10 - 1 + 1) + 0);

        if(PCTRNG == 3 || PCTRNG == 5 ||PCTRNG == 7 )
        {
            perceivedControlTest = true;
        }else
        {
            perceivedControlTest = false;
        }


        if(event.getAction() == MotionEvent.ACTION_DOWN){
            //if(perceivedControlTest){
                //dont act on input
            //}else{



            float x = event.getX();
            float y = event.getY();

            float inputPressure = event.getPressure();
            prevInputTime = 0;
            inputStart = 0;
            inputend = 0;
            inputduration = inputend - inputStart;
            timeBetweenInputs = inputStart - prevInputTime;
            //send values to database

            if(perceivedControlTest)
            {
                touch = false;
            }
            else
            {
                touch = true;
            }
            //check for perceived control

            //}
        }else if(event.getAction() == MotionEvent.ACTION_UP){

        }
        return super.onTouchEvent(event);
    }

    public void endGame(){
        endTime = System.currentTimeMillis();
        double totalTime = endTime - startTime;
        Toast.makeText(getContext(), "Game Over" , Toast.LENGTH_SHORT).show();
        Intent gameOverIntent = new Intent(getContext(), gameOverActivity.class);
        gameOverIntent.putExtra("Score", score);
        getContext().startActivity(gameOverIntent);
    }

    //theoretical method to create gameplay/level
    public void createLevel()
    {
        birdSprite = new playerSprite(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bird), 200, 200, false), 250, 250);
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
        for(int i = 0; i <= enemies.size(); i++)
        {
            if(impactObstacle(enemies.get(i)))
            {
                lifeNum--;
                if(lifeNum <=0) {
                    endGame();
                }
            }
            if(enemies.get(i).xAxis < 0)
            {
                //recycle bitmap to free up memory
                enemies.set(i, terminateEnemy(enemies.get(i)));
                enemies.remove(i);
            }
        }

        //pre implementation logic for hitting the top or bottom of the screen

         if (birdSprite.yAxis < minBirdY)
         {
           birdSprite.yAxis = getScreenHeight()/2;
           lifeNum--;
         }
         if (birdSprite.yAxis > maxBirdY)
         {
           birdSprite.yAxis = getScreenHeight()/2;
           lifeNum--;
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
        //characterSprite.update
        for(int i = 0; i <= enemies.size(); i++)
        {
            enemies.get(i).moveSprite();
        }

    }

    public void generateEnemies()
    {
        obstacle enemy1;
        int enemyRNG = (int)Math.floor(Math.random() *(2 - 0 + 1) + 0);
        if(enemyRNG == 0)
        {
            enemy1 = ef.createEnemy(this, "GREENENEMY", 200, 200);

        }
        else if(enemyRNG == 1)
        {
            enemy1 = ef.createEnemy(this, "REDENEMY", 200, 200);
        }
        else
        {
            enemy1 = ef.createEnemy(this, "BLUEENEMY", 200, 200);
        }

        enemies.add(enemy1);
    }

    public obstacle terminateEnemy(obstacle e)
    {
        e.image.recycle();
        return null;
    }
}
