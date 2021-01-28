package com.example.gsonrxjavaretrofittest.api;

import com.example.gsonrxjavaretrofittest.pojo.EmployeeResponse;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface  ApiService {
    //@Headers("Content-Type: application/json")
    @GET("testTask.json")
    Observable<EmployeeResponse> getResponseService();
}
