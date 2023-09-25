package com.example.fist_android.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.fist_android.R;
import com.example.fist_android.api.OfficeAPI;
import com.example.fist_android.databinding.ActivityMainBinding;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
//    MainViewModel mainViewModel;
    int testSecond = 0;
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            testSecond++;
            if(testSecond == 5){
                Log.d("화면전환","화면전환");
                Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
                startActivity(intent);
                timer.cancel();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        timer.schedule(timerTask, 0, 1000);

        OfficeAPI officeAPI = new OfficeAPI();
        officeAPI.fetchOffice();
    }

}