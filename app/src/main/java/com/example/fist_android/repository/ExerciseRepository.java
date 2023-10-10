package com.example.fist_android.repository;

import android.net.Uri;
import android.widget.VideoView;

import java.util.ArrayList;

public class ExerciseRepository {
    //=============================================================//
    //Signleton
    //=============================================================//
    private static ExerciseRepository exerciseInstance; // 싱글톤 인스턴스
    private ExerciseRepository() {}
    public static ExerciseRepository getInstance() {
        if (exerciseInstance == null) {
            exerciseInstance = new ExerciseRepository();
        }
        return exerciseInstance;
    }
    //=============================================================//
    CourseRepository courseRepository = CourseRepository.getInstance();

    public int todayExerciseLength;

    public boolean exerciseStart = false;
    public boolean exerciseTestStart = false;
    public boolean exerciseStop = false;
    public boolean exercisePause = false;

    //=============================================================//

}
