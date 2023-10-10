package com.example.fist_android.repository;

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

    public boolean exerciseStart = false;
    public boolean exerciseTestStart = false;
    public boolean exerciseStop = false;
    public boolean exercisePause = false;


    public void createVideoView(){

    }
}
