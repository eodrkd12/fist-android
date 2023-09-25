package com.example.fist_android.api;

import android.util.Log;

import com.example.fist_android.model.Monitor;
import com.example.fist_android.model.Office;
import com.example.fist_android.model.ResponseDTO;
import com.example.fist_android.repository.MonitorRepository;
import com.example.fist_android.repository.OfficeRepository;
import com.example.fist_android.retrofit.RetrofitAPI;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MonitorAPI {
    MonitorRepository monitorRepository = MonitorRepository.getInstance();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://211.107.110.77:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    public void fetchMonitor(String officeId, int offest, int limit){
        Logger.d("Monitor Fetch Start");
        retrofitAPI.getMonitorData(officeId, offest, limit).enqueue(new Callback<ResponseDTO<Monitor>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Monitor>> call, Response<ResponseDTO<Monitor>> response) {
                if(response.isSuccessful()){
                    ResponseDTO<Monitor> monitorList = response.body();
                    Monitor monitors[] = monitorList.getData();
                    for(Monitor monitor: monitors){
                        monitor.printMonitorData();
                        monitorRepository.monitorList = monitors;
                        monitorRepository.monitorNameList.add(monitor.getMonitorName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Monitor>> call, Throwable t) {

            }
        });
        Logger.d("Monitor Fetch Done");
    }
}
