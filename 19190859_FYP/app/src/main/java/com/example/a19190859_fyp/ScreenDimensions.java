package com.example.a19190859_fyp;

import android.content.res.Resources;

public class ScreenDimensions {

    public int getScreenWidth()
    {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight()
    {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
