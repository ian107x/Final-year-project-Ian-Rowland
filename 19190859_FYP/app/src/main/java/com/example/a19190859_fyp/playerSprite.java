package com.example.a19190859_fyp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class playerSprite implements sprite{

    public Bitmap image;
    public int xAxis, yAxis;
    public int birdVelocity = 10;
    public int maxJumpHeight;
    public int life;
    public int gameScore;

    public playerSprite(Bitmap bitmap, int x, int y)
    {
        image = bitmap;
        xAxis = x;
        yAxis = y;
        life = 5;
        gameScore = 0;
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, xAxis, yAxis, null);

    }

    @Override
    public void moveSprite(){
        {
            if(this.yAxis > this.maxJumpHeight)
            {
                yAxis -= birdVelocity * 10;
                if(this.yAxis <= this.maxJumpHeight)
                {
                    this.maxJumpHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
                }
            }
            else
            {
                yAxis += birdVelocity;

            }
        }
    }
}
