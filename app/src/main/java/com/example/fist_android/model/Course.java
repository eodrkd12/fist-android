package com.example.fist_android.model;

import com.google.gson.annotations.SerializedName;

public class Course {
    @SerializedName("courseId")
    private String courseId;
    @SerializedName("courseDate")
    private String courseDate;
    @SerializedName("courseName")
    private String courseName;
    @SerializedName("courseImageUrl")
    private String courseImageUrl;
    @SerializedName("stationCount")
    private int stationCount;
    @SerializedName("stationMember")
    private int stationMember;
    @SerializedName("warmUpTime")
    private int warmUpTime;
    @SerializedName("coolDownTime")
    private int coolDownTime;
    @SerializedName("readyForWarmUpTime")
    private int readyForWarmUpTime;
    @SerializedName("readyForWeightTime")
    private int readyForWeightTime;
    @SerializedName("readyForHiitTime")
    private int readyForHiitTime;
    @SerializedName("readyForCoolDownTime")
    private int readyForCoolDownTime;
    @SerializedName("categoryList")
    private CourseCategory[] categoryList;

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseImageUrl(String courseImageUrl) {
        this.courseImageUrl = courseImageUrl;
    }

    public void setStationCount(int stationCount) {
        this.stationCount = stationCount;
    }

    public void setStationMember(int stationMember) {
        this.stationMember = stationMember;
    }

    public void setWarmUpTime(int warmUpTime) {
        this.warmUpTime = warmUpTime;
    }

    public void setCoolDownTime(int coolDownTime) {
        this.coolDownTime = coolDownTime;
    }

    public void setReadyForWarmUpTime(int readyForWarmUpTime) {
        this.readyForWarmUpTime = readyForWarmUpTime;
    }

    public void setReadyForWeightTime(int readyForWeightTime) {
        this.readyForWeightTime = readyForWeightTime;
    }

    public void setReadyForHiitTime(int readyForHiitTime) {
        this.readyForHiitTime = readyForHiitTime;
    }

    public void setReadyForCoolDownTime(int readyForCoolDownTime) {
        this.readyForCoolDownTime = readyForCoolDownTime;
    }

    public void setCategoryList(CourseCategory[] categoryList) {
        this.categoryList = categoryList;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseImageUrl() {
        return courseImageUrl;
    }

    public int getStationCount() {
        return stationCount;
    }

    public int getStationMember() {
        return stationMember;
    }

    public int getWarmUpTime() {
        return warmUpTime;
    }

    public int getCoolDownTime() {
        return coolDownTime;
    }

    public int getReadyForWarmUpTime() {
        return readyForWarmUpTime;
    }

    public int getReadyForWeightTime() {
        return readyForWeightTime;
    }

    public int getReadyForHiitTime() {
        return readyForHiitTime;
    }

    public int getReadyForCoolDownTime() {
        return readyForCoolDownTime;
    }

    public CourseCategory[] getCategoryList() {
        return categoryList;
    }
}
