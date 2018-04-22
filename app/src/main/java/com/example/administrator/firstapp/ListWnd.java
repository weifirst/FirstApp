package com.example.administrator.firstapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListWnd extends Activity implements AdapterView.OnItemLongClickListener/*.OnItemClickListener*/{
    private ListView listView;
    Map<Long,Integer> mymap = new ArrayMap<>();
    private long lWillDelID;
    private int nPosition;

    List<String> data = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        listView = new ListView(this);
        String[] strings = {"a","b","c"};

        final MyAdapter adapter=new MyAdapter(getApplicationContext(),strings);
        listView.setAdapter(adapter);
        //listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        listView.setOnItemLongClickListener(this);
        setContentView(listView);
    }

   /* private List<String> getData(){
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
    }*/

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
    //public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //通过view获取其内部的组件，进而进行操作
//        String text = (String) ((TextView)view.findViewById(R.id.text)).getText();
        //大多数情况下，position和id相同，并且都从0开始
  //      String showText = "点击第" + position + "项，文本内容为：" + text + "，ID为：" + id;

        lWillDelID = id;
        nPosition = position;
        new AlertDialog.Builder(ListWnd.this)
            .setIcon(R.drawable.twodog)
            .setTitle("确认")
            .setMessage("确认要删除这条数据吗？")
            .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    DelRecord(lWillDelID,nPosition);
                    dialog.dismiss();
                }
            })
            .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                   // finish();
                }
            })
            .show();
        return true;
    }

    void DelRecord(long id, int nPosition)
    {
        Long iid = new Long(id);
        String showText = "ID为" + id + "  数据库ID为" + mymap.get(iid);
        Toast.makeText(this, showText, Toast.LENGTH_LONG).show();

        SQLiteDatabase db = openOrCreateDatabase("p2p.db", Context.MODE_PRIVATE, null);
        String sSql = "DELETE FROM Invest WHERE _id=" + mymap.get(iid);
        // String.format(sSql, "DELETE FROM Invest WHERE _id=?", new Object[]{mymap.get(iid)});
        db.execSQL(sSql);
        db.close();

       // ListView listView = (ListView) parent;
        ListAdapter listAdapter = listView.getAdapter();
        ArrayAdapter arrayAdapter = (ArrayAdapter) listAdapter;
        arrayAdapter.remove(arrayAdapter.getItem(nPosition));
    }

    class MyAdapter extends BaseAdapter {
        private String[] data;
        private Context mContext;
        public MyAdapter(Context mContext, String[] data) {
            super();
            this.mContext = mContext;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override public Object getItem(int position) {
            return null;
        }

        @Override public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.list_item,null);
            final TextView textView = (TextView) view.findViewById(R.id.textView);
            Button button = (Button) view.findViewById(R.id.button);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setImageResource(R.mipmap.ic_launcher);
            button.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    textView.append("!");
                }
            });
            textView.setText(data[position]);
            return view;

           /* TextView textView = new TextView(mContext);
            textView.setText(data[position]);
            return textView;*/
        }
    }
}
