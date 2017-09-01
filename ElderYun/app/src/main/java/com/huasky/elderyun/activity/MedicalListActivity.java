package com.huasky.elderyun.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.apkfuns.logutils.LogUtils;
import com.huasky.elderyun.R;
import com.huasky.elderyun.bean.LoginBean;
import com.huasky.elderyun.common.Global;
import com.huasky.elderyun.common.StatusBarUtils;
import com.huasky.elderyun.common.base.ActivityLifeCycleEvent;
import com.huasky.elderyun.common.base.CommonActivity;
import com.huasky.elderyun.common.utils.HttpUtil;
import com.huasky.elderyun.common.utils.httpClient.ProgressSubscriber;
import com.huasky.elderyun.common.utils.httpClient.RetrofitManger;
import com.huasky.elderyun.common.utils.utils.ToastUtils;
import com.huasky.elderyun.widget.CircleView;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class MedicalListActivity extends CommonActivity implements View.OnTouchListener {

    @BindView(R.id.rl_circle_medicallist)
    CircleView rlCircleMedicallist;
    private String elderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_list);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarColor(this, R.color.colorStatus);
        rlCircleMedicallist.setOnTouchListener(this);
        elderId=((LoginBean)Hawk.get(Global.LOGIN_KEY)).getElderId();
    }


    @OnClick(R.id.rl_medicalregimen_medicallist)
    void setRl_medicalRegimen() {
        startActivity(new Intent(this, MedicalChooserActivity.class));
    }


    private long lastPressed;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - lastPressed) <= 2000) {
            finish();
        } else {
            lastPressed = System.currentTimeMillis();
            ToastUtils.showShortToast("再按一次退出程序");
        }
    }

    @OnClick(R.id.btn_mediallist_exit)
    public void onViewClicked() {
        new AlertDialog.Builder(this)
                .setMessage("消息")
                .setMessage("确认退出")
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doLoginout();
                        Hawk.delete(Global.LOGIN_KEY);
                    }
                }).show();
    }

    private void doLoginout() {
        Observable ob = RetrofitManger.getDefault().doLogout();
        HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber(this) {
            @Override
            protected void _onNext(Object o) {
                Hawk.delete(Global.LOGIN_KEY);
                startActivity(new Intent(MedicalListActivity.this, LoginActivity.class));
                MedicalListActivity.this.finish();
            }

            @Override
            protected void _onError(String message) {

            }
        }, "cacheKey", ActivityLifeCycleEvent.PAUSE, lifecycleSubject, false, false);
    }

    private boolean isPressed;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isPressed) {
                emergencyCall();
            }
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() != R.id.rl_circle_medicallist) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isPressed = true;
                rlCircleMedicallist.start();
                handler.postDelayed(runnable, 3000);
                rl_animate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isPressed = false;
                rlCircleMedicallist.stop();
                rlCircleMedicallist.clearAnimation();
                handler.removeCallbacks(runnable);
                break;
        }
        return true;
    }

    private Animation scaleAnimation;
    private void rl_animate() {
        if(scaleAnimation==null){
            scaleAnimation= AnimationUtils.loadAnimation(this,R.anim.circle_scale);
        }
        rlCircleMedicallist.startAnimation(scaleAnimation);
    }

    private void emergencyCall() {
        Observable ob=RetrofitManger.getDefault().emergency(elderId,"0","","");
        HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber(this,false) {
            @Override
            protected void _onNext(Object o) {
                ToastUtils.showLongToast("已报警");
            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showLongToast(message);
            }
        },"cacheKey",ActivityLifeCycleEvent.PAUSE,lifecycleSubject,false,false);
    }

    @OnClick(R.id.rl_call)
    public void onCall() {
        startActivity(new Intent(this,EmergencyListActivity.class));
    }
}
