package com.huasky.elderyun.common;

import android.app.Activity;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.apkfuns.logutils.LogUtils;

/**
 * Created by cj on 2017/4/14.
 */

public class StatusBarUtils {
    public static void setStatusBarColor(Activity activity,int colorId){
        try{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                Window window=activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(activity,colorId));
            }
        }catch (Exception e){
            LogUtils.d(e);
        }
    }
}
