package com.example.fist_android.repository;

import android.content.Context;

import com.example.fist_android.model.Office;
import com.example.fist_android.model.ResponseDTO;
import com.example.fist_android.preference.PreferenceManager;
import com.example.fist_android.retrofit.RetrofitAPI;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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



    //=============================================================//
    //Retrofit
    //=============================================================//
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://211.107.110.77:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
    //=============================================================//
    public void fetchOffice() {
        Logger.d("Office Fetch Start");
        retrofitAPI.getOfficeData().enqueue(new Callback<ResponseDTO<Office>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Office>> call, Response<ResponseDTO<Office>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<Office> officeList = response.body();
                    Office offices[] = officeList.getData();
                    for(Office office : offices){
                        office.printOfficeData();
                        OfficeRepository officeRepository = OfficeRepository.getInstance();
                        officeRepository.officeList = offices;
                        officeRepository.officeNameList.add(office.getOfficeName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Office>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        Logger.d("Office Fetch Done");
    }
    //=============================================================//

    public void saveOfficeData(Context context, String key, String value){
        PreferenceManager.setString(context, key, value);
    }
    public String getOfficeData(Context context, String key){
        return PreferenceManager.getString(context, key);
    }
}
