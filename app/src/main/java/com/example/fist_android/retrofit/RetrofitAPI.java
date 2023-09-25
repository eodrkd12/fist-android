package com.example.fist_android.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.fist_android.model.Monitor;
import com.example.fist_android.model.Office;
import com.example.fist_android.model.ResponseDTO;

public interface RetrofitAPI {
    @GET("/office/list")
    Call<ResponseDTO<Office>> getOfficeData();
    @GET("/monitor/list/{officeId}")
    Call<ResponseDTO<Monitor>> getMonitorData(
            @Path("officeId") String officeId,
            @Query("offset") int offset,
            @Query("limit") int limit
    );
}
