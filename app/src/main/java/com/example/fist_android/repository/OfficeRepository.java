package com.example.fist_android.repository;

import android.content.Context;

import com.example.fist_android.model.Office;
import com.example.fist_android.preference.PreferenceManager;

import java.util.ArrayList;

public class OfficeRepository {
    //=============================================================//
    //Singleton
    //=============================================================//
    private static OfficeRepository officeInstance; // 싱글톤 인스턴스
    private OfficeRepository() {
    }

    public static OfficeRepository getInstance() {
        if (officeInstance == null) {
            officeInstance = new OfficeRepository();
        }
        return officeInstance;
    }
    //=============================================================//
    public Office[] officeList;
    public ArrayList<String> officeNameList = new ArrayList<>();



    public void saveOfficeData(Context context, String key, String value){
        PreferenceManager.setString(context, key, value);
    }
    public String getOfficeData(Context context, String key){
        return PreferenceManager.getString(context, key);
    }
}
