package com.example.zzn.nuomi.http;

import com.example.zzn.nuomi.model.AllResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by castl on 2016/5/13.
 */
public interface GankApi {
    @GET("data/{type}/{count}/{pageIndex}")
    Observable<AllResult> getAllDate(@Path("type") String type,
                                     @Path("count") int count,
                                     @Path("pageIndex") int pageIndex
    );
}
