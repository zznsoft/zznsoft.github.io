package com.example.zzn.nuomi.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zzn.nuomi.R;
import com.example.zzn.nuomi.adapter.CurriculumAdapter;
import com.example.zzn.nuomi.adapter.MainAdapter;
import com.example.zzn.nuomi.http.GankUrl;
import com.example.zzn.nuomi.http.NetWork;
import com.example.zzn.nuomi.model.AllResult;
import com.example.zzn.nuomi.util.Localize;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by ZZN on 2017/8/7.
 */

public class MainFragment extends BaseFragment {
    private RecyclerView recycler_view;
    private SwipeRefreshLayout swipe;
    private MainAdapter mAdapter;
    private CurriculumAdapter curriculumAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String localize;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main,null);
        recycler_view = (RecyclerView) v.findViewById(R.id.recycler_view);
        swipe = (SwipeRefreshLayout) v.findViewById(R.id.swip);
        checkPermission();
        return v;
    }

    private void checkPermission() {
        RxPermissions rxPermissions=new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION).subscribe(
                new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean){
                            try {
                                localize= Localize.getLngAndLat(getActivity());
                                System.out.println(localize.split(",")[0]+"--"+localize.split(",")[1]);
                                initData();
                            }catch (Exception e){
                                localize="120.612214,29.982864";
                                initData();
                            }
                        }else {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                            alertDialog.setMessage("请手动开启权限！");
                            alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().finish();
                                }
                            });
                            alertDialog.create().show();
                        }
                    }
                });
    }

    private void initData() {

        layoutManager = new LinearLayoutManager(mContent);
        curriculumAdapter =new CurriculumAdapter(mContent);
        recycler_view.setLayoutManager(new GridLayoutManager(recycler_view.getContext(), 1, GridLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter=new MainAdapter(mContent, curriculumAdapter,layoutManager));
        recycler_view.addOnScrollListener(mScrollListener);

        getNetData(false); //刚进入界面先刷新一次
        getNetData(true);
        //刷新时执行的事件
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNetData(true);
            }
        });
        swipe.measure(View.MEASURED_SIZE_MASK, View.MEASURED_HEIGHT_STATE_SHIFT);
        swipe.setRefreshing(true);
    }

    //RecyclerView向下滑动事件
    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == layoutManager.getItemCount()) {
//                getNetData(true);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            lastVisibleItem = findLastVisibleItemPosition();
        }
    };

    //查询最后一个可见Item的下标
    public int findLastVisibleItemPosition() {
        int lastVisibleItemPosition = 0;
        lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        return lastVisibleItemPosition;
    }

    //资讯
    private void getNetData(boolean loadMore) {
        int contentQuantity=8;
        Observable.combineLatest(NetWork.getGankApi().getAllDate(GankUrl.FLAG_GIRLS, contentQuantity, 1), NetWork.getGankApi().getAllDate(GankUrl.FLAG_Android, contentQuantity, 1),
                new Func2<AllResult, AllResult, Void>() {
                    @Override
                    public Void call(AllResult PicResult, AllResult TextResult) {
                        mAdapter.setPicData(PicResult.getResults());
                        mAdapter.setTextData(TextResult.getResults());
                        return null;
                    }
                })
                .compose(this.<Void>bindToLifecycle())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mAdapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    }
                });
    }
//    //课程信息
//    private void getCurriculum() {
//       int contentQuantity=4;
//        Observable.combineLatest(NetWork.getGankApi().getAllDate(GankUrl.FLAG_GIRLS, contentQuantity, 1),
//                NetWork.getGankApi().getAllDate(GankUrl.FLAG_Android, contentQuantity, 1),
//                new Func2<AllResult, AllResult, Void>() {
//                    @Override
//                    public Void call(AllResult PicResult, AllResult TextResult) {
//                        curriculumAdapter.setTextData(TextResult.getResults());
//                        return null;
//                    }
//                })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        curriculumAdapter.notifyDataSetChanged();
//                    }
//                });
//    }

    //天气数据
//    public void getWeather() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                  new MainFragment().getWeather();
//            }
//        }).start();
//    }
//        OkHttpClient client = new OkHttpClient();                //新建客户端
//        Request request = new Request.Builder()                    //新建请求
//                .get()
//                .addHeader("Authorization","APPCODE 7acec63f07e44e11843dafa4e8b3aa92")//get请求
//                .url("http://jisutqybmf.market.alicloudapi.com/weather/query?location=29.98303,120.612443")//URL
//                .build();
//        Response response = null;            //返回对象
//        try {
//            response = client.newCall(request).execute();
//            if (response.isSuccessful()) {                                    //阻塞线程。
//                Log.e("code",":"+response.code());
//                Log.e("body",response.body().string());
//            }
//            else {
//                Log.e("code",":"+response.code());
//                Log.e("body",response.message());
//                Log.e("---","不成功");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
}
