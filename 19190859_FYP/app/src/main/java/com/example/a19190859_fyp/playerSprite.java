package com.example.a19190859_fyp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class playerSprite implements sprite{

    public Bitmap image;
    public int xAxis, yAxis;
    public int birdVelocity = 10;

    public playerSprite(Bitmap bitmap, int x, int y)
    {
        image = bitmap;
        xAxis = x;
        yAxis = y;
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, xAxis, yAxis, null);

    }

    //@Override
    public void moveSprite(boolean touch) {
        if(touch/* && yAxis != maxJumpHeight */)
        {
            yAxis -= birdVelocity;
        }
        else
        {
            yAxis += birdVelocity;
        }
    }
}
