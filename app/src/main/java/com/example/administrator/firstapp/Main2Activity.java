package com.example.administrator.firstapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private ListView listView;

    public void Click(View v)
    {
          switch (v.getId()){
          case R.id.btnSave:
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

              Toast.makeText(Main2Activity.this,msg,Toast.LENGTH_LONG).show();

              //关闭当前数据库
              db.close();
                return;


          }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        return;

       /* listView = new ListView(this);

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));

        setContentView(listView);*/
    }

    private List<String> getData(){
        List<String> data = new ArrayList<String>();

        data.add("测试数据1");

        data.add("测试数据2");

        data.add("测试数据3");

        data.add("测试数据4");

        data.add("测试数据5");

        data.add("测试数据6");

        data.add("测试数据7");

        data.add("测试数据8");

        data.add("测试数据9");

        data.add("测试数据10");

        data.add("测试数据11");

        data.add("测试数据12");

        data.add("测试数据13");

        data.add("测试数据14");

        data.add("测试数据15");

        data.add("测试数据16");

        return data;
    }
}
