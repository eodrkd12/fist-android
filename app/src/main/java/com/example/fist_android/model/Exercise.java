package com.example.fist_android.model;

import com.google.gson.annotations.SerializedName;

public class Exercise {
    @SerializedName("exerciseId")
    private String exerciseId;
    @SerializedName("exerciseName")
    private String exerciseName;
    @SerializedName("exerciseFileName")
    private String exerciseFileName;
    @SerializedName("exerciseDirName")
    private String exerciseDirName;


    public String getExerciseId() {
        return exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getExerciseFileName() {
        return exerciseFileName;
    }

    public String getExerciseDirName() {
        return exerciseDirName;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setExerciseFileName(String exerciseFileName) {
        this.exerciseFileName = exerciseFileName;
    }

    public void setExerciseDirName(String exerciseDirName) {
        this.exerciseDirName = exerciseDirName;
    }
}
