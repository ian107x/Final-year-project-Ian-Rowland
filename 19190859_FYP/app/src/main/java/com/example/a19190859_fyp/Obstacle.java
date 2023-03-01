package com.example.a19190859_fyp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Obstacle implements Sprite {

    public Bitmap image;
    private int xAxis, yAxis;
    public int objectVelocity;
    private int maxVelocity;
    private int height;
    private int width;

    public Obstacle(Bitmap bitmap, int x, int y)
    {
        image = bitmap;
        xAxis = x;
        yAxis = y;
        maxVelocity = 2 * objectVelocity;
        height = this.image.getHeight();
        width = this.image.getWidth();
    }

    public abstract void interact(PlayerSprite p);

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

    public void boostScore(PlayerSprite p)
    {
        //p.gameScore++;
        p.setScore(p.getScore() + 1);
        //if(p.getScore() % 20 == 0 && p.getScore() <= 60){}
        if(p.getScore() == 20 || p.getScore() == 40 || p.getScore() == 60)
        {
            boostSpeed();
        }
    }

    public int getxAxis()
    {
        return this.xAxis;
    }
    public void setxAxis(int x)
    {
        this.xAxis = x;
    }

    public int getyAxis()
    {
        return this.yAxis;
    }

    public void setyAxis(int y)
    {
        this.yAxis = y;
    }

    public int getWidth()
    {
        return this.width;
    }
    public int getHeight()
    {
        return this.height;
    }

}
