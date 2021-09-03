package com.example.eungapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private HorizontalScrollView scrollView;
    private GridView gridView;
    List<SensorItem> sList;
    private ImageView scanButton;
    private IntentIntegrator qrScan;
    private SensorAdapter adapter;

    private TextView favorites;
    private TextView livingRoom;
    private TextView diningRoom;
    private TextView bedRoom1;
    private TextView bedRoom2;
    private TextView bedRoom3;
    private TextView activeMenu;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        gridView = view.findViewById(R.id.sensorGridView);
        scanButton = view.findViewById(R.id.scanButton);
        adapter = new SensorAdapter(getContext());
        qrScan = new IntentIntegrator(this.getActivity());

        favorites = (TextView) view.findViewById(R.id.favorites);
        livingRoom = (TextView) view.findViewById(R.id.living_room);
        diningRoom = (TextView) view.findViewById(R.id.dining_room);
        bedRoom1 = (TextView) view.findViewById(R.id.bed_room_1);
        bedRoom2 = (TextView) view.findViewById(R.id.bed_room_2);
        bedRoom3 = (TextView) view.findViewById(R.id.bed_room_3);
        activeMenu = favorites;

        Call<List<SensorItem>> getCall = MyAPICall.mMyAPI.get_sensors();
        getCall.enqueue(new Callback<List<SensorItem>>() {
            @Override
            public void onResponse(Call<List<SensorItem>> call, Response<List<SensorItem>> response) {
                if (response.isSuccessful()) {
                    sList = response.body();
                    for (SensorItem item: sList) { adapter.addItem(item); }
                    setGridView();
                }
                else {
                    Log.d("CryDetectionCall", "Status Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<SensorItem>> call, Throwable t) {
                Log.d("SensorCall", "Fail msg : " + t.getMessage());
            }
        });
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.setPrompt("QR코드를 화면에 맞춰주세요\n\n\n");
                qrScan.setBeepEnabled(false);
                qrScan.initiateScan();
            }
        });
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeMenu.setTextAppearance(R.style.homeScrollMenuItemDeactive);
                favorites.setTextAppearance(R.style.homeScrollMenuItemActive);
                activeMenu = favorites;
                adapter = new SensorAdapter(getContext());
                for (SensorItem item: sList) { adapter.addItem(item); }
                setGridView();
            }
        });
        livingRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeMenu.setTextAppearance(R.style.homeScrollMenuItemDeactive);
                livingRoom.setTextAppearance(R.style.homeScrollMenuItemActive);
                activeMenu = livingRoom;
                adapter = new SensorAdapter(getContext());
                for (SensorItem item: sList) {
                    if (item.getLocation().equals("거실")) { adapter.addItem(item); }
                }
                setGridView();
            }
        });
        diningRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeMenu.setTextAppearance(R.style.homeScrollMenuItemDeactive);
                diningRoom.setTextAppearance(R.style.homeScrollMenuItemActive);
                activeMenu = diningRoom;
                adapter = new SensorAdapter(getContext());
                for (SensorItem item: sList) {
                    if (item.getLocation().equals("부엌")) { adapter.addItem(item); }
                }
                setGridView();
            }
        });
        bedRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeMenu.setTextAppearance(R.style.homeScrollMenuItemDeactive);
                bedRoom1.setTextAppearance(R.style.homeScrollMenuItemActive);
                activeMenu = bedRoom1;
                adapter = new SensorAdapter(getContext());
                for (SensorItem item: sList) {
                    if (item.getLocation().equals("침실1")) { adapter.addItem(item); }
                }
                setGridView();
            }
        });
        bedRoom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeMenu.setTextAppearance(R.style.homeScrollMenuItemDeactive);
                bedRoom2.setTextAppearance(R.style.homeScrollMenuItemActive);
                activeMenu = bedRoom2;
                adapter = new SensorAdapter(getContext());
                for (SensorItem item: sList) {
                    if (item.getLocation().equals("침실2")) { adapter.addItem(item); }
                }
                setGridView();
            }
        });
        bedRoom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeMenu.setTextAppearance(R.style.homeScrollMenuItemDeactive);
                bedRoom3.setTextAppearance(R.style.homeScrollMenuItemActive);
                activeMenu = bedRoom3;
                adapter = new SensorAdapter(getContext());
                for (SensorItem item: sList) {
                    if (item.getLocation().equals("침실3")) { adapter.addItem(item); }
                }
                setGridView();
            }
        });
        return view;
    }
    void setGridView() {
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SensorDetailFragment fragment = new SensorDetailFragment();
                Bundle bundle = new Bundle();
                SensorItem sensorItem = (SensorItem) adapter.getItem(i);
                if (!(sensorItem.getModel().equals("기타") || sensorItem.getModel().equals("음성 센서"))) {
                    bundle.putString("model", sensorItem.getModel());
                    bundle.putString("location", sensorItem.getLocation());
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, fragment).commitAllowingStateLoss();
                }
            }
        });
    }
}