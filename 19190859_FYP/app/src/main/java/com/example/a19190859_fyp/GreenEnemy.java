package com.example.a19190859_fyp;

import android.graphics.Bitmap;

public class GreenEnemy extends Obstacle {
    public GreenEnemy(Bitmap bitmap, int x, int y) {
        super(bitmap, x, y);
        this.objectVelocity = 5;
    }

    @Override
    public void interact(PlayerSprite p)
    {
        p.gainLife(1);
    }


}
