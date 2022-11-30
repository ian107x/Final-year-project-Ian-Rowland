package com.example.a19190859_fyp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class characterView extends View {

    private Bitmap bird;

    private int birdXaxis = 10;
    private int birdYaxis;
    private int speed;
    private int lifeNum;
    private boolean perceivedControlTest;

    private boolean touch = false;

    private int canvasWidth, canvasHeight;

    private int enemyX, enemyY, enemySpeed;
    //private Paint enemyPaint = new Paint();

    private int score;

    private double startTime;
    private double endTime;

    private Bitmap gameBackground;
    private Paint scoreBoard = new Paint();
    private Bitmap life[] = new Bitmap[2];

    public characterView(Context context){
        super(context);
        bird = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        gameBackground = BitmapFactory.decodeResource(getResources(), R.drawable.bg1);

        //enemyPaint.setColor(Color.Blue);
        //enemyPaint.setAntiAlias(false);

        scoreBoard.setColor(Color.BLUE);
        scoreBoard.setTextSize(50);
        scoreBoard.setTypeface(Typeface.DEFAULT);
        scoreBoard.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.life_crystal);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.life_crystal_grayed);
        birdYaxis = 500;
        score = 0;
        lifeNum = 5;

    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        canvas.drawBitmap(gameBackground, 0, 0, null);

        int minBirdY = bird.getHeight();
        int maxBirdY = canvasHeight - bird.getHeight() * 3;
        birdYaxis += speed;

        if(birdYaxis < minBirdY){
            birdYaxis = minBirdY;
        }
        if(birdYaxis > maxBirdY){
            birdYaxis = maxBirdY;
        }
        speed +=2;


        if(touch){
            canvas.drawBitmap(bird, birdXaxis, birdYaxis, null);
            touch = false;
        }else{
            canvas.drawBitmap(bird, birdXaxis, birdYaxis, null);
        }

        enemyX = enemyX - enemySpeed;
        if(impactEnemyCheck(birdXaxis, birdYaxis)){
            lifeNum = lifeNum - 1;
            birdXaxis = birdXaxis - 100;
        }

        /*
        *
        * enemyX = enemyX - enemySpeed
        *if(impactEnemyCheck(enemyX, enemyY)){
        *    lifeNum = lifeNum - 1;
        *    birdXaxis = birdXaxis - 100;
        }

        * if(enemyX < 0){
        *   enemyX = canvasWidth + 21;
        *   enemyY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        * }
        * canvas.drawCircle(enemyX, enemyY, 10, enemyPaint);
        * */


        canvas.drawBitmap(bird, 0, 0, null);

        canvas.drawText("Score: ", 20, 60, scoreBoard);

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

    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;

            speed = -10;
            //check for perceived control
        }
        return true;
    }
}
