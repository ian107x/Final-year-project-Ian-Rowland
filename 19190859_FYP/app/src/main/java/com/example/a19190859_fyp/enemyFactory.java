package com.example.a19190859_fyp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.res.Resources;
import android.view.SurfaceView;

//factory method pattern to allow for random creation of different types of enemies
public class enemyFactory {
    Bitmap bmp;

    //public characterView v;
    public enemy createEnemy(SurfaceView v, String enemyType, int x, int y){
        if(enemyType.equalsIgnoreCase("REDENEMY"))
        {
            //return new redEnemy(Bitmap.createBitmap(R.drawable.enemy), x, y);
            bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(v.getResources(), R.drawable.enemy), v.getWidth(), v.getHeight(), false);
            return new redEnemy(bmp, x, y);
        }else if (enemyType.equalsIgnoreCase("GREENENEMY"))
        {
            bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.enemy);
            return new greenEnemy(bmp, x, y);
        }
        else if(enemyType.equalsIgnoreCase("BLUEENEMY"))
        {
            bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.enemy);
            return new blueEnemy(bmp, x, y);
        }
        else{
            return null;
        }
    }
}
