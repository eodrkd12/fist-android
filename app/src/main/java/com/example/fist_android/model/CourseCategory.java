package com.example.fist_android.model;

import com.google.gson.annotations.SerializedName;

public class CourseCategory {
    @SerializedName("courseCategoryId")
    private String courseCategoryId;
    @SerializedName("exerciseType")
    private String exerciseType;
    @SerializedName("exerciseList")
    private ExerciseList[] exerciseList;
    @SerializedName("roundcaseList")
    private RoundCaseList[] roundcaseList;

    public String getCourseCategoryId() {
        return courseCategoryId;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public ExerciseList[] getExerciseList() {
        return exerciseList;
    }

    public RoundCaseList[] getRoundcaseList() {
        return roundcaseList;
    }

    public void setCourseCategoryId(String courseCategoryId) {
        this.courseCategoryId = courseCategoryId;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public void setExerciseList(ExerciseList[] exerciseList) {
        this.exerciseList = exerciseList;
    }

    public void setRoundcaseList(RoundCaseList[] roundcaseList) {
        this.roundcaseList = roundcaseList;
    }
}
