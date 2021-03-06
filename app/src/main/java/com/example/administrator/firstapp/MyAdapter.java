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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {
    private List<String> m_Data;
    private Context mContext;
    public MyAdapter(Context mContext, List<String> data) {
        super();
        this.mContext = mContext;
        this.m_Data = data;
    }

    @Override
    public int getCount() {
        return m_Data.size();
    }

    @Override public Object getItem(int position) {
        return null;
    }

    @Override public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
         LayoutInflater inflater = LayoutInflater.from(mContext);
         ViewHolder holder = null;
         if( convertView==null ){
             convertView = inflater.inflate(R.layout.list_item, null);
             holder = new ViewHolder();
             holder.button = (Button) convertView.findViewById(R.id.button);
             holder.textView = (TextView) convertView.findViewById(R.id.textView);
             holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
             convertView.setTag(holder);
         }
         else{
             holder = (ViewHolder)convertView.getTag();
         }
         holder.imageView.setImageResource(R.mipmap.ic_launcher);
         holder.button.setOnClickListener(new View.OnClickListener(){
             @Override public void onClick(View v){
                 mOnItemDeleteListener.onDeleteClick(i);
                 Log.d("click","button");
            }
         });

         String[] strArr = new String[m_Data.size()];
         m_Data.toArray(strArr);
         holder.textView.setText(strArr[i]);

         return convertView;
    }

    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }
}
