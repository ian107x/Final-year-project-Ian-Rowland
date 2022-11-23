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

    private boolean touch;

    private int canvasWidth, canvasHeight;

    private Bitmap gameBackground;
    private Paint scoreBoard = new Paint();
    private Bitmap life[] = new Bitmap[2];

    public characterView(Context context){
        super(context);
        bird = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        gameBackground = BitmapFactory.decodeResource(getResources(), R.drawable.bg1);;

        scoreBoard.setColor(Color.BLUE);
        scoreBoard.setTextSize(50);
        scoreBoard.setTypeface(Typeface.DEFAULT);
        scoreBoard.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.lifeCrystal);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.lifeCrystalGrayed);
        birdYaxis = 500;

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


        canvas.drawBitmap(bird, 0, 0, null);

        canvas.drawText("Score: ", 20, 60, scoreBoard);

        canvas.drawBitmap(life[0], 520, 10, null);
        canvas.drawBitmap(life[0], 580, 10, null);
        canvas.drawBitmap(life[0], 640, 10, null);
        canvas.drawBitmap(life[0], 700, 10, null);
        canvas.drawBitmap(life[0], 760, 10, null);
    }

    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;

            speed = -10;
        }
        return true;
    }
}
