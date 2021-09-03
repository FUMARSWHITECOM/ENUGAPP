package com.example.eungapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

public class SensorListView extends GridLayout {
    ImageView imageView;
    TextView nameView;
    TextView locationView;
    Context context;

    public SensorListView(Context context) {
        super(context);
        this.context = context;
        init(context);
    }
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sensor_item, this, true);
        imageView = (ImageView) findViewById(R.id.sensor_image);
        nameView = (TextView) findViewById(R.id.sensor_name);
        locationView = (TextView) findViewById(R.id.sensor_location);
    }

    public void setImage(int drawable) { imageView.setImageResource(drawable); }
    public void setName(String name) { nameView.setText(name); }
    public void setLocation(String location) { locationView.setText(location); }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setDeactivateForeground() {
        findViewById(R.id.sensor_item_layout).setForeground(ContextCompat.getDrawable(context, R.drawable.sensor_deactivate_overlay));
    }
}
