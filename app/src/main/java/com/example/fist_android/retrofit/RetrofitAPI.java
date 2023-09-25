package com.example.fist_android.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

import com.example.fist_android.model.Office;
import com.example.fist_android.model.ResponseDTO;
import com.google.gson.JsonObject;

public interface RetrofitAPI {
    @GET("/office/list")
    Call<ResponseDTO<Office>> getData();
}
