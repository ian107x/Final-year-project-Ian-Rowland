package com.example.a19190859_fyp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class obstacle implements sprite {

    public Bitmap image;
    public int xAxis, yAxis;
    public int objectVelocity;
    public int maxVelocity;

    public obstacle(Bitmap bitmap, int x, int y)
    {
        image = bitmap;
        xAxis = x;
        yAxis = y;
        maxVelocity = 2 * objectVelocity;
    }

    public abstract void interact(playerSprite p);

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, xAxis, yAxis, null);
    }

    public void moveSprite()
    {
        this.xAxis -= objectVelocity;

    }
    public void boostSpeed()
    {
        if(objectVelocity < maxVelocity)
        {
            objectVelocity++;
        }
        if (objectVelocity > maxVelocity)
        {
            objectVelocity = maxVelocity;
        }
    }

    public void boostScore(playerSprite p )
    {
        playerSprite.gameScore++;
    }

}
