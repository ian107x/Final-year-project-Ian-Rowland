package com.example.a19190859_fyp;

import android.graphics.Bitmap;

public class greenEnemy extends obstacle {
    public greenEnemy(Bitmap bitmap, int x, int y) {
        super(bitmap, x, y);
        this.objectVelocity = 5;
    }

    @Override
    public void interact() {

    }

    @Override
    public void draw() {

    }

    @Override
    public void moveSprite() {
        this.xAxis += objectVelocity;

    }
}
