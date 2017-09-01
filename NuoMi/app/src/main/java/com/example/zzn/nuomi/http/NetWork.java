package com.example.zzn.nuomi.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by castl on 2016/5/13.
 */
public class NetWork {
    private static GankApi gankApi;
    private static WeatherApi weatherApi;
    private static MyApi myApi;

    public static GankApi getGankApi() {
        if (gankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OkHttpClient.okhttp())
                    .baseUrl(GankUrl.GANK_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            gankApi = retrofit.create(GankApi.class);
        }
        return gankApi;
    }

    public static WeatherApi getWeatherApi(){
        if (weatherApi==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OkHttpClient.okhttp())
                    .baseUrl(GankUrl.WEATHER_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            weatherApi = retrofit.create(WeatherApi.class);
        }
        return weatherApi;
    }

    public static MyApi getMyApi() {
        if (myApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OkHttpClient.okhttp())
                    .baseUrl(GankUrl.MY_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            myApi = retrofit.create(MyApi.class);
        }
        return myApi;
    }
}
