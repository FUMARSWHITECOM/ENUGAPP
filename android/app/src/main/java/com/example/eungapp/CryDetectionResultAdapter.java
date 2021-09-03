package com.example.eungapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class CryDetectionResultAdapter extends BaseAdapter {
    private ArrayList<Map<String, String>> itemList = new ArrayList<Map<String, String>>();
    private Context context;

    public CryDetectionResultAdapter(Context context) { this.context = context; }
    public void addItem(Map<String, String> item) { this.itemList.add(item); }
    @Override
    public int getCount() { return this.itemList.size(); }

    @Override
    public Object getItem(int i) { return this.itemList.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CryDetectionResultListView itemView;
        if (view == null) { itemView = new CryDetectionResultListView(context); }
        else itemView = (CryDetectionResultListView) view;

        Map<String, String> item = itemList.get(i);

        for (String name: item.keySet()) {
            itemView.setName(name);
            itemView.setValue(item.get(name));
            if (name.equals("온도 센서")) { itemView.setImage(R.drawable.thermometer); }
            if (name.equals("습도 센서")) { itemView.setImage(R.drawable.humidity); }
            if (name.equals("가스 센서")) { itemView.setImage(R.drawable.gas); }
            if (name.equals("음성 센서")) { itemView.setImage(R.drawable.microphone); }
            if (name.equals("기타")) { itemView.setImage(R.drawable.basic_sensor); }
        }
        return itemView;
    }
}
