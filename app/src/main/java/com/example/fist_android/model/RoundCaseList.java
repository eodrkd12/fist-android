package com.example.fist_android.model;

import com.google.gson.annotations.SerializedName;

public class RoundCaseList {
    @SerializedName("categoryRoundcaseId")
    private String categoryRoundcaseId;
    @SerializedName("roundcase")
    private Roundcase roundcase;
    @SerializedName("waterTime")
    private int waterTime;

    public String getCategoryRoundcaseId() {
        return categoryRoundcaseId;
    }

    public Roundcase getRoundcase() {
        return roundcase;
    }

    public int getWaterTime() {
        return waterTime;
    }

    public void setCategoryRoundcaseId(String categoryRoundcaseId) {
        this.categoryRoundcaseId = categoryRoundcaseId;
    }

    public void setRoundcase(Roundcase roundcase) {
        this.roundcase = roundcase;
    }

    public void setWaterTime(int waterTime) {
        this.waterTime = waterTime;
    }
}
