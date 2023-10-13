package com.example.fist_android.repository;

import android.content.Context;

import com.example.fist_android.common.Constant;
import com.example.fist_android.model.Office;
import com.example.fist_android.model.ResponseDTO;
import com.example.fist_android.preference.PreferenceManager;
import com.example.fist_android.retrofit.RetrofitAPI;
import com.google.gson.annotations.SerializedName;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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
    public ArrayList<String> officeNameList = new ArrayList<>(Arrays.asList("지점을 선택해주세요."));
    public String officeId;
    public String officeName;
    public String officeAddr;
    public String officeTel;


    //=============================================================//
    //Retrofit
    //=============================================================//
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.BE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    public void fetchOffice(final OfficeRepository.OfficeFetchCallback callback) {
        Logger.d("Office Fetch Start");
        retrofitAPI.getOfficeData().enqueue(new Callback<ResponseDTO<Office[]>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Office[]>> call, Response<ResponseDTO<Office[]>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<Office[]> officeList = response.body();
                    Office offices[] = officeList.getData();
                    for(Office office : offices){
//                        office.printOfficeData();
                        OfficeRepository officeRepository = OfficeRepository.getInstance();
                        officeRepository.officeList = offices;
                        officeRepository.officeNameList.add(office.getOfficeName());
                    }
                    // 응답을 처리한 후에 콜백을 호출하여 UI 업데이트를 요청합니다.
                    if (callback != null) {
                        callback.onOfficeFetchComplete();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Office[]>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        Logger.d("Office Fetch Done");
    }
    // Office 데이터를 가져온 후 UI를 업데이트하는 콜백 인터페이스
    public interface OfficeFetchCallback {
        void onOfficeFetchComplete();
    }
    //=============================================================//
    //SharedPreference
    //=============================================================//
    public void getOfficeData(Context context){
        officeInstance.officeId = PreferenceManager.getString(context, "officeId");
        officeInstance.officeName = PreferenceManager.getString(context, "officeName");
        officeInstance.officeAddr = PreferenceManager.getString(context, "officeAddr");
        officeInstance.officeTel = PreferenceManager.getString(context, "officeTel");
    }

    public void saveOfficeData(Context context, int index){
        PreferenceManager.setString(context, "officeId", officeList[index].getOfficeId());
        PreferenceManager.setString(context, "officeName", officeList[index].getOfficeName());
        PreferenceManager.setString(context, "officeAddr", officeList[index].getOfficeAddr());
        PreferenceManager.setString(context, "officeTel", officeList[index].getOfficeTel());
    }

    public void saveStringOfficeData(Context context, String key, String value){
        PreferenceManager.setString(context, key, value);
    }
    public String getStringOfficeData(Context context, String key){
        return PreferenceManager.getString(context, key);
    }

    //=============================================================//
    //Print
    //=============================================================//
    public void printOfficeData(){
        Logger.i("OfficeId : " + officeInstance.officeId + "\n" +
                "OfficeName : " + officeInstance.officeName+ "\n" +
                "OfficeAddr : " + officeInstance.officeAddr + "\n" +
                "OfficeTel : " + officeInstance.officeTel);
    }
}
