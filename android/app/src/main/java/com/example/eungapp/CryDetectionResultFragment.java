package com.example.eungapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryDetectionResultFragment extends Fragment {
    private ImageView statusImage;
    private TextView statusText;
    private TextView statusComment;

    private TextView locationView;
    private TextView timestampView;
    private Button alarmButton;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crydetection_result, container, false);
        String location = getArguments().getString("location");
        String timestamp = getArguments().getString("timestamp");
        String serial_number = getArguments().getString("serial_number");
        String temperature = getArguments().getString("temperature");
        String humidity = getArguments().getString("humidity");
        Boolean smell = getArguments().getBoolean("smell");
        Boolean checked = getArguments().getBoolean("checked");

        statusImage = (ImageView) view.findViewById(R.id.result_status_image);
        statusText = (TextView) view.findViewById(R.id.result_status_text);
        statusComment = (TextView) view.findViewById(R.id.result_status_comment);
        locationView = (TextView) view.findViewById(R.id.crydetection_location);
        timestampView = (TextView) view.findViewById(R.id.crydetection_time);
        alarmButton = (Button) view.findViewById(R.id.alarmButton);

        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.thread.stopVibrate();
                Call<CheckItem> getCall = MyAPICall.mMyAPI.check(serial_number, timestamp);
                getCall.enqueue(new Callback<CheckItem>() {
                    @Override
                    public void onResponse(Call<CheckItem> call, Response<CheckItem> response) {
                        if (response.isSuccessful()) {
                            CryDetectionResultFragment fragment = new CryDetectionResultFragment();
                            Bundle bundle = getArguments();
                            bundle.putBoolean("checked", true);
                            fragment.setArguments(getArguments());
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frameLayout, fragment).commitAllowingStateLoss();
                        } else {
                            Log.d("Check", "Status Code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckItem> call, Throwable t) {
                        Log.d("Check", "Fail msg : " + t.getMessage());
                    }
                });
            }
        });

        if (checked) {
            alarmButton.setVisibility(View.GONE);
            statusImage.setImageResource(R.drawable.checked);
            statusText.setText("확인된 알림");
            statusComment.setText("");
        }
        locationView.setText(location);

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String str = timestamp;
            str = str.replace('T', ' ');
            Log.d("date", str); // 오류 메시지 출력
            Date date = transFormat.parse(str.substring(0, 19));
            transFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
            String result = transFormat.format(date);
            timestampView.setText(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GridView gridView = view.findViewById(R.id.sensor_detail_list);
        CryDetectionResultAdapter adapter = new CryDetectionResultAdapter(getContext());

        Map<String, String> m1 = new HashMap<String, String>();
        m1.put("온도 센서", temperature + "°C");
        adapter.addItem(m1);

        Map<String, String> m2 = new HashMap<String, String>();
        m2.put("습도 센서", humidity + "%");
        adapter.addItem(m2);

        Map<String, String> m3 = new HashMap<String, String>();
        if (smell) m3.put("가스 센서", "냄새 감지");
        else m3.put("가스 센서", "냄새 감지 안됨");
        adapter.addItem(m3);

        gridView.setAdapter(adapter);
        return view;
    }
}