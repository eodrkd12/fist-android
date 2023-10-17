package com.example.fist_android.repository;

import android.net.Uri;
import android.widget.VideoView;

import com.example.fist_android.get.Observer;
import com.example.fist_android.get.Subject;

import java.util.ArrayList;

public class ExerciseRepository implements Subject {
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

    private final ArrayList<Observer> observerList = new ArrayList<>();

    public boolean exerciseStart = false;
    public boolean exerciseTestStart = false;
    public boolean exerciseStop = false;
    public boolean exercisePause = false;

    public int exerciseIndex = 0;

    //=============================================================//
    //Observer
    //=============================================================//
    public void setExercisePause(boolean isPaused) {
        exerciseInstance.exercisePause = isPaused;
        for(Observer o : observerList){
            o.onExercisePauseChanged(isPaused);
        }
        notifyObservers(exercisePause, false);
    }
    public void setExerciseStop(boolean isStoped){
        exerciseInstance.exerciseStop = isStoped;
        for(Observer o : observerList){
            o.onExerciseStopChanged(isStoped);
        }
        notifyObservers(false, exerciseStop);
    }

    @Override
    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers(boolean isPaused, boolean isStoped) {
        for(Observer o : observerList){
//            o.onExercisePauseChanged(isPaused);
//            o.onExerciseStopChanged(isStoped);
        }
    }

    //=============================================================//

}
