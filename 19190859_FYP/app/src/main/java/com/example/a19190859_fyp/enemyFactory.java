package com.example.a19190859_fyp;

import android.graphics.Bitmap;

//factory method pattern to allow for random creation of different types of enemies
public class enemyFactory {

    public enemy createEnemy(String enemyType, int x, int y){
        if(enemyType.equalsIgnoreCase("REDENEMY"))
        {
            return new redEnemy(Bitmap.createBitmap(redEnemyImage), x, y);
        }else if (enemyType.equalsIgnoreCase("GREENENEMY"))
        {
            return new redEnemy(Bitmap.createBitmap(greenEnemyImage), x, y);
        }
        else if(enemyType.equalsIgnoreCase("BLUEENEMY")){
            return new redEnemy(Bitmap.createBitmap(blueEnemyImage), x, y);
        }
        else{
            return null;
        }
    }
}
