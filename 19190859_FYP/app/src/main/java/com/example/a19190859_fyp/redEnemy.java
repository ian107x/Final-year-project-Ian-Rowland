package com.example.a19190859_fyp;

import android.graphics.Bitmap;

public class redEnemy extends obstacle {
    public redEnemy(Bitmap bitmap, int x, int y) {
        super(bitmap, x, y);
    }

    @Override
    public void interact() {

    }

    @Override
    public void draw() {

    }

    @Override
    public void moveSprite(characterView v) {
        this.xAxis -= v.speed;

    }
}
