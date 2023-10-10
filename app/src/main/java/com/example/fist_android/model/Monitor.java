package com.example.fist_android.model;

import com.google.gson.annotations.SerializedName;

public class Monitor {
    @SerializedName("monitorId")
    private String monitorId;
    @SerializedName("monitorName")
    private String monitorName;
    @SerializedName("monitorType")
    private int monitorType;
    @SerializedName("exerciseType")
    private String exerciseType;
    @SerializedName("layoutType")
    private int layoutType;

    public void setMonitorId(String monitorId){
        this.monitorId = monitorId;
    }
    public String getMonitorId(){
        return this.monitorId;
    }
    public void setMonitorName(String monitorName){
        this.monitorName = monitorName;
    }
    public String getMonitorName(){
        return this.monitorName;
    }
    public void setMonitorType(int monitorType){
        this.monitorType = monitorType;
    }
    public int getMonitorType(){
        return this.monitorType;
    }
    public void setExerciseType(String exerciseType){
        this.exerciseType = exerciseType;
    }
    public String getExerciseType(){
        return this.exerciseType;
    }
    public void setLayoutType(int layoutType){
        this.layoutType = layoutType;
    }
    public int getLayoutType(){
        return this.layoutType;
    }

    public void printMonitorData(){
        System.out.println("===========Monitor==============");
        System.out.println("MonitorId : " + this.monitorId);
        System.out.println("MonitorName : " + this.monitorName);
        System.out.println("MonitorType : " + this.monitorType);
        System.out.println("ExerciseType : " + this.exerciseType);
        System.out.println("LayoutType : " + this.layoutType);
        System.out.println("===============================");
    }
}
