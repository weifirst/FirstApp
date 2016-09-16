package com.example.administrator.firstapp;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/9/14.
 */
public class CScreenSize {
    static private int nScreenWidth = 0;
    static private int nScreenHeight = 0;

    static void Init(Context context)
    {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        nScreenWidth = dm.widthPixels ;
        nScreenHeight = dm.heightPixels;
    }

    static int GetWidth()
    {
        return nScreenWidth;
    }

    static int GetHeight()
    {
        return nScreenHeight;
    }
}
