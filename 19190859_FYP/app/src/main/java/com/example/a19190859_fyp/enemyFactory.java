package com.example.a19190859_fyp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

//factory method pattern to allow for random creation of different types of enemies
public class enemyFactory {
    //Bitmap bmp;

    //public characterView v;
    public obstacle createEnemy(SurfaceView v, String enemyType, int x, int y){
        if(enemyType.equalsIgnoreCase("REDENEMY"))
        {
            //return new redEnemy(Bitmap.createBitmap(R.drawable.enemy), x, y);
            Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(v.getResources(), R.drawable.enemy), v.getWidth(), v.getHeight(), false);
            return new redEnemy(bmp, x, y);
        }else if (enemyType.equalsIgnoreCase("GREENENEMY"))
        {
            Bitmap bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.enemy);
            return new greenEnemy(bmp, x, y);
        }
        else if(enemyType.equalsIgnoreCase("BLUEENEMY"))
        {
            Bitmap bmp = BitmapFactory.decodeResource(v.getResources(), R.drawable.enemy);
            obstacle be = new blueEnemy(bmp, x, y);
            bmp.recycle();
            return be;
        }
        else{
            return null;
        }
    }
}
