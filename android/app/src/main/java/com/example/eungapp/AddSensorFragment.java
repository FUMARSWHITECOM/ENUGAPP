package com.example.eungapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSensorFragment extends Fragment {
    static HashMap<String, String> locationHashKorToEng = new HashMap<String, String>();
    private RelativeLayout sensorLayout;
    private ImageView sensorImage;
    private TextView sensorName;
    private Spinner dropDownLocation;
    private Button addButton;

    static { initLocationHashKorToEng(); }
    public static void initLocationHashKorToEng() {
        locationHashKorToEng.put("거실", "LIVING_ROOM");
        locationHashKorToEng.put("부엌", "DINING_ROOM");
        locationHashKorToEng.put("침실1", "BED_ROOM1");
        locationHashKorToEng.put("침실2", "BED_ROOM2");
        locationHashKorToEng.put("침실3", "BED_ROOM3");
        locationHashKorToEng.put("기타", "ETC");
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String serialNumber = getArguments().getString("serial_number");
        String model = getArguments().getString("model");
        View view = inflater.inflate(R.layout.fragment_add_sensor, container, false);
        sensorLayout = (RelativeLayout) view.findViewById(R.id.sensor_layout);
        sensorImage = (ImageView) sensorLayout.findViewById(R.id.sensor_image);
        sensorName = (TextView) sensorLayout.findViewById(R.id.sensor_name);
        dropDownLocation = (Spinner) view.findViewById(R.id.dropdown_location);
        addButton = (Button) view.findViewById(R.id.addButton);

        sensorLayout.findViewById(R.id.sensor_location).setVisibility(View.INVISIBLE);

        if (model.equals("THERM")) {
            sensorImage.setImageResource(R.drawable.thermometer);
            sensorName.setText("모델: 온도 센서" + "\n시리얼 번호: " + serialNumber);}
        if (model.equals("HUMID")) {
            sensorImage.setImageResource(R.drawable.humidity);
            sensorName.setText("모델: 습도 센서" + "\n시리얼 번호: " + serialNumber);}
        if (model.equals("GAS")) {
            sensorImage.setImageResource(R.drawable.gas);
            sensorName.setText("모델: 가스 센서" + "\n시리얼 번호: " + serialNumber);}
        if (model.equals("AUX")) {
            sensorImage.setImageResource(R.drawable.microphone);
            sensorName.setText("모델: 음성 센서" + "\n시리얼 번호: " + serialNumber);}
        if (model.equals("ETC")) {
            sensorImage.setImageResource(R.drawable.basic_sensor);
            sensorName.setText("모델: 기타" + "\n시리얼 번호: " + serialNumber);}

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = locationHashKorToEng.get((String) dropDownLocation.getSelectedItem());
                int id = MainActivity.CURRENT_USER.getId();
                Call<SensorItem> sensorCall = MyAPICall.mMyAPI.sensor(serialNumber, location, id, true, model);
                sensorCall.enqueue(new Callback<SensorItem>() {
                    @Override
                    public void onResponse(Call<SensorItem> call, Response<SensorItem> response) {
                        if (response.isSuccessful()) {
                            Toast myToast = Toast.makeText(container.getContext(),"센서 추가 성공", Toast.LENGTH_SHORT);
                            myToast.show();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frameLayout, new HomeFragment()).commitAllowingStateLoss();
                        }
                        else {
                            Toast myToast = Toast.makeText(container.getContext(),"센서 추가 실패", Toast.LENGTH_SHORT);
                            myToast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SensorItem> call, Throwable t) {
                        Log.d("SignUp", "Fail msg : " + t.getMessage()); // 오류 메시지 출력
                    }
                });
            }
        });


        return view;
    }
}