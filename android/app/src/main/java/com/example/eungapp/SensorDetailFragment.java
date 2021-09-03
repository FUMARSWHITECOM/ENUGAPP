package com.example.eungapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SensorDetailFragment extends Fragment {
    TextView sensorName;
    TextView sensorLocation;
    ImageView statusImage;
    TextView currentValue;
    TextView statusComment;

    TextView deviceName1;
    ImageView deviceImage1;
    TextView deviceName2;
    ImageView deviceImage2;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_detail, container, false);
        String model= getArguments().getString("model");
        String location = getArguments().getString("location");
        Log.d("CryDetectionCall", model + location);

        sensorName = (TextView) view.findViewById(R.id.crydetection_time);
        sensorLocation = (TextView) view.findViewById(R.id.crydetection_location);
        statusImage = (ImageView) view.findViewById(R.id.result_status_image);
        currentValue = (TextView) view.findViewById(R.id.result_status_text);
        statusComment = (TextView) view.findViewById(R.id.result_status_comment);

        deviceName1 = (TextView) view.findViewById(R.id.result_sensor_name1);
        deviceImage1 = (ImageView) view.findViewById(R.id.device_image_1);
        deviceName2 = (TextView) view.findViewById(R.id.device_name_2);
        deviceImage2 = (ImageView) view.findViewById(R.id.device_image_2);

        if (model.equals("온도 센서")) {
            sensorName.setText(model);
            sensorLocation.setText(location);
            statusImage.setImageResource(R.drawable.hot);
            currentValue.setText("온도: 30°C");
            statusComment.setText("다소 더울 수 있습니다.");

            deviceName1.setText("삼성 스마트 에어컨");
            deviceImage1.setImageResource(R.drawable.airconditioner);

            deviceName2.setText("XiaoMi 전동 커튼");
            deviceImage2.setImageResource(R.drawable.blinds);
        }
        if (model.equals("습도 센서")) {
            sensorName.setText(model);
            sensorLocation.setText(location);
            statusImage.setImageResource(R.drawable.humid);
            currentValue.setText("습도: 60%");
            statusComment.setText("날씨가 다소 습합니다.");

            deviceName1.setText("삼성 스마트 에어컨");
            deviceImage1.setImageResource(R.drawable.airconditioner);

            deviceName2.setText("위니아 제습기");
            deviceImage2.setImageResource(R.drawable.dehumidifier);
        }
        if (model.equals("가스 센서")) {
            sensorName.setText(model);
            sensorLocation.setText(location);
            statusImage.setImageResource(R.drawable.smell);
            currentValue.setText("냄새 감지됨");
            statusComment.setText("기저귀를 확인해주세요.");

            deviceName1.setText("LG 퓨리케어 공기청정기");
            deviceImage1.setImageResource(R.drawable.purifier);

            deviceName2.setText("XiaoMi 전동 커튼");
            deviceImage2.setImageResource(R.drawable.blinds);
        }

        return view;
    }
}