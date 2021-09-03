package com.example.eungapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {
    private GridView gridView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        gridView = view.findViewById(R.id.gridView);
        CryDetectionAdapter adapter = new CryDetectionAdapter(getContext());

        Call<List<CryDetectionItem>> getCall = MyAPICall.mMyAPI.get_cryDetections();
        getCall.enqueue(new Callback<List<CryDetectionItem>>() {
            @Override
            public void onResponse(Call<List<CryDetectionItem>> call, Response<List<CryDetectionItem>> response) {
                if (response.isSuccessful()) {
                    List<CryDetectionItem> cList = response.body();
                    for (CryDetectionItem item: cList) {
                        //Log.d("CryDetectionCall", item.getSensorItem().getLocation());
                        adapter.addItem(item);
                    }
                    adapter.sort();
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            CryDetectionResultFragment fragment = new CryDetectionResultFragment();
                            Bundle bundle = new Bundle();
                            CryDetectionItem cryDetectionItem = (CryDetectionItem) adapter.getItem(i);
                            bundle.putString("location", cryDetectionItem.getSensorItem().getLocation());
                            bundle.putString("timestamp", cryDetectionItem.getTimestamp());
                            bundle.putString("serial_number", cryDetectionItem.getSensorItem().getSerialNumber());
                            bundle.putString("temperature", Double.toString(cryDetectionItem.getTemperature()));
                            bundle.putString("humidity", Double.toString(cryDetectionItem.getHumidity()));
                            bundle.putBoolean("smell", cryDetectionItem.getSmell());
                            bundle.putBoolean("checked", cryDetectionItem.getChecked());
                            fragment.setArguments(bundle);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frameLayout, fragment).commitAllowingStateLoss();
                        }
                    });
                }
                else {
                    Log.d("CryDetectionCall", "Status Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CryDetectionItem>> call, Throwable t) {
                Log.d("CryDetectionCall", "Fail msg : " + t.getMessage());
            }
        });
        return view;
    }
}