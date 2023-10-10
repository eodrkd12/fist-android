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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
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
    private boolean previewFlag = true;
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
                if(previewFlag){
                    int progress = (int) (millisUntilFinished - 7000);
                }
                else{
                    int progress = (int) (millisUntilFinished);
                    if ((maxSeconds - millisUntilFinished) % 1000 > 0) {
                        maxSeconds = maxSeconds - 1000;
                        progressText.setText(String.valueOf(maxSeconds / 1000));
                    }
                    progressBarCircle.setProgress((int) (progress));
                }

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
        if(previewFlag){
            int previewStartTime = (int) maxSeconds - 7000;
            progressBarCircle.setProgress(previewStartTime);
        }else{
            progressBarCircle.setProgress((int) maxSeconds);
        }
    }
    //=============================================================//

    //=============================================================//
    //Exercise Video
    //=============================================================//
    public void setExerciseVideo(){
        boolean[] nullCheck = new boolean[8];
        VideoView videoView[] = new VideoView[8];
        FrameLayout videoFrmae[] = new FrameLayout[8];
        final CountDownLatch latch = new CountDownLatch(8);

        videoView[0] = binding.videoView0; videoFrmae[0] = binding.videoFrame0;
        videoView[1] = binding.videoView1; videoFrmae[1] = binding.videoFrame1;
        videoView[2] = binding.videoView2; videoFrmae[2] = binding.videoFrame2;
        videoView[3] = binding.videoView3; videoFrmae[3] = binding.videoFrame3;
        videoView[4] = binding.videoView4; videoFrmae[4] = binding.videoFrame4;
        videoView[5] = binding.videoView5; videoFrmae[5] = binding.videoFrame5;
        videoView[6] = binding.videoView6; videoFrmae[6] = binding.videoFrame6;
        videoView[7] = binding.videoView7; videoFrmae[7] = binding.videoFrame7;

        for(int i = 0; i < 8; i++){
            if(courseRepository.exerciseList.get(i) != null){
                String exerciseDirName = courseRepository.exerciseList.get(i).getExercise().getExerciseDirName();
                String exerciseFileName = courseRepository.exerciseList.get(i).getExercise().getExerciseFileName();
                Uri videoUri= Uri.parse("http://211.107.110.77:3000/video/" + exerciseDirName + "/" + exerciseFileName);
                videoView[i].setVideoURI(videoUri);
                nullCheck[i] = false;
            }
            else{
                videoView[i].setVisibility(View.GONE);
                videoFrmae[i].setVisibility(View.GONE);
                nullCheck[i] = true;
            }
        }

        for(int i = 0; i < 8; i += 2){
            if(nullCheck[i] && nullCheck[i+1]){
                switch (i){
                    case 0:
                        LinearLayout firstLayout = binding.videoLayout0;
                        firstLayout.setVisibility(View.GONE);
                        break;
                    case 2:
                        LinearLayout secondLayout = binding.videoLayout1;
                        secondLayout.setVisibility(View.GONE);
                        break;
                    case 4:
                        LinearLayout thridLayout = binding.videoLayout2;
                        thridLayout.setVisibility(View.GONE);
                        break;
                    case 6:
                        LinearLayout fourthLayout = binding.videoLayout3;
                        fourthLayout.setVisibility(View.GONE);
                        break;
                }
            }
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