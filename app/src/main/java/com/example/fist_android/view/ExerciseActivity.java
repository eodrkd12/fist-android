package com.example.fist_android.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.fist_android.R;
import com.example.fist_android.databinding.ActivityExerciseBinding;
import com.example.fist_android.repository.CourseRepository;
import com.example.fist_android.repository.ExerciseRepository;

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
    private LinearLayout thirdLayout;
    private LinearLayout fourthLayout;

    private float realWidthInDp;
    private float realHeightInDp;
    //=============================================================//
    float headerFontRate = 3.5f;
    float headerPaddingRate = 2f;
    float stationFontRate = 3.5f;
    float countFontRate = 2.5f;
    //=============================================================//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_exercise);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exercise);

        /**
         * activity 사전 처리
         * 앱 Topbar 제거
         */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        /**
         * Device의 실제 Height값을 DP단위로 가져와서 저장
         */
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        realWidthInDp = displayMetrics.widthPixels / displayMetrics.density;
        realHeightInDp = displayMetrics.heightPixels / displayMetrics.density;

        //binding
        progressBarCircle = binding.timerProgressBar;
        progressText = binding.progressTextView;
        remainGuideText = binding.remainGuideText;
        remainSetText = binding.remainSetText;
        headerLeft = binding.headerLeft;


        //video row 1~4
        firstLayout = binding.videoLayout0;
        secondLayout = binding.videoLayout1;
        thirdLayout = binding.videoLayout2;
        fourthLayout = binding.videoLayout3;

        remainGuideText.setTextSize(headerFontRate * realHeightInDp / 100);
        remainSetText.setTextSize(headerFontRate * realHeightInDp / 100);
        progressText.setTextSize(headerFontRate * realHeightInDp / 100);
        headerLeft.setPadding(
                (int) (headerPaddingRate * realHeightInDp / 100),
                (int) (headerPaddingRate * realHeightInDp / 100),
                (int) (headerPaddingRate * realHeightInDp / 100),
                (int) (headerPaddingRate * realHeightInDp / 100));

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
        LinearLayout videoFrame[] = new LinearLayout[8];
        int rowCheck = 0;

        videoView[0] = binding.videoView0;
        videoView[1] = binding.videoView1;
        videoView[2] = binding.videoView2;
        videoView[3] = binding.videoView3;
        videoView[4] = binding.videoView4;
        videoView[5] = binding.videoView5;
        videoView[6] = binding.videoView6;
        videoView[7] = binding.videoView7;

        videoFrame[0] = binding.videoFrame0;
        videoFrame[1] = binding.videoFrame1;
        videoFrame[2] = binding.videoFrame2;
        videoFrame[3] = binding.videoFrame3;
        videoFrame[4] = binding.videoFrame4;
        videoFrame[5] = binding.videoFrame5;
        videoFrame[6] = binding.videoFrame6;
        videoFrame[7] = binding.videoFrame7;


        for(int i = 0; i < 8; i++){
            if(courseRepository.exerciseList.get(i) != null){
                String exerciseDirName = courseRepository.exerciseList.get(i).getExercise().getExerciseDirName();
                String exerciseFileName = courseRepository.exerciseList.get(i).getExercise().getExerciseFileName();
//                Uri videoUri= Uri.parse(Constant.BE_URL + "/video/" + exerciseDirName + "/" + exerciseFileName);
                Uri videoUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
                videoView[i].setVideoURI(videoUri);
                nullCheck[i] = false;
            }
            else{
                videoFrame[i].setVisibility(View.GONE);
                nullCheck[i] = true;
            }
        }

        LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT
        );
        leftParams.weight=1;
        leftParams.setMargins(0,0,(int)(1f * realHeightInDp / 100),0);
        LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT
        );
        rightParams.weight=1;
