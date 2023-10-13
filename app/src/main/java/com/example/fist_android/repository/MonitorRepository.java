package com.example.fist_android.repository;

import android.content.Context;

import com.example.fist_android.common.Constant;
import com.example.fist_android.model.Monitor;
import com.example.fist_android.model.Office;
import com.example.fist_android.model.ResponseDTO;
import com.example.fist_android.preference.PreferenceManager;
import com.example.fist_android.retrofit.RetrofitAPI;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MonitorRepository {

    //=============================================================//
    //Signleton
    //=============================================================//
    private static MonitorRepository monitorInstance; // 싱글톤 인스턴스
    private MonitorRepository() {
    }
    public static MonitorRepository getInstance() {
        if (monitorInstance == null) {
            monitorInstance = new MonitorRepository();
        }
        return monitorInstance;
    }

    //=============================================================//
    public Monitor[] monitorList;
    public ArrayList<String> monitorNameList = new ArrayList<>(Arrays.asList("모니터를 선택해주세요."));

    public String monitorId;
    public String monitorName;
    public int monitorType;
    public String exerciseType;
    public int layoutType;

    //=============================================================//
    //Retrofit
    //=============================================================//
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.BE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
    public void fetchMonitor(String officeId, int offest, int limit, final MonitorFetchCallback callback){
        Logger.d("Monitor Fetch Start");
        retrofitAPI.getMonitorData(officeId, offest, limit).enqueue(new Callback<ResponseDTO<Monitor[]>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Monitor[]>> call, Response<ResponseDTO<Monitor[]>> response) {
                if(response.isSuccessful()){
                    ResponseDTO<Monitor[]> monitorData = response.body();
                    Monitor[] monitors = monitorData.getData();
                    if(monitors.length == 0){
                        monitorList = null;
                        monitorNameList.clear();
                    }
                    for(Monitor monitor: monitors){
//                        monitor.printMonitorData();
                        monitorList = monitors;
                        monitorNameList.add(monitor.getMonitorName());
                    }

                    // 응답을 처리한 후에 콜백을 호출하여 UI 업데이트를 요청합니다.
                    if (callback != null) {
                        callback.onMonitorFetchComplete();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Monitor[]>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        Logger.d("Monitor Fetch Done");
    }

    // Monitor 데이터를 가져온 후 UI를 업데이트하는 콜백 인터페이스
    public interface MonitorFetchCallback {
        void onMonitorFetchComplete();
    }

    //=============================================================//
    //SharedPreference
    //=============================================================//
    public void getMonitorData(Context context){
        monitorInstance.monitorId = PreferenceManager.getString(context, "monitorId");
        monitorInstance.monitorName = PreferenceManager.getString(context, "monitorName");
        monitorInstance.monitorType = PreferenceManager.getInt(context, "monitorType");
        monitorInstance.exerciseType = PreferenceManager.getString(context, "exerciseType");
        monitorInstance.layoutType = PreferenceManager.getInt(context, "layoutType");
    }
    public void saveMonitorData(Context context, int index){
        PreferenceManager.setString(context, "monitorId", monitorList[index].getMonitorId());
        PreferenceManager.setString(context, "monitorName", monitorList[index].getMonitorName());
        PreferenceManager.setInt(context, "monitorType", monitorList[index].getMonitorType());
        PreferenceManager.setString(context, "exerciseType", monitorList[index].getExerciseType());
        PreferenceManager.setInt(context, "layoutType", monitorList[index].getLayoutType());
    }
    public void saveStringMonitorData(Context context, String key, String value){
        PreferenceManager.setString(context, key, value);
    }
    public String getStringMonitorData(Context context, String key){
        return PreferenceManager.getString(context, key);
    }
    public void saveIntMonitorData(Context context, String key, int value){
        PreferenceManager.setInt(context, key, value);
    }
    public int getIntMonitorData(Context context, String key){
        return PreferenceManager.getInt(context, key);
    }
    //=============================================================//

    //=============================================================//
    //Print
    //=============================================================//
    public void printMonitorData(){
        Logger.i("MonitorId : " + monitorInstance.monitorId + "\n" +
                "MonitorName : " + monitorInstance.monitorName + "\n" +
                "MonitorType : " + monitorInstance.monitorType + "\n" +
                "ExerciseType : " + monitorInstance.exerciseType + "\n" +
                "LayoutType : " + monitorInstance.layoutType);
    }
}
