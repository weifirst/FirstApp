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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListWnd extends AppCompatActivity {
    private ListView listView;

    public void Click(View v)
    {
          switch (v.getId()){
          case R.id.btnSave:
          {

                return;

          }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        setContentView(listView);
    }

    private List<String> getData(){
        List<String> data = new ArrayList<String>();

        SQLiteDatabase db = openOrCreateDatabase("p2p.db", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM Invest",null);
        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            String riqi = c.getString(c.getColumnIndex("DaoqiDate"));
            int nJinE = c.getInt(c.getColumnIndex("JinE"));
            String PingtaiName = c.getString(c.getColumnIndex("PingTaiName"));
            String msg=String.format( "%s % 5d %s", riqi,nJinE,PingtaiName);
            data.add(msg);
        }
        c.close();
        //关闭当前数据库
        db.close();

        return data;
    }
}
