package com.example.eungapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class CryDetectionAdapter extends BaseAdapter {
    private ArrayList<CryDetectionItem> itemList = new ArrayList<CryDetectionItem>();
    private Context context;

    public CryDetectionAdapter(Context context) { this.context = context; }
    public void addItem(CryDetectionItem item) { this.itemList.add(item); }
    @Override
    public int getCount() { return this.itemList.size(); }

    @Override
    public Object getItem(int i) { return this.itemList.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CryDetectionListView itemView;
        if (view == null) { itemView = new CryDetectionListView(context); }
        else itemView = (CryDetectionListView) view;

        CryDetectionItem item = itemList.get(i);

        if (item.getSensorItem().getLocation().equals("거실")) {
            itemView.setImage(R.drawable.living_room);
        }
        if (item.getSensorItem().getLocation().equals("침실1")) {
            itemView.setImage(R.drawable.bed_room);
        }

        itemView.setLocation(item.getSensorItem().getLocation());

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String str = item.getTimestamp();
            str = str.replace('T', ' ');
            Date date = transFormat.parse(str.substring(0, 19));
            transFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
            String result = transFormat.format(date);
            itemView.setTimestamp(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (item.getChecked()) {
            itemView.blind();
        }
        return itemView;
    }

    public void sort() {
        Collections.sort(itemList, new Descending());
    }
}

class Descending implements Comparator<CryDetectionItem> {
    @Override
    public int compare(CryDetectionItem s, CryDetectionItem t1) {
        return t1.getTimestamp().compareTo(s.getTimestamp());
    }
}
