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
import com.example.fist_android.preference.PreferenceManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Logger.i("화면전환");
            Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
            startActivity(intent);
            timer.cancel();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));


//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        OfficeAPI officeAPI = new OfficeAPI();
        officeAPI.fetchOffice();
        timer.schedule(timerTask, 0, 3000);
    }

}