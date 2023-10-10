package com.example.fist_android.model;

import com.google.gson.annotations.SerializedName;

public class Roundcase {
    @SerializedName("roundcaseId")
    private String roundcaseId;
    @SerializedName("roundcaseNumber")
    private String roundcaseNumber;
    @SerializedName("roundcaseSet")
    private String roundcaseSet;
    @SerializedName("roundcaseExercise")
    private String roundcaseExercise;
    @SerializedName("roundcaseRest")
    private String roundcaseRest;
    @SerializedName("roundcaseMove")
    private String roundcaseMove;
    @SerializedName("roundcaseWater")
    private String roundcaseWater;

    public String getRoundcaseId() {
        return roundcaseId;
    }

    public String getRoundcaseNumber() {
        return roundcaseNumber;
    }

    public String getRoundcaseSet() {
        return roundcaseSet;
    }

    public String getRoundcaseExercise() {
        return roundcaseExercise;
    }

    public String getRoundcaseRest() {
        return roundcaseRest;
    }

    public String getRoundcaseMove() {
        return roundcaseMove;
    }

    public String getRoundcaseWater() {
        return roundcaseWater;
    }

    public void setRoundcaseId(String roundcaseId) {
        this.roundcaseId = roundcaseId;
    }

    public void setRoundcaseNumber(String roundcaseNumber) {
        this.roundcaseNumber = roundcaseNumber;
    }

    public void setRoundcaseSet(String roundcaseSet) {
        this.roundcaseSet = roundcaseSet;
    }

    public void setRoundcaseExercise(String roundcaseExercise) {
        this.roundcaseExercise = roundcaseExercise;
    }

    public void setRoundcaseRest(String roundcaseRest) {
        this.roundcaseRest = roundcaseRest;
    }

    public void setRoundcaseMove(String roundcaseMove) {
        this.roundcaseMove = roundcaseMove;
    }

    public void setRoundcaseWater(String roundcaseWater) {
        this.roundcaseWater = roundcaseWater;
    }
}
