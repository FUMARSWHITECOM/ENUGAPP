package com.example.eungapp;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static UserItem CURRENT_USER = null;
    public static CryDetectionThread thread;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    private ListFragment listFragment = new ListFragment();
    private SignInFragment signInFragment = new SignInFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyAPICall.initMyAPI();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, signInFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.accountItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        thread = new CryDetectionThread(MainActivity.this);
        thread.start();
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId()) {
                case R.id.homeItem:
                    if (CURRENT_USER == null) {
                        Toast.makeText(MainActivity.this, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    transaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();
                    break;
                case R.id.listItem:
                    if (CURRENT_USER == null) {
                        Toast.makeText(MainActivity.this, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    transaction.replace(R.id.frameLayout, listFragment).commitAllowingStateLoss();
                    break;
                case R.id.accountItem:
                    transaction.replace(R.id.frameLayout, signInFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
    @Override
    @SuppressLint("MissingSuperCall")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() == null) {
            Toast.makeText(MainActivity.this, "스캔 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                JSONObject obj = new JSONObject(result.getContents());
                Bundle bundle = new Bundle();
                bundle.putString("serial_number", obj.getString("serial_number"));
                bundle.putString("model", obj.getString("model"));

                AddSensorFragment fragment = new AddSensorFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment).commitAllowingStateLoss();
            } catch (JSONException e) { e.printStackTrace(); }
        }
    }
}