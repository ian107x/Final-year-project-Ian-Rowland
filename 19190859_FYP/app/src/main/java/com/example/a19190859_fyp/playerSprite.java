package com.example.a19190859_fyp;

import android.graphics.Bitmap;

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
    public void draw() {

    }

    @Override
    public void moveSprite() {
        yAxis += birdVelocity;

    }
}
