package com.example.fist_android.api;

import android.util.Log;

import com.example.fist_android.model.Office;
import com.example.fist_android.model.ResponseDTO;
import com.example.fist_android.retrofit.RetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OfficeAPI {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://211.107.110.77:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    public void fetchOffice() {
        Log.d("Office Fetch Start", "start");
        retrofitAPI.getData().enqueue(new Callback<ResponseDTO<Office>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Office>> call, Response<ResponseDTO<Office>> response) {
                if (response.isSuccessful()) {
//                    Log.d("Office Fetch DONE", response.body());
                    ResponseDTO<Office> officeList = response.body();
                    Office office[] = officeList.getData();
                    for(Office printOffice : office){
                        printOffice.printOfficeData();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Office>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        Log.d("Office Fetch Done", "Done");
    }
}