//        leftParams.setMargins((int)(0.5f * realHeightInDp / 100),0,0,0);

        if(!nullCheck[0] && !nullCheck[1]){
            videoFrame[0].setLayoutParams(leftParams);
            videoFrame[1].setLayoutParams(rightParams);
        }
        if(!nullCheck[2] && !nullCheck[3]){
            videoFrame[2].setLayoutParams(leftParams);
            videoFrame[3].setLayoutParams(rightParams);

        }
        if(!nullCheck[4] && !nullCheck[5]){
            videoFrame[4].setLayoutParams(leftParams);
            videoFrame[5].setLayoutParams(rightParams);
        }
        if(!nullCheck[6] && !nullCheck[7]){
            videoFrame[6].setLayoutParams(leftParams);
            videoFrame[7].setLayoutParams(rightParams);
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
                        thirdLayout.setVisibility(View.GONE);
                        break;
                    case 6:
//                        fourthLayout = binding.videoLayout3;
                        fourthLayout.setVisibility(View.GONE);
                        break;
                }
            }
        }

        float weight = rowCheck == 4 ? 0.175f : 0.22f;

        LinearLayout.LayoutParams firstParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) ((weight * 100) * realHeightInDp / 100)
        );
        firstParams.weight = weight;
        LinearLayout.LayoutParams secondParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) ((weight * 100) * realHeightInDp / 100)
        );
        secondParams.weight = weight;
        LinearLayout.LayoutParams thirdParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) ((weight * 100) * realHeightInDp / 100)
        );
        thirdParams.weight = weight;
        LinearLayout.LayoutParams fourthParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) ((weight * 100) * realHeightInDp / 100)
        );
        fourthParams.weight = weight;


        if(!nullCheck[0] || !nullCheck[1]){
            if((!nullCheck[2] || !nullCheck[3])
            || (!nullCheck[4] || !nullCheck[5])
            || (!nullCheck[6] || !nullCheck[7])){
                firstParams.setMargins((int)(1f * realHeightInDp / 100),0,(int)(1f * realHeightInDp / 100),(int)(2f * realHeightInDp / 100));
            }
        }
        if((!nullCheck[2] || !nullCheck[3])) {
            if((!nullCheck[4] || !nullCheck[5])|| (!nullCheck[6] || !nullCheck[7])){
                secondParams.setMargins((int)(1f * realHeightInDp / 100),0,(int)(1f * realHeightInDp / 100),(int)(2f * realHeightInDp / 100));
            }
        }
        if((!nullCheck[4] || !nullCheck[5])){
            if((!nullCheck[6] || !nullCheck[7])){
                thirdParams.setMargins((int)(1f * realHeightInDp / 100),0,(int)(1f * realHeightInDp / 100),(int)(2f * realHeightInDp / 100));
            }
        }
        fourthParams.setMargins((int)(1f * realHeightInDp / 100),0,(int)(1f * realHeightInDp / 100),0);
        firstLayout.setLayoutParams(firstParams);
        secondLayout.setLayoutParams(secondParams);
        thirdLayout.setLayoutParams(thirdParams);
        fourthLayout.setLayoutParams(fourthParams);

        setStationText();

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

    public void setStationText(){
        TextView stationText[] = new TextView[8];
        TextView countText[] = new TextView[8];
        stationText[0] = binding.videoStation0; countText[0] = binding.videoCount0;
        stationText[1] = binding.videoStation1; countText[1] = binding.videoCount1;
        stationText[2] = binding.videoStation2; countText[2] = binding.videoCount2;
        stationText[3] = binding.videoStation3; countText[3] = binding.videoCount3;
        stationText[4] = binding.videoStation4; countText[4] = binding.videoCount4;
        stationText[5] = binding.videoStation5; countText[5] = binding.videoCount5;
        stationText[6] = binding.videoStation6; countText[6] = binding.videoCount6;
        stationText[7] = binding.videoStation7; countText[7] = binding.videoCount7;

        FrameLayout.LayoutParams stationParams = (FrameLayout.LayoutParams) binding.videoStation0.getLayoutParams();
        FrameLayout.LayoutParams countParams = (FrameLayout.LayoutParams) binding.videoCount0.getLayoutParams();

        stationParams.setMargins((int)(1.5f * realHeightInDp / 100), (int)(1.5f * realHeightInDp / 100), 0, 0);
        countParams.setMargins(0,(int)(1.5f * realHeightInDp / 100),(int)(1.5f * realHeightInDp / 100),0);
        for(int i = 0; i < 8; i++){
            stationText[i].setTextSize(stationFontRate * realHeightInDp / 100);
            stationText[i].setLayoutParams(stationParams);

            countText[i].setTextSize(countFontRate * realHeightInDp / 100);
            countText[i].setLayoutParams(countParams);
        }

    }

}