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

public class AddRecordWnd extends AppCompatActivity {
    private ListView listView;

    public void Click(View v)
    {
        switch (v.getId()){
            case R.id.btnSave:
            {
                SQLiteDatabase db = openOrCreateDatabase("p2p.db", Context.MODE_PRIVATE, null);
                //创建person表
                db.execSQL("CREATE TABLE IF NOT EXISTS Invest (_id INTEGER PRIMARY KEY AUTOINCREMENT, DaoqiDate INTEGER, JinE INTEGER, PingTaiName VARCHAR)");

                final EditText EditDaoqiDate = (EditText)findViewById(R.id.editDaoQi);
                final EditText EditJine = (EditText)findViewById(R.id.editJinE);
                final EditText EditPingtaiName = (EditText)findViewById(R.id.editPingtaiName);

                int nDaoqiDate = Integer.valueOf(EditDaoqiDate.getText().toString());
                int nJinE = Integer.valueOf(EditJine.getText().toString());
                String sPingtaiName = EditPingtaiName.getText().toString();

                db.execSQL("INSERT INTO Invest VALUES (NULL, ?, ?, ?)", new Object[]{nDaoqiDate, nJinE, sPingtaiName});

                String msg="";
                Cursor c = db.rawQuery("SELECT * FROM Invest",null);
                while (c.moveToNext()) {
                    int _id = c.getInt(c.getColumnIndex("_id"));
                    String name = c.getString(c.getColumnIndex("DaoqiDate"));
                    int age = c.getInt(c.getColumnIndex("JinE"));
                    Log.i("db", "_id=>" + _id + ", name=>" + name + ", age=>" + age);
                    msg += name;
                    msg += age;
                }
                c.close();
              /*if( msg.isEmpty() ){
                  db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{"李明", 28});
              }*/

                Toast.makeText(AddRecordWnd.this,msg,Toast.LENGTH_LONG).show();
                // final EditText ledEdit = (EditText)findViewById(R.id.editJinE);
                //Toast.makeText(Main2Activity.this, ledEdit.getText().toString(), Toast.LENGTH_LONG).show();

                //关闭当前数据库
                db.close();

                finish();
                return;
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main21);
        return;

        /*listView = new ListView(this);

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
