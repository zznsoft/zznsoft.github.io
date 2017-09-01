/*
 * Copyright (c) 2016. pokermman Inc. All rights reserved.
 */

package com.huasky.elderyun.common.utils.httpClient;

import com.orhanobut.hawk.Hawk;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**  RetrofitCache 缓存
 * Created by pokermman on 2016/12/23.
 */

public class RetrofitCache {
    /**
     * @param cacheKey 缓存的Key
     * @param fromNetwork 可观察者
     * @param isSave       是否缓存
     * @param forceRefresh 是否强制刷新
     * @return
     */
    public static <T> Observable<T> load(final String cacheKey,
                                         Observable<T> fromNetwork,
                                         boolean isSave, boolean forceRefresh) {
        Observable<T> fromCache = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

//                subscriber.onCompleted();
                T cache = Hawk.get(cacheKey);
                if (cache != null) {
                    subscriber.onNext(cache);
                } else {
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        //是否缓存
        if (isSave) {
            /**
             * 这里的fromNetwork 不需要指定Schedule,在handleRequest中已经变换了
             */
            fromNetwork = fromNetwork.map(new Func1<T, T>() {
                @Override
                public T call(T result) {
                    Hawk.put(cacheKey, result);
                    return result;
                }
            });
        }
        //强制刷新
        if (forceRefresh) {
            return fromNetwork;
        } else {
            return Observable.concat(fromCache, fromNetwork).first();
        }
    }

}
