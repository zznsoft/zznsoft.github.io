/*
 * Copyright (c) 2016. pokermman Inc. All rights reserved.
 */

package com.huasky.elderyun.common.utils.httpClient;

import android.content.Context;
import android.content.Intent;

//import com.huasky.doctoryun.activity.LoginActivity;
//import com.huasky.doctoryun.common.Global;
//import com.huasky.doctoryun.common.utils.dialog.SimpleLoadDialog;
//import com.huasky.doctoryun.common.utils.utils.NetworkUtils;
//import com.huasky.doctoryun.common.utils.utils.ToastUtils;

import com.huasky.elderyun.activity.LoginActivity;
import com.huasky.elderyun.common.Global;
import com.huasky.elderyun.common.utils.utils.NetworkUtils;
import com.huasky.elderyun.common.utils.utils.ToastUtils;
import com.huasky.elderyun.common.utils.widget.SimpleLoadDialog;

import rx.Subscriber;

/**
 * Created by pokermman on 2016/12/23.
 */


public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    //    private SweetAlertDialog dialogHandler;
    private SimpleLoadDialog dialogHandler;
    private Context context;

    public ProgressSubscriber(Context context) {
        this.context = context;
        dialogHandler = new SimpleLoadDialog(context, this, true);
    }

    public ProgressSubscriber(Context context, Boolean isShow) {
        this.context = context;
        if (isShow) {
            dialogHandler = new SimpleLoadDialog(context, this, true);
        }

    }


    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!NetworkUtils.isAvailableByPing()) {
            _onError("网络不可用");
            ToastUtils.showShortToast("网络不可用");
        } else if (e instanceof ApiException) {
            _onError(e.getMessage());
            if (e.getMessage().equals(Global.ERROR_MSG_NOTLOGIN)){
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                ToastUtils.showShortToast("登录超时，请重新登录");
            }
            ToastUtils.showShortToast(e.getMessage());
        } else if (e instanceof java.net.SocketTimeoutException) {
            _onError("连接超时，请稍后再试");
            dismissProgressDialog();
        } else {
            _onError(e.getMessage());
            ToastUtils.showShortToast(e.getMessage());
        }
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.isUnsubscribed();
        }
    }

    /**
     * 显示Dialog
     */
    public void showProgressDialog() {
        if (dialogHandler != null) {
            dialogHandler.show();
        }
    }

    /**
     * 隐藏Dialog
     */
    public void dismissProgressDialog() {
        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.dismiss();
            dialogHandler = null;
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}
