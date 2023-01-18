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
            Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(v.getResources(), R.drawable.redcloud), v.getWidth()/5, v.getHeight()/10, false);
            obstacle re = new redEnemy(bmp, x, y);
            //bmp.recycle();
            return re;
        }else if (enemyType.equalsIgnoreCase("GREENENEMY"))
        {
            Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(v.getResources(), R.drawable.greencloud), v.getWidth()/5, v.getHeight()/10, false);
            obstacle ge = new redEnemy(bmp, x, y);
            //bmp.recycle();
            return ge;
        }
        else if(enemyType.equalsIgnoreCase("BLUEENEMY"))
        {
            Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(v.getResources(), R.drawable.bluecloud), v.getWidth()/5, v.getHeight()/10, false);
            obstacle be = new blueEnemy(bmp, x, y);
            //bmp.recycle();
            return be;
        }
        else{
            return null;
        }
    }
}
