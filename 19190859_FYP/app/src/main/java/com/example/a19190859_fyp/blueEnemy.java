package com.example.a19190859_fyp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class blueEnemy extends obstacle {
    public blueEnemy(Bitmap bitmap, int x, int y) {
        super(bitmap, x, y);
        this.objectVelocity = 3;
    }

    @Override
    public void interact(playerSprite p)
    {
        p.life -= 1;
    }

}
