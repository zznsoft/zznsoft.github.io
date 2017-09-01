/*
 * Copyright (c) 2016. pokermman Inc. All right reserved.
 */

package com.huasky.elderyun.common.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.huasky.elderyun.R;
import com.huasky.elderyun.common.utils.AppManager;
import com.huasky.elderyun.common.utils.httpClient.ProgressCancelListener;
import com.huasky.elderyun.common.utils.utils.SnackbarUtils;
import com.huasky.elderyun.common.utils.widget.SimpleLoadDialog;

import rx.subjects.PublishSubject;

/**
 * Created by pokermman on 2016/12/29.
 */

//@RuntimePermissions
public class CommonActivity extends AppCompatActivity implements ProgressCancelListener {


    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject
            = PublishSubject.create();


    public SimpleLoadDialog simpleLoadDialog;


    public static final int NURSE_GROUP_LIST_ACTIVITY = 1000;
    public static final int ADD_DEVICE_LIST_ACTIVITY = 1001;
    public static final int NURSE_DETAILS_ACTIVITY = 1002;
    public static final int ADD_PERSON_ACTIVITY = 1003;
    public static final int LOGIN_ACTIVITY = 1004;
    public static final int REGISTER_ACTIVITY = 1005;
    public static final int SERIESNUMSEARCH_ACTIVITY = 1006;
    public static final int CAPTURE_ACTIVITY = 1007;
    public static final int EZDEVICELSIT_ACTIVITY = 1008;
    public static final int PERSONAL_CENTER_ACTIVITY = 1009;
    public static final int PERSONAL_CENTER_SETTING_ACTIVITY = 1010;
    public static final int DEVICE_ALL_ACTIVITY = 1011;
    public static final int DEVICE_SETTING_ACTIVITY = 1012;
    public static final int ROBOT_INPUT_ACTIVITY = 1013;
    public static final int ROBOT_SEARCH_LIST_ACTIVITY = 1014;

    public static final int REQUEST_PERMISSIONS = 9999;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE);
        super.onCreate(savedInstanceState);
        initDialog();

//        initView();
    }

    @Override
    public void onCancelProgress() {
    }

    private void initDialog() {
        AppManager.getAppManager().addActivity(this);
        simpleLoadDialog = new SimpleLoadDialog(this,this,true);
    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);

    }

    protected void ToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void getDisplayInfomation() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        LogUtils.d("the screen size is " + point.toString());
    }


    /**
     * 获取状态栏高度——方法1
     */
    public void getDisplayStatusBarHeight() {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
//        LogUtils.d(statusBarHeight1);
    }


    public Point getDisplayInfomationSize() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        LogUtils.d("the screen size is " + point.toString());
        return point;
    }

    /**
     * 获取状态栏高度——方法1
     */
    public int getDisplayStatusBarHeightSize() {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
//        LogUtils.d(statusBarHeight1);
        return statusBarHeight1;

    }


    //pixel = dip*density;
    public int convertDpToPixel(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        LogUtils.d("displayMetrics:" + displayMetrics);
        return (int) (dp * displayMetrics.density);
    }

    public int convertPixelToDp(int pixel) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return (int) (pixel / displayMetrics.density);

    }
    /**
     *设置顶部状态栏颜色
     * */
    public void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(activity,colorResId));
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStatusFullScrenen(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    public void showSnackbar(View view, String message) {
        SnackbarUtils.showShortSnackbar(view, message, ContextCompat.getColor(this, android.R.color.white), ContextCompat.getColor(this, android.R.color.black));
    }
    public void showMessageDialogComfir(String str, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setNegativeButton("取消", negativeListener);
        dialog.setPositiveButton("确定",positiveListener);
        dialog.setMessage(str);
        dialog.create().show();
    }
//    @NeedsPermission(android.Manifest.)
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
}