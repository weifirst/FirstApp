package com.example.administrator.firstapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

// Created by wzg on 2016/9/13.

public class ContentView  extends ViewGroup {

    public ContentView(Context context/*, String passWord, GestureCallBack callBack*/) {
        super(context);
        /*screenDispaly = ScreenUtils.getScreenDispaly(context);
        d = screenDispaly[0]/3;
        this.list = new ArrayList<Point>();
        this.context = context;
        // 添加9个图标
        addChild();
        // 初始化一个可以画线的view
        drawl = new Drawl(context, list,passWord,callBack);*/

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for(int index = 0; index < getChildCount(); index++){
            View v = getChildAt(index);
            int nLeft = l+(r-l)/4;
            int nRight = r-(r-l)/4;
            int nTop = t+(b-t)/4;
            int nBottom = b-(b-t)/4;
            v.layout(nLeft, nTop,nRight,nBottom);
        }

        /*for (int i = 0; i < getChildCount(); i++) {
            //第几行
            int row = i/3;
            //第几列
            int col = i%3;
            View v = getChildAt(i);
            v.layout(col*d+d/baseNum, row*d+d/baseNum, col*d+d-d/baseNum, row*d+d-d/baseNum);
        }*/
    }

    @Override

    protected
    void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            v.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
