package com.example.a19190859_fyp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class redEnemy extends obstacle {
    public redEnemy(Bitmap bitmap, int x, int y) {
        super(bitmap, x, y);
        this.objectVelocity = 7;
    }

    @Override
    public void interact(playerSprite p) {
        p.life -= 2;
    }

    @Override
    public void moveSprite() {
        this.xAxis -= objectVelocity;

    }
}
