package com.example.eungapp;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class SensorAdapter extends BaseAdapter {
    private ArrayList<SensorItem> itemList = new ArrayList<SensorItem>();
    private Context context;

    public SensorAdapter(Context context) { this.context = context; }
    public void addItem(SensorItem item) { this.itemList.add(item); }
    @Override
    public int getCount() { return this.itemList.size(); }

    @Override
    public Object getItem(int i) { return this.itemList.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SensorListView itemView;
        if (view == null) { itemView = new SensorListView(context); }
        else itemView = (SensorListView) view;

        SensorItem item = itemList.get(i);

        if (item.getModel().equals("온도 센서")) { itemView.setImage(R.drawable.thermometer); }
        if (item.getModel().equals("습도 센서")) { itemView.setImage(R.drawable.humidity); }
        if (item.getModel().equals("가스 센서")) { itemView.setImage(R.drawable.gas); }
        if (item.getModel().equals("음성 센서")) { itemView.setImage(R.drawable.microphone); }
        if (item.getModel().equals("기타")) { itemView.setImage(R.drawable.basic_sensor); }

        itemView.setName(item.getModel());
        itemView.setLocation(item.getLocation());

        if (!item.getStatus()) { itemView.setDeactivateForeground(); }
        return itemView;
    }
}

