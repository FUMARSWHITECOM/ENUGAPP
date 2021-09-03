package com.example.eungapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class CryDetectionListView extends LinearLayout {
    ImageView imageView;
    TextView locationView;
    TextView timestampView;
    Context context;

    public CryDetectionListView(Context context) { super(context); init(context); this.context = context; }
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.crydetection_item, this, true);
        imageView = (ImageView) findViewById(R.id.location_icon);
        locationView = (TextView) findViewById(R.id.location_text);
        timestampView = (TextView) findViewById(R.id.timestamp_text);
    }

    public void setImage(int drawable) { imageView.setImageResource(drawable); }
    public void setLocation(String location) { locationView.setText(location); }
    public void setTimestamp(String timestamp) { timestampView.setText(timestamp); }
    public void blind() {
        RelativeLayout t = (RelativeLayout) findViewById(R.id.temp);
        t.setForeground(ContextCompat.getDrawable(context, R.drawable.sensor_deactivate_overlay));
    }
}
