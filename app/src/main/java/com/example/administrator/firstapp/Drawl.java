package com.example.administrator.firstapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Drawl extends View {
    private int mov_x;// 声明起点坐标
    private int mov_y;
    private Paint paint;// 声明画笔
    private Canvas canvas;// 画布
    private Bitmap bitmap;// 位图
    private int m_nRadius = 60;
    /**
     * 用户绘图的回调
     */
    private GestureCallBack callBack;
    private PointF m_ptFirst = new PointF(0,0);

    PointF m_nPos[][] = {{new PointF(0,0), new PointF(0,0), new PointF(0,0)},
                         {new PointF(0,0), new PointF(0,0), new PointF(0,0)},
                         {new PointF(0,0), new PointF(0,0), new PointF(0,0)}};

    ArrayList<PointF> m_ptList = new ArrayList<>();


    public Drawl(Context context, String passWord,GestureCallBack callBack) {
        super(context);

        paint = new Paint(Paint.DITHER_FLAG);// 创建一个画笔
        bitmap = Bitmap.createBitmap(CScreenSize.GetWidth(), CScreenSize.GetWidth(), Bitmap.Config.ARGB_8888); // 设置位图的宽高
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
        this.callBack = callBack;

        paint.setStyle(Paint.Style.STROKE);// 设置非填充
        paint.setStrokeWidth(2);// 笔宽5像素
        //paint.setColor(Color.rgb(4, 115, 157));// 设置颜色
        paint.setColor(Color.rgb(0, 170, 255));// 设置颜色
        paint.setAntiAlias(true);// 不显示锯齿

        paint.setStyle(Paint.Style.FILL);// 设置非填充
        DrawNineCircle();
    }

    // 画位图
    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);

        // 创建画笔
       /* Paint p = new Paint();
        p.setColor(Color.WHITE);// 设置红色
        p.setStyle(Paint.Style.STROKE);                   //空心效果
        p.setAntiAlias(true);                       //设置画笔为无锯齿
        p.setStrokeWidth((float) 3.0);              //线宽

        int nWidth = CScreenSize.GetWidth();
        for( int i=0; i<3; i++ ){
            for( int j=0; j<3; j++ ) {
                float x = (float)((j*2+1)*(nWidth/6.0));
                float y = (float)((i*2+1)*(nWidth/6.0));
                m_nPos[i][j].x = x;
                m_nPos[i][j].y = y;
                canvas.drawCircle(x, y, m_nRadius, p);
            }
        }*/
    }

    // 触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //mov_x = event.getX();
                //mov_y = (int) event.getY();
               // canvas.drawLine(0,0, event.getX(), event.getY(),paint);// 画线
               // invalidate();
                m_ptFirst = InPoint(event.getX(),event.getY());
                m_ptList.clear();
                if( m_ptFirst.x!=0 ){
                    m_ptList.add( m_ptFirst );
                }



 /*               // 判断当前点击的位置是处于哪个点之内
                currentPoint = getPointAt(mov_x, mov_y);
                if (currentPoint != null) {
                    currentPoint.setHighLighted(true);
                    passWordSb.append(currentPoint.getNum());
                }
                // canvas.drawPoint(mov_x, mov_y, paint);// 画点
                invalidate();*/
                break;
            case MotionEvent.ACTION_MOVE:
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                DrawNineCircle();

                PointF pt = InPoint(event.getX(),event.getY());
                if( pt.x!=0 ){
                    int nCount = m_ptList.size();
                    if( nCount==0 || nCount>0 && (m_ptList.get(nCount-1).x!=pt.x || m_ptList.get(nCount-1).y!=pt.y)  ) {
                        m_ptList.add(pt);
                    }
                }
                if( !m_ptList.isEmpty() ) {
                    DrawDotAndLine(event.getX(), event.getY());
                }
                invalidate();

         /*       clearScreenAndDrawList();

                // 得到当前移动位置是处于哪个点内
                Point pointAt = getPointAt((int) event.getX(), (int) event.getY());
                //代表当前用户手指处于点与点之前
                if(currentPoint==null && pointAt == null){
                    return true;
                }else{//代表用户的手指移动到了点上
                    if(currentPoint == null){//先判断当前的point是不是为null
                        //如果为空，那么把手指移动到的点赋值给currentPoint
                        currentPoint = pointAt;
                        //把currentPoint这个点设置选中为true;
                        currentPoint.setHighLighted(true);
                        passWordSb.append(currentPoint.getNum());
                    }
                }

                if (pointAt == null || currentPoint.equals(pointAt)
                        || pointAt.isHighLighted()) {
                    // 点击移动区域不在圆的区域 或者
                    // 如果当前点击的点与当前移动到的点的位置相同
                    // 那么以当前的点中心为起点，以手指移动位置为终点画线
                    canvas.drawLine(currentPoint.getCenterX(),
                            currentPoint.getCenterY(), event.getX(), event.getY(),
                            paint);// 画线



                } else {
                    // 如果当前点击的点与当前移动到的点的位置不同
                    // 那么以前前点的中心为起点，以手移动到的点的位置画线
                    canvas.drawLine(currentPoint.getCenterX(),
                            currentPoint.getCenterY(), pointAt.getCenterX(),
                            pointAt.getCenterY(), paint);// 画线

                    pointAt.setHighLighted(true);

                    Pair<Point, Point> pair = new Pair<Point, Point>(currentPoint,
                            pointAt);
                    lineList.add(pair);

                    // 赋值当前的point;
                    currentPoint = pointAt;
                    passWordSb.append(currentPoint.getNum());
                }
                invalidate();*/
                break;
            case MotionEvent.ACTION_UP:// 当手指抬起的时候
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                DrawNineCircle();
                invalidate();
                int a = m_ptList.size();
                if( a==5 ){
                    callBack.checkedSuccess();
                }
                else{
                    callBack.checkedFail();
                }
                    /*     // 清掉屏幕上所有的线，只画上集合里面保存的线
                if(passWord.equals(passWordSb.toString())){
                    //代表用户绘制的密码手势与传入的密码相同
                    callBack.checkedSuccess();
                }else{
                    //用户绘制的密码与传入的密码不同。
                    callBack.checkedFail();
                }
                //重置passWordSb
                passWordSb = new StringBuilder();
                //清空保存点的集合
                lineList.clear();
                //重新绘制界面
                clearScreenAndDrawList();
                for (Point p : list) {
                    p.setHighLighted(false);
                }
                invalidate();*/
                break;
            default:
                break;
        }
        return true;
    }

    private  PointF InPoint(float x, float y)
    {
        PointF pt = new PointF(0,0);
        for( int i=0; i<3; i++){
            for( int j=0; j<3; j++ ){
                float f = (float) Math.sqrt((m_nPos[i][j].x-x)*(m_nPos[i][j].x-x)+(m_nPos[i][j].y-y)*(m_nPos[i][j].y-y));
                if( f<m_nRadius ){
                    return m_nPos[i][j];
                }
            }
        }
        return pt;
    }

    private void DrawDotAndLine(float x, float y)
    {
        int nPointCount = m_ptList.size();
        for( int i=0; i<nPointCount; i++ ){
            paint.setStyle(Paint.Style.FILL);// 设置填充
            canvas.drawCircle(m_ptList.get(i).x, m_ptList.get(i).y, 16, paint);

            paint.setStyle(Paint.Style.STROKE);// 设置非填充
            canvas.drawCircle(m_ptList.get(i).x, m_ptList.get(i).y, m_nRadius, paint);

            //canvas.draw
            if( i==nPointCount-1 ){
                canvas.drawLine(m_ptList.get(i).x, m_ptList.get(i).y, x, y, paint);
            }
            else {
                canvas.drawLine(m_ptList.get(i).x, m_ptList.get(i).y, m_ptList.get(i+1).x, m_ptList.get(i+1).y, paint);// 画线
                //画线上的箭头
                //确定箭头顶部的坐标
                PointF ptTop = GetCircleLineCrosspoint(m_ptList.get(i).x, m_ptList.get(i).y, m_ptList.get(i+1).x, m_ptList.get(i+1).y, 50);

                PointF ptLeft = new PointF(0,0);
                PointF ptRight = new PointF(0,0);
                GetTrianglePoint(m_ptList.get(i).x, m_ptList.get(i).y, m_ptList.get(i+1).x, m_ptList.get(i+1).y, 30, 10, ptLeft, ptRight);

                Path path = new Path();
                path.moveTo(ptTop.x, ptTop.y);
                path.lineTo(ptLeft.x, ptLeft.y);
                path.lineTo(ptRight.x, ptRight.y);
                path.close();
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(path, paint);
            }
        }
    }

    private PointF GetCircleLineCrosspoint(float x1, float y1, float x2, float y2, int nLen)
    {
        PointF pt = new PointF();
        if( x1==x2 ){
            pt.x = x1;
            pt.y = y1+(y2>y1?nLen:-nLen);
            return pt;
        }

        if( y1==y2 ){
            pt.x = x1+(x2>x1?nLen:-nLen);
            pt.y = y1;
            return pt;
        }
         /*x1=[-b+根号下（b^2-4ac)]/2a
          x2=[-b-根号下（b^2-4ac)]/2a*/
        //(1) (fx-x1)*(fx-x1)+(fy-y1)*(fy-y1) = nLen*nLen;
        //(2) (y-y1)/(y2-y1) = (fx-x1)/(x2-x1)
        //      y-y1 = (fx-x1)*(y2-y1)/(x2-x1)
        //      y = (fx-x1)*(y2-y1)/(x2-x1)+y1;
        //      y = (fx-x1)*(b1)/(a1)+y1;
        //      y = (fx*b1-x1*b1)/(a1)+y1;
        //      y = fx*b1/a1-x1*b1/a1+y1;

        double K = 0;
        if( x1!=x2 ) {
            K = (y2 - y1) / (x2 - x1);
        }
        //      y = K*x-x1*K+y1;
        double B = -x1*K+y1;
        //最终(2)   y = K*x+B;

        //int c1 = nLen*nLen;
        //由(1)得： fx*fx-2*x1*fx+x1*x1 + fy*fy-2*y1*fy+y1*y1 = c1;
        //         fx*fx-2*x1*fx+x1*x1 + (h1*fx+i1)*(h1*fx+i1)-2*y1*(h1*fx+i1) = c1-y1*y1;
        //         fx*fx-2*x1*fx + (h1*fx+i1)*(h1*fx+i1)-2*y1*(h1*fx+i1) = c1-y1*y1-x1*x1;
        //         fx*fx-2*x1*fx + (h1*h1*fx*fx+2*h1*i1*fx+i1*i1)-2*y1*(h1*fx+i1) = c1-y1*y1-x1*x1;
        //         fx*fx-2*x1*fx + (h1*h1*fx*fx+2*h1*i1*fx+i1*i1)-2*y1*h1*fx-2*y1*i1 = c1-y1*y1-x1*x1;
        //         fx*fx-2*x1*fx + h1*h1*fx*fx+2*h1*i1*fx+i1*i1-2*y1*h1*fx-2*y1*i1 = c1-y1*y1-x1*x1;
        //         (h1*h1+1)fx*fx-2*x1*fx + 2*h1*i1*fx+i1*i1-2*y1*h1*fx-2*y1*i1 = c1-y1*y1-x1*x1;
        //         (h1*h1+1)fx*fx-2*x1*fx + 2*h1*i1*fx-2*y1*h1*fx-2*y1*i1 = c1-y1*y1-x1*x1-i1*i1;
        //         (h1*h1+1)fx*fx-2*x1*fx + 2*h1*i1*fx-2*y1*h1*fx = c1-y1*y1-x1*x1-i1*i1+2*y1*i1;
        //         (h1*h1+1)fx*fx + (2*h1*i1-2*x1-2*y1*h1)*fx = c1-y1*y1-x1*x1-i1*i1+2*y1*i1;
        //         (h1*h1+1)fx*fx + (2*h1*i1-2*x1-2*y1*h1)*fx -c1+y1*y1+x1*x1+i1*i1-2*y1*i1 = 0;

        double a = K*K+1;
        double b = 2*K*B-2*x1-2*y1*K;
        double c = -nLen*nLen+y1*y1+x1*x1+B*B-2*y1*B;
        double x =(-b+Math.sqrt(b*b-4*a*c))/(2*a);

        float fBig;
        float fSmall;
        if( x2>x1 ){
            fBig = x2;
            fSmall = x1;
        }
        else{
            fBig = x1;
            fSmall = x2;
        }
        if( x<=fSmall || x>=fBig ){
            x =(-b-Math.sqrt(b*b-4*a*c))/(2*a);
        }

        pt.x = (float)x;
        pt.y = (float)(K*x+B);

        return pt;
    }

    private void GetTrianglePoint(float x1, float y1, float x2, float y2, int nLen, int nVerticalLineLen, PointF pt1, PointF pt2)
    {
        PointF ptNear = GetCircleLineCrosspoint(x1, y1, x2, y2, nLen);
        if( x1==x2 ){
            pt1.x = ptNear.x-nVerticalLineLen;
            pt1.y = ptNear.y;
            pt2.x = ptNear.x+nVerticalLineLen;
            pt2.y = ptNear.y;
            return;
        }

        if( y1==y2 ){
            pt1.x = ptNear.x;
            pt1.y = ptNear.y-nVerticalLineLen;
            pt2.x = ptNear.x;
            pt2.y = ptNear.y+nVerticalLineLen;
            return;
        }

        //负倒数
        double K = (x1 - x2) / (y2 - y1);
        // y = K*x+B
        // B = -K*x+y

        double B = -K * ptNear.x + ptNear.y;

        x1 = ptNear.x;
        y1 = ptNear.y;

        double a = K*K+1;
        double b = 2*K*B-2*x1-2*y1*K;
        double c = -nVerticalLineLen*nVerticalLineLen+y1*y1+x1*x1+B*B-2*y1*B;

        pt1.x = (float)((-b+Math.sqrt(b*b-4*a*c))/(2*a));
        pt1.y = (float) (K*pt1.x+B);

        pt2.x = (float)((-b-Math.sqrt(b*b-4*a*c))/(2*a));
        pt2.y = (float) (K*pt2.x+B);
    }

    //读取配置文件
    public Properties loadConfig(Context context, String file) {
        Properties properties = new Properties();
        try {
            FileInputStream s = new FileInputStream(file);
            properties.load(s);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }

    public void DrawNineCircle()
    {
        Paint p = new Paint();
        p.setColor(Color.WHITE);// 设置红色
        p.setStyle(Paint.Style.STROKE);                   //空心效果
        p.setAntiAlias(true);                       //设置画笔为无锯齿
        p.setStrokeWidth((float) 2.0);              //线宽

        int nWidth = CScreenSize.GetWidth();
        for( int i=0; i<3; i++ ){
            for( int j=0; j<3; j++ ) {
                float x = (float)((j*2+1)*(nWidth/6.0));
                float y = (float)((i*2+1)*(nWidth/6.0));
                m_nPos[i][j].x = x;
                m_nPos[i][j].y = y;
                canvas.drawCircle(x, y, m_nRadius, p);
            }
        }
    }

    public interface GestureCallBack{

        /**
         * 代表用户绘制的密码与传入的密码相同
         */
        public abstract void checkedSuccess();
        /**
         * 代表用户绘制的密码与传入的密码不相同
         */
        public abstract void checkedFail();
    }
    //写数据
   /* public void writeFile(String fileName,String writestr) throws IOException{
        try{
            FileOutputStream fout =Context.openFileOutput(fileName, Context.MODE_PRIVATE);
            byte[] bytes = writestr.getBytes();
            fout.write(bytes);
            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }*/

    //读数据
    /*public String readFile(String fileName) throws IOException {
        String res="";
        try{
            FileInputStream fin = openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }*/

}
