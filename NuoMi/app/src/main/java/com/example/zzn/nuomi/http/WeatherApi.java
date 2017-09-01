package com.example.zzn.nuomi.http;

import com.example.zzn.nuomi.model.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by ZZN on 2017/8/22.
 */
public interface WeatherApi {
    @Headers("Authorization:APPCODE 7acec63f07e44e11843dafa4e8b3aa92")
    @GET("weather/query")
    Call<WeatherResult> getWeather(@Query("location") String localize);
}