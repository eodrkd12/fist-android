package com.example.fist_android.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.GridLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.fist_android.R;
import com.example.fist_android.databinding.ActivityExerciseBinding;
import com.example.fist_android.repository.CourseRepository;
import com.example.fist_android.repository.ExerciseRepository;
import com.orhanobut.logger.Logger;

public class ExerciseActivity extends AppCompatActivity {
    ActivityExerciseBinding binding;
    ExerciseRepository exerciseRepository = ExerciseRepository.getInstance();
    CourseRepository courseRepository = CourseRepository.getInstance();
    //=============================================================//
    private enum TimerStatus {
        STARTED,
        STOPPED
    }
    private int maxSeconds = 30 * 1000;
    private TimerStatus timerStatus = TimerStatus.STOPPED;
    private CountDownTimer countDownTimer;
    private ProgressBar progressBarCircle;
    private TextView progressText;
    //=============================================================//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_exercise);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exercise);
        progressBarCircle = binding.timerProgressBar;
        progressText = binding.progressTextView;

        //TopBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        startStop();
        Uri videoUri= Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");

    }


    /**
     * 카운트 다운 시간을 리셋하고 재시작하는 기능
     */
    private void reset() {
        stopCountDownTimer();
        startCountDownTimer();
    }

    /**
     * 타이머가 시작하고 멈추는 기능
     */
    private void startStop() {

        if (timerStatus == TimerStatus.STOPPED) {
            setProgressBarValues();
            timerStatus = TimerStatus.STARTED;
            startCountDownTimer();
        } else {
            timerStatus = TimerStatus.STOPPED;
            stopCountDownTimer();
        }

    }

    /**
     * 카운트다운 시작 기능
     */
    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(maxSeconds, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (millisUntilFinished);
                if ((maxSeconds - millisUntilFinished) % 1000 > 0) {
                    maxSeconds = maxSeconds - 1000;
                    progressText.setText(String.valueOf(maxSeconds / 1000));
                }
                progressBarCircle.setProgress((int) (progress));
            }

            @Override
            public void onFinish() {
//                textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                setProgressBarValues();
                timerStatus = TimerStatus.STOPPED;
            }
        }.start();
        countDownTimer.start();
    }

    /**
     *  카운트 다운 정지 및 초기화
     */
    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    /**
     * 원형 프로그레스 바에 값 세팅
     */
    private void setProgressBarValues() {
        progressText.setText(String.valueOf(maxSeconds / 1000));

        progressBarCircle.setMax((int) maxSeconds);
        progressBarCircle.setProgress((int) maxSeconds);
    }
}