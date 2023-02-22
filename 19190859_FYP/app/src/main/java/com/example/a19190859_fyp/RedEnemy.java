package com.example.a19190859_fyp;

import android.graphics.Bitmap;

public class RedEnemy extends Obstacle {
    public RedEnemy(Bitmap bitmap, int x, int y) {
        super(bitmap, x, y);
        this.objectVelocity = 7;
    }

    @Override
    public void interact(PlayerSprite p)
    {
        p.loseLife(2);
    }



}
