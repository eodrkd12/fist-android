package com.example.fist_android.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.fist_android.R;
import com.example.fist_android.databinding.ActivityMainBinding;
import com.example.fist_android.preference.PreferenceManager;
import com.example.fist_android.repository.CourseRepository;
import com.example.fist_android.repository.MonitorRepository;
import com.example.fist_android.repository.OfficeRepository;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    OfficeRepository officeRepository = OfficeRepository.getInstance();
    MonitorRepository monitorRepository = MonitorRepository.getInstance();
    CourseRepository courseRepository = CourseRepository.getInstance();
    ActivityMainBinding binding;
    Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TopBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Logger
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
//                .methodCount(0)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        //Test
//        PreferenceManager.clear(getApplicationContext());
        //Internal Data
        officeRepository.getOfficeData(getApplicationContext());
        monitorRepository.getMonitorData(getApplicationContext());
        officeRepository.printOfficeData();
        monitorRepository.printMonitorData();

//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        officeRepository.fetchOffice(new OfficeRepository.OfficeFetchCallback() {
            @Override
            public void onOfficeFetchComplete() {
                if(checkData()){
                    binding.guideText.setText("데이터 확인 완료...");

                    Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
                    startActivity(intent);
                    finish();
                    timer.cancel();
//                    courseRepository.fetchCourseData(officeRepository.officeId, "2023-09-19", new CourseRepository.CourseFetchCallback() {
//                        @Override
//                        public void onCourseFetchComplete() {
//                            courseRepository.printCourseData();
//                            binding.guideText.setText("데이터 다운 완료");
//                        }
//                    });
                }
                else{
                    Logger.i("화면전환");
                    Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
                    startActivity(intent);
                    finish();
                    timer.cancel();
                }
            }
        });
//        timer.schedule(timerTask, 3000);
    }
    @Override
    protected void onResume() {
        super.onResume();
        int monitorType = monitorRepository.getIntMonitorData(getApplicationContext(), "monitorType"); // 모니터 타입 가져오기

        if (monitorType == 1) {
            // 세로 모드 레이아웃 설정
            Logger.d("세로모드");
        } else if (monitorType == 2) {
            // 가로 모드 레이아웃 설정
            Logger.d("가로모드");
        }
    }

    private Boolean checkData(){
        String officeId = officeRepository.getStringOfficeData(getApplicationContext(), "officeId");
        String monitorId = monitorRepository.getStringMonitorData(getApplicationContext(), "monitorId");
        if(!officeId.isEmpty() && !monitorId.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
}