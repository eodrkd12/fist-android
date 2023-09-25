package com.example.fist_android.repository;

import android.content.Context;

import com.example.fist_android.model.Monitor;
import com.example.fist_android.model.Office;
import com.example.fist_android.model.ResponseDTO;
import com.example.fist_android.preference.PreferenceManager;
import com.example.fist_android.retrofit.RetrofitAPI;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

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
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://211.107.110.77:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
    //=============================================================//

    public Monitor[] monitorList;
    public ArrayList<String> monitorNameList = new ArrayList<>();


    //=============================================================//
    public void fetchMonitor(String officeId, int offest, int limit, final MonitorFetchCallback callback){
        Logger.d("Monitor Fetch Start");
        retrofitAPI.getMonitorData(officeId, offest, limit).enqueue(new Callback<ResponseDTO<Monitor>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Monitor>> call, Response<ResponseDTO<Monitor>> response) {
                if(response.isSuccessful()){
                    ResponseDTO<Monitor> monitorData = response.body();
                    Monitor monitors[] = monitorData.getData();
                    if(monitors.length == 0){
                        monitorList = null;
                        monitorNameList.clear();
                    }
                    for(Monitor monitor: monitors){
                        monitor.printMonitorData();
                        monitorList = monitors;
                        monitorNameList.add(monitor.getMonitorName());
                    }
                    Logger.d("Response DATA: " + monitorNameList);

                    // 응답을 처리한 후에 콜백을 호출하여 UI 업데이트를 요청합니다.
                    if (callback != null) {
                        callback.onMonitorFetchComplete();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Monitor>> call, Throwable t) {
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
}
