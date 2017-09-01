/*
 * Copyright (c) 2016. pokermman Inc. All rights reserved.
 */

package com.huasky.elderyun.common.utils.httpClient;

import com.apkfuns.logutils.LogUtils;
import com.huasky.elderyun.bean.LoginBean;
import com.huasky.elderyun.common.Global;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pokermman on 2016/12/23.
 */

public class CommonInterceptor implements Interceptor {

    private String token;
    public CommonInterceptor(){
        token = "";
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        LoginBean tokenParam = Hawk.get(Global.LOGIN_KEY);
        if (tokenParam == null){
            token = "";
        }else {
            token = tokenParam.getToken();
        }
        Request oldRequest = chain.request();

        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("token",token);

        Request  newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(),oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}
