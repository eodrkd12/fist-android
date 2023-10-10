package com.example.fist_android.model;

import com.google.gson.annotations.SerializedName;

public class ExerciseList {
    @SerializedName("courseExerciseId")
    private String courseExerciseId;
    @SerializedName("exercise")
    private Exercise exercise;
    @SerializedName("rmWeight")
    private int rmWeight;
    @SerializedName("rfWeight")
    private int rfWeight;
    @SerializedName("ymWeight")
    private int ymWeight;
    @SerializedName("yfWeight")
    private int yfWeight;
    @SerializedName("wmWeight")
    private int wmWeight;
    @SerializedName("wfWeight")
    private int wfWeight;
    @SerializedName("exerciseRule")
    private String exerciseRule;
    @SerializedName("exerciseCount")
    private int exerciseCount;
    @SerializedName("startCount")
    private int startCount;
    @SerializedName("countGap")
    private int countGap;
    @SerializedName("countPeak")
    private int countPeak;
    @SerializedName("station")
    private int station;
    @SerializedName("monitor")
    private Monitor monitor;
    @SerializedName("displayOrder")
    private int displayOrder;


    public String getCourseExerciseId() {
        return courseExerciseId;
    }
    public Exercise getExercise() {
        return exercise;
    }

    public int getRmWeight() {
        return rmWeight;
    }

    public int getRfWeight() {
        return rfWeight;
    }

    public int getYmWeight() {
        return ymWeight;
    }

    public int getYfWeight() {
        return yfWeight;
    }

    public int getWmWeight() {
        return wmWeight;
    }

    public int getWfWeight() {
        return wfWeight;
    }

    public String getExerciseRule() {
        return exerciseRule;
    }

    public int getExerciseCount() {
        return exerciseCount;
    }

    public int getStartCount() {
        return startCount;
    }

    public int getCountGap() {
        return countGap;
    }

    public int getCountPeak() {
        return countPeak;
    }

    public int getStation() {
        return station;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setCourseExerciseId(String courseExerciseId) {
        this.courseExerciseId = courseExerciseId;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public void setRmWeight(int rmWeight) {
        this.rmWeight = rmWeight;
    }

    public void setRfWeight(int rfWeight) {
        this.rfWeight = rfWeight;
    }

    public void setYmWeight(int ymWeight) {
        this.ymWeight = ymWeight;
    }

    public void setYfWeight(int yfWeight) {
        this.yfWeight = yfWeight;
    }

    public void setWmWeight(int wmWeight) {
        this.wmWeight = wmWeight;
    }

    public void setWfWeight(int wfWeight) {
        this.wfWeight = wfWeight;
    }

    public void setExerciseRule(String exerciseRule) {
        this.exerciseRule = exerciseRule;
    }

    public void setExerciseCount(int exerciseCount) {
        this.exerciseCount = exerciseCount;
    }

    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }

    public void setCountGap(int countGap) {
        this.countGap = countGap;
    }

    public void setCountPeak(int countPeak) {
        this.countPeak = countPeak;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
