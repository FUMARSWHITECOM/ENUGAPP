package com.example.eungapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CryDetectionResultListView extends LinearLayout {
    TextView sensorName;
    ImageView sensorImage;
    TextView sensorValue;

    public CryDetectionResultListView(Context context) { super(context); init(context); }
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.crydetection_sensor_result_item, this, true);
        sensorName = (TextView) findViewById(R.id.result_sensor_status);
        sensorImage = (ImageView) findViewById(R.id.result_status_image);
        sensorValue = (TextView) findViewById(R.id.result_status_text);
    }

    public void setName(String name) { sensorName.setText(name); }
    public void setImage(int drawable) { sensorImage.setImageResource(drawable); }
    public void setValue(String value) { sensorValue.setText(value); }
}
