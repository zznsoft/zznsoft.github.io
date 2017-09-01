package com.example.zzn.nuomi.http;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ZZN on 2017/8/18.
 */

public class OkHttpClient {
    public static okhttp3.OkHttpClient okhttp(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("Retrofitmessage",message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        return okHttpClient;
    }
}
