/*
 * Copyright (c) 2016. pokermman Inc. All rights reserved.
 */

package com.huasky.elderyun.common.utils.httpClient;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by pokermman on 2016/12/26.
 */

public class CookieManger implements CookieJar {
    public static String APP_PLATFORM = "app-platform";

    private static Context mContext;

    private static PersistentCookieStore cookieStore;


    public CookieManger(Context context) {
        mContext = context;
        if (cookieStore == null ) {
            cookieStore = new PersistentCookieStore(mContext);
        }

    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0){
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }

    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies =cookieStore.get(url);
        return cookies;
    }
}
