package com.example.fist_android.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.fist_android.R;
import com.example.fist_android.databinding.ActivityExerciseBinding;
import com.example.fist_android.repository.CourseRepository;
import com.example.fist_android.repository.ExerciseRepository;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ExerciseActivity extends AppCompatActivity {
    ActivityExerciseBinding binding;
    ExerciseRepository exerciseRepository = ExerciseRepository.getInstance();
    CourseRepository courseRepository = CourseRepository.getInstance();
    //=============================================================//
    private enum TimerStatus {
        STARTED,
        STOPPED
    }
    private int maxSeconds = 20 * 1000;
    private TimerStatus timerStatus = TimerStatus.STOPPED;
    private CountDownTimer countDownTimer;
    private ProgressBar progressBarCircle;
    private TextView progressText;
    private TextView remainGuideText;
    private TextView remainSetText;
    private RelativeLayout headerLeft;
    private LinearLayout firstLayout;
    private LinearLayout secondLayout;
    private LinearLayout thridLayout;
    private LinearLayout fourthLayout;
    private float screenHeightInDp;
    //=============================================================//
    float headerFontRate = 3.5f;
    float headerPaddingRate = 2f;
    //=============================================================//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_exercise);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exercise);
        //TopBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 현재 화면의 높이 가져오기
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeightInDp = displayMetrics.heightPixels / displayMetrics.density;

        //binding
        progressBarCircle = binding.timerProgressBar;
        progressText = binding.progressTextView;
        remainGuideText = binding.remainGuideText;
        remainSetText = binding.remainSetText;
        headerLeft = binding.headerLeft;

        firstLayout = binding.videoLayout0;
        secondLayout = binding.videoLayout1;
        thridLayout = binding.videoLayout2;
        fourthLayout = binding.videoLayout3;

        remainGuideText.setTextSize(headerFontRate * screenHeightInDp / 100);
        remainSetText.setTextSize(headerFontRate * screenHeightInDp / 100);
        headerLeft.setPadding(
                (int) (headerPaddingRate * screenHeightInDp / 100),
                (int) (headerPaddingRate * screenHeightInDp / 100),
                (int) (headerPaddingRate * screenHeightInDp / 100),
                (int) (headerPaddingRate * screenHeightInDp / 100));

//        startStop();

        courseRepository.sortExerciseVideo();

        setExerciseVideo();
    }


    //=============================================================//
    //Timer
    //=============================================================//
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
        countDownTimer = new CountDownTimer(maxSeconds, 100) {
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
                setProgressBarValues();

                Drawable newProgressDrawable = getResources().getDrawable(R.drawable.drawable_circle_outer_red);
                progressBarCircle.setProgressDrawable(newProgressDrawable);
                maxSeconds = 20 * 1000;

                timerStatus = TimerStatus.STARTED;
//                timerStatus = TimerStatus.STOPPED;
                startCountDownTimer();

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
    //=============================================================//

    //=============================================================//
    //Exercise Video
    //=============================================================//
    public void setExerciseVideo(){
        boolean[] nullCheck = new boolean[8];
        VideoView videoView[] = new VideoView[8];
        LinearLayout videoFrmae[] = new LinearLayout[8];
        int rowCheck = 0;

        videoView[0] = binding.videoView0;
        videoView[1] = binding.videoView1;
        videoView[2] = binding.videoView2;
        videoView[3] = binding.videoView3;
        videoView[4] = binding.videoView4;
        videoView[5] = binding.videoView5;
        videoView[6] = binding.videoView6;
        videoView[7] = binding.videoView7;

        videoFrmae[0] = binding.videoFrame0;
        videoFrmae[1] = binding.videoFrame1;
        videoFrmae[2] = binding.videoFrame2;
        videoFrmae[3] = binding.videoFrame3;
        videoFrmae[4] = binding.videoFrame4;
        videoFrmae[5] = binding.videoFrame5;
        videoFrmae[6] = binding.videoFrame6;
        videoFrmae[7] = binding.videoFrame7;


        for(int i = 0; i < 8; i++){
            if(courseRepository.exerciseList.get(i) != null){
                String exerciseDirName = courseRepository.exerciseList.get(i).getExercise().getExerciseDirName();
                String exerciseFileName = courseRepository.exerciseList.get(i).getExercise().getExerciseFileName();
                Uri videoUri= Uri.parse("http://211.107.110.77:3000/video/" + exerciseDirName + "/" + exerciseFileName);
                videoView[i].setVideoURI(videoUri);
                nullCheck[i] = false;
            }
            else{
//                videoView[i].setVisibility(View.GONE);
                videoFrmae[i].setVisibility(View.GONE);
                nullCheck[i] = true;
            }
            videoFrmae[i].setPadding(0, 0, 0, (int)(2f * screenHeightInDp / 100));
        }

        for(int i = 0; i < 8; i += 2){
            if(nullCheck[i] && nullCheck[i+1]){
                rowCheck++;
                switch (i){
                    case 0:
//                        firstLayout = binding.videoLayout0;
                        firstLayout.setVisibility(View.GONE);
                        break;
                    case 2:
//                        secondLayout = binding.videoLayout1;
                        secondLayout.setVisibility(View.GONE);
                        break;
                    case 4:
//                        thridLayout = binding.videoLayout2;
                        thridLayout.setVisibility(View.GONE);
                        break;
                    case 6:
//                        fourthLayout = binding.videoLayout3;
                        fourthLayout.setVisibility(View.GONE);
                        break;
                }
            }
        }

        if(rowCheck == 4){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.weight = 0.175f;
            firstLayout.setLayoutParams(params);
            secondLayout.setLayoutParams(params);
            thridLayout.setLayoutParams(params);
            fourthLayout.setLayoutParams(params);
        }
        else{
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.weight = 0.22f;
            firstLayout.setLayoutParams(params);
            secondLayout.setLayoutParams(params);
            thridLayout.setLayoutParams(params);
            fourthLayout.setLayoutParams(params);
        }

        for(int i = 0; i < 8; i++){
            final int currentIndex = i;
            videoView[i].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    videoView[currentIndex].seekTo(0);
                    videoView[currentIndex].start();
                    mediaPlayer.setLooping(true);
                }
            });
        }
    }

}