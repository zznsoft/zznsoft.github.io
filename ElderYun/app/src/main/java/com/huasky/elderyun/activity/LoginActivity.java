package com.huasky.elderyun.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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
import com.huasky.elderyun.common.utils.utils.EncryptUtils;
import com.huasky.elderyun.common.utils.utils.ToastUtils;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import rx.Observable;

public class LoginActivity extends CommonActivity implements TextView.OnEditorActionListener, View.OnTouchListener {

    @BindView(R.id.et_login_identify)
    EditText etLoginIdentify;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtils.setStatusBarColor(this, R.color.colorStatus);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        etLoginIdentify.setOnEditorActionListener(this);
        etLoginPassword.setOnEditorActionListener(this);
        etLoginPassword.setOnTouchListener(this);
        etLoginIdentify.setOnTouchListener(this);
        etLoginIdentify.setCursorVisible(false);
        etLoginPassword.setCursorVisible(false);

        LoginBean loginBean=Hawk.get(Global.LOGIN_KEY);
        if(loginBean!=null){
            startActivity(new Intent(this,MedicalListActivity.class));
            finish();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null&&actionId== EditorInfo.IME_ACTION_DONE && im.isActive()) {
            im.hideSoftInputFromWindow(v.getWindowToken(), 0);
            v.setCursorVisible(false);
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ((TextView) v).setCursorVisible(true);
        }
        return false;
    }

    @OnClick({R.id.tv_login_forgetpassword, R.id.tv_login_register, R.id.btn_login_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forgetpassword://忘记密码
                break;
            case R.id.tv_login_register://快速注册
                break;
            case R.id.btn_login_login://登录
                doLogin();
                break;
        }
    }

    private void doLogin() {
        String identify=etLoginIdentify.getText().toString();
        String password=etLoginPassword.getText().toString();
        if(!checkUser(identify,password)){
            ToastUtils.showShortToast("请输入正确的身份证号码和密码");
            return;
        }

        Observable ob=RetrofitManger.getDefault().doLogin(identify, EncryptUtils.encryptMD5ToString(password));
        HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber<LoginBean>(this) {
            @Override
            protected void _onNext(LoginBean loginBean) {
                if(loginBean!=null){
                    Hawk.put(Global.LOGIN_KEY,loginBean);
                }
                ToastUtils.showShortToast("登录成功");
                startActivity(new Intent(LoginActivity.this,MedicalListActivity.class));
                finish();
            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShortToast(message);
            }
        },"cacheKey",ActivityLifeCycleEvent.PAUSE,lifecycleSubject,false,false);

    }

    private boolean checkUser(String identify, String password) {
        if(TextUtils.isEmpty(identify)||TextUtils.isEmpty(password)){
            return false;
        }
        return true;
    }
}
