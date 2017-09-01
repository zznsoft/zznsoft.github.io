/*
 * Copyright (c) 2016. pokermman Inc. All rights reserved.
 */

package com.huasky.elderyun.common.utils;


import com.huasky.elderyun.common.base.ActivityLifeCycleEvent;
import com.huasky.elderyun.common.utils.httpClient.HttpResult;
import com.huasky.elderyun.common.utils.httpClient.ProgressSubscriber;
import com.huasky.elderyun.common.utils.httpClient.RetrofitCache;
import com.huasky.elderyun.common.utils.httpClient.RxHelper;

import rx.Observable;
import rx.functions.Action0;
import rx.subjects.PublishSubject;

/** retrofir2.0 http封装工具类
 * Created by pokermman on 2016/12/23.
 */

public class HttpUtil {

    /**
     * 构造方法私有
     */
    private HttpUtil() {
    }

    /**
     * 在访问HttpUtil时创建单例
     */
    private static class SingletonHolder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    /**
     * 获取单例
     */
    public static HttpUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

//    //添加线程管理并订阅
//    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber, String cacheKey, boolean isSave, boolean forceRefresh) {
//        //数据预处理
//        Observable.Transformer<HttpResult<Object>, Object> result = RxHelper.handleResult();
//        //重用操作符
//        Observable observable = ob.compose(result)
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        //显示Dialog和一些其他操作
//                        subscriber.showProgressDialog();
//                    }
//                });
//    }
    /*
     * Observable (可观察者，即被观察者)、
     * Observer (观察者)、
     * subscribe (订阅)、事件。
     * Observable 和 Observer 通过 subscribe() 方法实现订阅关系，
     * 从而 Observable 可以在需要的时候发出事件来通知 Observer。
     * <p>
     * <p>
     * subscribeOn和observeOn都是用来切换线程用的
     */

    /**
     * 添加线程管理并订阅
     * @author PokerMman
     * @param ob 可观察者
     * @param subscriber Dialog管理和处理回调
     * @param cacheKey 缓存kay
     * @param event Activity 生命周期
     * @param lifecycleSubject 生命周期PublishSubject
     * @param isSave 是否缓存
     * @param forceRefresh 是否强制刷新
     */
    //添加线程管理并订阅
    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber, String cacheKey, ActivityLifeCycleEvent event, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject,
                            boolean isSave, boolean forceRefresh) {
        //数据预处理
        Observable.Transformer<HttpResult<Object>, Object> result = RxHelper.handleResult(event,lifecycleSubject);

        //重用操作符
        Observable observable = ob.compose(result)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //显示Dialog和一些其他操作
                        subscriber.showProgressDialog();
                    }
                });

        RetrofitCache.load(cacheKey,observable,isSave,forceRefresh).subscribe(subscriber);
    }
}
