/*
 * Copyright (c) 2017. pokermman Inc. All right reserved.
 */

package com.huasky.elderyun.common.utils.httpClient;

import com.huasky.elderyun.app.BaseApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/** 萤石 api http
 * Created by pokermman on 2017/1/10.
 */

public class ApiManger {


//    private static OkHttpClient mOkHttpClient;
//
//
//    public static YsOpenService SERVICE;
//
//    /**
//     * 请求超时时间
//     */
//    private static final int DEFAULT_TIMEOUT = 10000;
//
//
//    private static final String BASE_URL = "https://open.ys7.com";// 萤石SDK地址
////    private static final String BASE_URL = "http://115.239.244.202:1106";// test
//
//    //短缓存有效期为1分钟
//    public static final int CACHE_STALE_SHORT = 60;
//    //7天
//    private static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;
//
//
//    public static YsOpenService getDefault(){
//        if (SERVICE == null) {
//            //手动创建一个OkHttpClient并设置超时时间
//            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
//            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);//添加超时时间
////            /**
////             * 添加缓存时间
////             * */
////            Cache cache = new Cache(new File(AppContext.getInstance().getCacheDir(), "HttpCache"),
////                    1024 * 1024 * 100);
////            httpClientBuilder.cache(cache);
//
//            httpClientBuilder.cookieJar(new CookieManger(BaseApplication.getContext()));//持久化cookie
//            /**
//             * 打印http信息
//             * */
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//打印
//            httpClientBuilder.addInterceptor(httpLoggingInterceptor);
//
//
//            /**
//             * 对所有请求添加请求头
//             */
//            httpClientBuilder.addInterceptor(new Interceptor() {
//                @Override
//                public okhttp3.Response intercept(Chain chain) throws IOException {
//                    Request request = chain.request();
//                    okhttp3.Response originalResponse = chain.proceed(request);
////                    return originalResponse.newBuilder().build();
//                    return originalResponse.newBuilder().header("Host", "open.ys7.com").addHeader("Content-Type", "application/x-www-form-urlencoded").build();
//                }
//            });
//            /**
//             * 添加统一的token参数*/
//            CommonInterceptor commonInterceptor = new CommonInterceptor();
//            httpClientBuilder.addInterceptor(commonInterceptor);
//
//            SERVICE = new Retrofit.Builder()
//                    .client(httpClientBuilder.build())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加RXJAVA
//                    .baseUrl(BASE_URL)
//                    .build().create(YsOpenService.class);
//        }
//        return SERVICE;
//    }
}
