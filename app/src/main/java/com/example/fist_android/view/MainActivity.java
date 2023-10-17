package com.example.fist_android.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fist_android.R;
import com.example.fist_android.common.LogManager;
import com.example.fist_android.databinding.ActivityMainBinding;
import com.example.fist_android.preference.PreferenceManager;
import com.example.fist_android.repository.CourseRepository;
import com.example.fist_android.repository.ExerciseRepository;
import com.example.fist_android.repository.MonitorRepository;
import com.example.fist_android.repository.OfficeRepository;
import com.example.fist_android.socket.SocketManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    OfficeRepository officeRepository = OfficeRepository.getInstance();
    MonitorRepository monitorRepository = MonitorRepository.getInstance();
    CourseRepository courseRepository = CourseRepository.getInstance();
    ExerciseRepository exerciseRepository = ExerciseRepository.getInstance();
    SocketManager socketManager = SocketManager.getInstance();
    Timer timer = new Timer();
    //=============================================================//
    TextView gudieText;
    //=============================================================//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TopBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Test
//        PreferenceManager.clear(getApplicationContext());

        //Logger
        LogManager logManager = LogManager.getInstance();

        /**
         * Preference Data 불러오기
         */
        officeRepository.getOfficeData(getApplicationContext());
        monitorRepository.getMonitorData(getApplicationContext());
//        officeRepository.printOfficeData();
//        monitorRepository.printMonitorData();

        /**
         * Socket Event 설정
         */
        socketManager.getSocket().on(Socket.EVENT_CONNECT, socketManager.onConnect);
        socketManager.getSocket().on("course", socketManager.getTodayCourse);
        socketManager.getSocket().on("start", socketManager.startExercise);
        socketManager.getSocket().on("teststart", socketManager.startTestExercise);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        officeRepository.printOfficeData();
        monitorRepository.printMonitorData();

        timer.schedule(timerTask, 0, 3000);
    }
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if(courseRepository.downloadTodayCouse){
                binding.guideText.setText("데이터 다운 완료...");
                if(exerciseRepository.exerciseStart || exerciseRepository.exerciseTestStart){
                    //화면전환
                    Logger.d("하 이년왜이래 : " + courseRepository.exerciseList.size());
                    Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
                    startActivity(intent);
                    finish();
                    timer.cancel();
                }
            }
        }
    };
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