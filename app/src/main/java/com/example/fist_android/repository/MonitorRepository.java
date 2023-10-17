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
    public ArrayList<Monitor> monitorList = new ArrayList<>();
    public ArrayList<Monitor> horizontalMonitorList = new ArrayList<>();
    public ArrayList<String> monitorNameList = new ArrayList<>(Arrays.asList("모니터를 선택해주세요."));
    public ArrayList<String> horizontalMonitorNameList = new ArrayList<>(Arrays.asList("모니터를 선택해주세요."));

    /**
     * 세션 1 모니터
     */
    public String monitorId;
    public String monitorName;
    public int monitorType;
    public String exerciseType;
    public int layoutType;
    /**
     * 세션 2 모니터
     */
    public String secondMonitorId;
    public String secondMoniterName;
    public int secondMonitorType;
    public String secondExerciseType;
    public int secondLayoutType;
    /**
     * 가로 모니터
     */
    public String horizontalMonitorName;
    public String horizontalMonitorId;
    public int horizontalMonitorType;
    public String horizontalExerciseType;
    public int horizontalLayoutType;



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
                        if(monitor.getMonitorType() == 1){
                            monitorList.add(monitor);
                            monitorNameList.add(monitor.getMonitorName());
                        }
                        else{
                            horizontalMonitorList.add(monitor);
                            horizontalMonitorNameList.add(monitor.getMonitorName());
                        }
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

        monitorInstance.secondMonitorId = PreferenceManager.getString(context, "secondMonitorId");
        monitorInstance.secondMoniterName = PreferenceManager.getString(context, "secondMonitorName");
        monitorInstance.secondMonitorType = PreferenceManager.getInt(context, "secondMonitorType");
        monitorInstance.secondExerciseType = PreferenceManager.getString(context, "secondExerciseType");
        monitorInstance.secondLayoutType = PreferenceManager.getInt(context, "secondLayoutType");

        monitorInstance.horizontalMonitorId = PreferenceManager.getString(context, "horizontalMonitorId");
        monitorInstance.horizontalMonitorName = PreferenceManager.getString(context, "horizontalMonitorName");
        monitorInstance.horizontalMonitorType = PreferenceManager.getInt(context, "horizontalMonitorType");
        monitorInstance.horizontalExerciseType = PreferenceManager.getString(context, "horizontalExerciseType");
        monitorInstance.horizontalLayoutType = PreferenceManager.getInt(context, "horizontalLayoutType");
    }
    public void saveMonitorData(Context context, int index, int secondIndex, int horizontalIndex){
        PreferenceManager.setString(context, "monitorId", monitorList.get(index).getMonitorId());
        PreferenceManager.setString(context, "monitorName", monitorList.get(index).getMonitorName());
        PreferenceManager.setInt(context, "monitorType", monitorList.get(index).getMonitorType());
        PreferenceManager.setString(context, "exerciseType", monitorList.get(index).getExerciseType());
        PreferenceManager.setInt(context, "layoutType", monitorList.get(index).getLayoutType());

        if(secondIndex != -1){
            PreferenceManager.setString(context, "secondMonitorId", monitorList.get(secondIndex).getMonitorId());
            PreferenceManager.setString(context, "secondMonitorName", monitorList.get(secondIndex).getMonitorName());
            PreferenceManager.setInt(context, "secondMonitorType", monitorList.get(secondIndex).getMonitorType());
            PreferenceManager.setString(context, "secondExerciseType", monitorList.get(secondIndex).getExerciseType());
            PreferenceManager.setInt(context, "secondLayoutType", monitorList.get(secondIndex).getLayoutType());
        }
        else{
            PreferenceManager.setString(context, "secondMonitorId", "");
            PreferenceManager.setString(context, "secondMonitorName", "");
            PreferenceManager.setInt(context, "secondMonitorType", -1);
            PreferenceManager.setString(context, "secondExerciseType", "");
            PreferenceManager.setInt(context, "secondLayoutType", -1);
        }

        if(horizontalIndex != -1){
            PreferenceManager.setString(context, "horizontalMonitorId", horizontalMonitorList.get(horizontalIndex).getMonitorId());
            PreferenceManager.setString(context, "horizontalMonitorName", horizontalMonitorList.get(horizontalIndex).getMonitorName());
            PreferenceManager.setInt(context, "horizontalMonitorType", horizontalMonitorList.get(horizontalIndex).getMonitorType());
            PreferenceManager.setString(context, "horizontalExerciseType", horizontalMonitorList.get(horizontalIndex).getExerciseType());
            PreferenceManager.setInt(context, "horizontalLayoutType", horizontalMonitorList.get(horizontalIndex).getLayoutType());
        }
        else{
            PreferenceManager.setString(context, "horizontalMonitorId", "");
            PreferenceManager.setString(context, "horizontalMonitorName", "");
            PreferenceManager.setInt(context, "horizontalMonitorType", -1);
            PreferenceManager.setString(context, "horizontalExerciseType", "");
            PreferenceManager.setInt(context, "horizontalLayoutType", -1);
        }
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
                "LayoutType : " + monitorInstance.layoutType + "\n" +
                "====================================================" + "\n" +
                "SecondMonitorId : " + monitorInstance.secondMonitorId + "\n" +
                "SecondMonitorName : " + monitorInstance.secondMoniterName + "\n" +
                "SecondMonitorType : " + monitorInstance.secondMonitorType + "\n" +
                "SecondExerciseType : " + monitorInstance.secondExerciseType + "\n" +
                "SecondLayoutType : " + monitorInstance.secondLayoutType + "\n" +
                "====================================================" + "\n" +
                "HorizontalMonitorId : " + monitorInstance.horizontalMonitorId + "\n" +
                "HorizontalMonitorName : " + monitorInstance.horizontalMonitorName + "\n" +
                "HorizontalMonitorType : " + monitorInstance.horizontalMonitorType + "\n" +
                "HorizontalExerciseType : " + monitorInstance.horizontalExerciseType + "\n" +
                "HorizontalLayoutType : " + monitorInstance.horizontalLayoutType);
    }
}
