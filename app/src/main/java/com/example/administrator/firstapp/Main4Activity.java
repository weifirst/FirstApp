package com.example.administrator.firstapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Main4Activity extends SuperActivity /*AppCompatActivity*/ {
    /** * 修改状态栏为全透明 * @param activity */
    public void Click(View v)
    {
        switch (v.getId())
        {
            case R.id.button:
            {
                SQLiteDatabase db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
                //创建person表
                db.execSQL("CREATE TABLE IF NOT EXISTS person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age SMALLINT)");

                String msg="";
                Cursor c = db.rawQuery("SELECT * FROM person WHERE age >= ?", new String[]{"10"});
                while (c.moveToNext()) {
                    int _id = c.getInt(c.getColumnIndex("_id"));
                    String name = c.getString(c.getColumnIndex("name"));
                    int age = c.getInt(c.getColumnIndex("age"));
                    Log.i("db", "_id=>" + _id + ", name=>" + name + ", age=>" + age);
                    msg += name;
                    msg += age;
                }
                c.close();
                if( msg.isEmpty() ){
                    db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{"李明", 28});
                }

                Toast.makeText(Main4Activity.this,msg,Toast.LENGTH_LONG).show();

                //关闭当前数据库
                db.close();

            }

                break;

        }
    }
    @TargetApi(19)
    public static void transparencyBar(Activity activity){
        return;
      /*  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window =activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        return;
      /*  Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;        // a|=b的意思就是把a和b按位或然后赋值给a   按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
        }
        else{
            winParams.flags &= ~bits;        //&是位运算里面，与运算  a&=b相当于 a = a&b  ~非运算符
        }
        win.setAttributes(winParams);*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        return;

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {            //系统版本大于19
            setTranslucentStatus(true);
        }
        transparencyBar(this);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.drawable.sky2);
        Class clazz = this.getWindow().getClass();
        try {
             int darkModeFlag = 0;
             Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
             Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
             darkModeFlag = field.getInt(layoutParams);
             Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
             if(true){
                 extraFlagField.invoke(this.getWindow(),darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
             }
             else{
                  extraFlagField.invoke(this.getWindow(), 0, darkModeFlag);//清除黑色字体
             }
        }
        catch (Exception e){
        }*/

    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序！", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else{
                Intent intent = new Intent();
                intent.setAction(SuperActivity.SYSTEM_EXIT);
                sendBroadcast(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
