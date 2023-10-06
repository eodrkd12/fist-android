package com.example.fist_android.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

import com.example.fist_android.R;
import com.example.fist_android.databinding.ActivityExerciseBinding;

public class ExerciseActivity extends AppCompatActivity {

    private long maxSeconds = 10 * 1000;
    private enum TimerStatus {
        STARTED,
        STOPPED
    }
    private TimerStatus timerStatus = TimerStatus.STOPPED;
    private CountDownTimer countDownTimer;
    private ProgressBar progressBarCircle;
    ActivityExerciseBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_exercise);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exercise);
        progressBarCircle = binding.timerProgressBar;

        //TopBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        startStop();
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

//            imageViewReset.setVisibility(View.VISIBLE);
//            imageViewStartStop.setImageResource(R.drawable.ic_baseline_stop_circle_24);
//            editTextMinute.setEnabled(false);
            timerStatus = TimerStatus.STARTED;
            startCountDownTimer();

        } else {
//            imageViewReset.setVisibility(View.GONE);
//            imageViewStartStop.setImageResource(R.drawable.ic_baseline_play_circle_24);
//            editTextMinute.setEnabled(true);
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
//                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));
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
//        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
//        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);

        progressBarCircle.setMax((int) maxSeconds);
        progressBarCircle.setProgress((int) maxSeconds);
    }
}