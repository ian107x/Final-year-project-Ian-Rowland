package com.example.a19190859_fyp;

import android.graphics.Bitmap;

public class BlueEnemy extends Obstacle {
    public BlueEnemy(Bitmap bitmap, int x, int y) {
        super(bitmap, x, y);
        this.objectVelocity = 3;
    }

    @Override
    public void interact(PlayerSprite p)
    {
        p.life -= 1;
        //p.setLife(p.getLife() - 1);
        p.loseLife(1);
    }

}
