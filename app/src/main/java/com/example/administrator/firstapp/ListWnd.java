package com.example.administrator.firstapp;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListWnd extends Activity implements AdapterView.OnItemClickListener{
    private ListView listView;
    Map<Long,Integer> mymap = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        listView.setOnItemClickListener(this);
        setContentView(listView);
    }

    private List<String> getData(){
        List<String> data = new ArrayList<String>();

        SQLiteDatabase db = openOrCreateDatabase("p2p.db", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM Invest",null);
        Long i=new Long(0);
        while (c.moveToNext()) {
            Integer id = c.getInt(c.getColumnIndex("_id"));
            String riqi = c.getString(c.getColumnIndex("DaoqiDate"));
            int nJinE = c.getInt(c.getColumnIndex("JinE"));
            String PingtaiName = c.getString(c.getColumnIndex("PingTaiName"));
            String msg=String.format( "%s % 5d %s", riqi,nJinE,PingtaiName);
            data.add(msg);
            mymap.put(i,id);
            i++;
        }
        c.close();
        //关闭当前数据库
        db.close();

        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //通过view获取其内部的组件，进而进行操作
//        String text = (String) ((TextView)view.findViewById(R.id.text)).getText();
        //大多数情况下，position和id相同，并且都从0开始
  //      String showText = "点击第" + position + "项，文本内容为：" + text + "，ID为：" + id;


        Long iid = new Long(id);
        String showText = "ID为"+id+"  数据库ID为"+mymap.get(iid);
        Toast.makeText(this, showText, Toast.LENGTH_LONG).show();

        SQLiteDatabase db = openOrCreateDatabase("p2p.db", Context.MODE_PRIVATE, null);
        String sSql="";
        String.format(sSql, "DELETE FROM Invest WHERE _id=?", new Object[]{mymap.get(iid)});
        db.execSQL(sSql);

        ListView listView = (ListView) parent;
        ListAdapter listAdapter = listView.getAdapter();
        ArrayAdapter arrayAdapter = (ArrayAdapter)listAdapter;
        arrayAdapter.remove( arrayAdapter.getItem(position) );
    }
}
