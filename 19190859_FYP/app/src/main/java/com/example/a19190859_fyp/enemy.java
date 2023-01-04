package com.example.a19190859_fyp;

import android.graphics.Bitmap;

public abstract class enemy {

    private Bitmap image;
    public int xAxis, yAxis;

    public enemy(Bitmap bitmap, int x, int y){
        image = bitmap;
        xAxis = x;
        yAxis = y;
    }

    public abstract void interact();

    public abstract void draw();

}
