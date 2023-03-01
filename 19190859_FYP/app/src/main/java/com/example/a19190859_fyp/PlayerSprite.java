package com.example.a19190859_fyp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class PlayerSprite {

    private Bitmap image;
    private int xAxis, yAxis;
    private final int birdVelocity = 10;
    private int maxJumpHeight;
    private volatile int life;
    private volatile int gameScore;
    private int height;
    private int width;

    public PlayerSprite(Bitmap bitmap, int x, int y)
    {
        image = bitmap;
        xAxis = x;
        yAxis = y;
        life = 10;
        gameScore = 0;
        height = this.image.getHeight();
        width = this.image.getWidth();
    }


    //draw the sprite PlayerSprite class to the canvas
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, xAxis, yAxis, null);

    }

    //change the yAxis value of the bird
    public void moveSprite()
    {
        {
            if(this.yAxis > this.maxJumpHeight)
            {
                yAxis -= birdVelocity;
                if(this.yAxis <= this.maxJumpHeight)
                {
                    //this.maxJumpHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
                    startFalling();
                }
            }
            else
            {
                yAxis += birdVelocity;

            }
        }
    }

    //set the jump peak to make the bird jump
    public void setJumpPeak()
    {
        this.maxJumpHeight = this.yAxis - (this.height * 2);

        //make it so that maxJumpHeight cannot be set to a point where the bird cannot reach, thereby creating an infinite jumping loop
        if(this.maxJumpHeight < 0)
        {
            this.maxJumpHeight = 0;
        }
    }

    //set jump peak to the bottom of the screen to make the bird fall
    public void startFalling()
    {
        this.maxJumpHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
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

    public void setScore(int score)
    {
        this.gameScore = score;
    }

    public int getScore()
    {
        return this.gameScore;
    }

    public int getLife()
    {
        return this.life;
    }

    public void setLife(int life)
    {
        this.life = life;
    }

    public void gainLife(int heal)
    {
        this.life += heal;
    }

    public void loseLife(int damage)
    {
        this.life -= damage;
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
