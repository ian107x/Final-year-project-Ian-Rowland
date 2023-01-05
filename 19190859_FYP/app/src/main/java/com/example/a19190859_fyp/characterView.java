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

public class characterView extends SurfaceView implements SurfaceHolder.Callback{

    private Bitmap bird;
    private Bitmap enemy;

    private int birdXaxis = 10;
    private int birdYaxis;
    public int speed = 10;
    private int lifeNum;
    private boolean perceivedControlTest;

    private boolean touch = false;

    private int canvasWidth, canvasHeight;

    private int enemyX, enemyY, enemySpeed = 20;
    private Paint enemyPaint = new Paint();

    private int score;

    private double startTime;
    private double endTime;

    private Bitmap gameBackground;
    private Paint scoreBoard = new Paint();
    private Paint lifeCount = new Paint();
    private Bitmap life[] = new Bitmap[2];
    enemyFactory ef = new enemyFactory();

    public characterView(Context context){
        super(context);
        bird = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        bird = Bitmap.createScaledBitmap(bird, 200, 100, false);
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


        int minBirdY = bird.getHeight();
        int maxBirdY = canvasHeight - bird.getHeight() * 3;

        birdYaxis = birdXaxis + speed;

        if(birdYaxis < minBirdY){
            birdYaxis = minBirdY;
        }
        if(birdYaxis > maxBirdY){
            birdYaxis = maxBirdY;
        }

        speed = speed + 10;


        if(touch){
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

        canvas.drawText("Score: ", 20, 60, scoreBoard);
        canvas.drawText("Score: ", 20, 120, lifeCount);

        canvas.drawBitmap(life[0], 520, 10, null);
        canvas.drawBitmap(life[0], 580, 10, null);
        canvas.drawBitmap(life[0], 640, 10, null);
        canvas.drawBitmap(life[0], 700, 10, null);
        canvas.drawBitmap(life[0], 760, 10, null);
    }

    public boolean impactEnemyCheck(int x, int y){
        if(birdXaxis < x && x < (birdXaxis + bird.getWidth()) && birdYaxis < y && y < (birdYaxis + bird.getHeight())){
            return true;
        }//else{
        return false;
        //}
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }



    public boolean onTouchEvent(MotionEvent event){


        if(event.getAction() == MotionEvent.ACTION_DOWN){
            //if(perceivedControlTest){
                //dont act on input
            //}else{
            touch = true;
            float inputPressure = event.getPressure();
            float prevInputTime = 0;
            float inputStart = 0;
            float inputend = 0;
            float inputduration = inputend - inputStart;
            float timeBetweenInputs = inputStart - prevInputTime;
            //send values to database

            //birdYaxis = birdYaxis - (birdYVelocity * 10);
            speed = -30;
            //speed+=20;
            //check for perceived control

            //}
        }else if(event.getAction() == MotionEvent.ACTION_UP){

        }
        return true;
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
    public void createLevel(){

    }

    //alternative implementation for game logic - prototyping and experimentation phase
    public void gameLogic(){

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    public void updateView()
    {

    }

    public obstacle terminateEnemy(obstacle e)
    {
        e.image.recycle();
        return null;
    }
}
