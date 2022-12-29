package com.example.a19190859_fyp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class characterView extends View {

    private Bitmap bird;

    private int birdXaxis = 10;
    private int birdYaxis;
    private int speed = 10;
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
        gameBackground= Bitmap.createScaledBitmap(gameBackground, getScreenWidth(), getScreenHeight(), false);

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

        //canvasWidth = canvas.getWidth();
        //canvasHeight = canvas.getHeight();

        canvasWidth = getScreenWidth();
        canvasHeight = getScreenHeight();

        //canvas.drawBitmap(gameBackground, 0, 0, null);
        canvas.drawBitmap(gameBackground, canvasWidth, canvasHeight, null);


        int minBirdY = bird.getHeight();
        int maxBirdY = canvasHeight - bird.getHeight() * 3;
        birdYaxis += speed;

        if(birdYaxis < minBirdY){
            birdYaxis = minBirdY;
        }
        if(birdYaxis > maxBirdY){
            birdYaxis = maxBirdY;
        }
        speed +=5;


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

            if (lifeNum == 0){
                Toast.makeText(getContext(), "Game Over" , Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(), gameOverActivity.class);
                gameOverIntent.putExtra("Score", score);
                getContext().startActivity(gameOverIntent);
            }
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

    //method for resizing bitmap for background and player character
    public Bitmap resizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }



    public boolean onTouchEvent(MotionEvent event){


        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;
            float inputPressure = event.getPressure();
            float prevInputTime = 0;
            float inputStart = 0;
            float inputend = 0;
            float inputduration = inputend - inputStart;
            float timeBetweenInputs = inputStart - prevInputTime;
            //characterSprite.y = characterSprite.y - (characterSprite.yVelocity * 10);
            //speed = -10;
            //check for perceived control
            if(perceivedControlTest){

            }
        }
        return true;
    }
}
