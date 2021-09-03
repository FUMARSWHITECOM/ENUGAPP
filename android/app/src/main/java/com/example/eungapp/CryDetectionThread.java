package com.example.eungapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.util.Log;

import java.util.List;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryDetectionThread extends Thread{
    Context context;
    Vibrator vibrator;
    boolean isRun = true;
    private int CRYDETECTION_LENGTH = -1;

    public CryDetectionThread(Context context) {
        this.context = context;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void stopVibrate() { vibrator.cancel(); }

    public void run() {
        while (isRun) {
            if (MainActivity.CURRENT_USER != null) {
            //if (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Call<List<CryDetectionItem>> getCall = MyAPICall.mMyAPI.get_cryDetections();
                getCall.enqueue(new Callback<List<CryDetectionItem>>() {
                    @Override
                    public void onResponse(Call<List<CryDetectionItem>> call, Response<List<CryDetectionItem>> response) {
                        if (response.isSuccessful()) {
                            List<CryDetectionItem> cList = response.body();
                            int curr_length = cList.size();
                            if (CRYDETECTION_LENGTH == -1) {
                                CRYDETECTION_LENGTH = curr_length;
                            }

                            if (CRYDETECTION_LENGTH != curr_length) {
                                vibrator.vibrate(new long[]{100, 100}, 0);
                                //alert();
                                CRYDETECTION_LENGTH = curr_length;
                            }
                        } else {
                            Log.d("CryDetectionCall", "Status Code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CryDetectionItem>> call, Throwable t) {
                        Log.d("CryDetectionCall", "Fail msg : " + t.getMessage());
                    }
                });
            }
        }
    }

    private void alert() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle("Alert");
        alertBuilder.setMessage("Click");
        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                vibrator.cancel();
            }
        });
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
