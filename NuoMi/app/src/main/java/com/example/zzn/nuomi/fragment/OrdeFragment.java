package com.example.zzn.nuomi.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zzn.nuomi.R;
import com.example.zzn.nuomi.adapter.MyItemOnClickListener;
import com.example.zzn.nuomi.adapter.OrderAdapter;
import com.example.zzn.nuomi.http.NetWork;
import com.example.zzn.nuomi.model.MyResult;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by ZZN on 2017/8/7.
 */

public class OrdeFragment extends BaseFragment {
    private RecyclerView recycler_view;
    private SwipeRefreshLayout swipe;
    private OrderAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_order,null);
        recycler_view = (RecyclerView) v.findViewById(R.id.order_recycler_view);
        swipe = (SwipeRefreshLayout) v.findViewById(R.id.order_swip);
        initData();
        return v;
    }

    @Override
    public void onResume() {
        Log.e("onResume","onResume");
        super.onResume();
    }

    private void initData() {

        layoutManager = new LinearLayoutManager(mContent);
        mAdapter = new OrderAdapter(mContent);
        recycler_view.setLayoutManager(new GridLayoutManager(recycler_view.getContext(), 6, GridLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter=new OrderAdapter(getActivity()));
        recycler_view.addOnScrollListener(mScrollListener);
        mAdapter.setItemOnClickListener(new MyItemOnClickListener() {
            @Override
            public void onItemOnClick(View view, String postion) {
//                Intent intent=new Intent(getActivity(), ShoppingCartActivity.class);
//                startActivity(intent);
            }
        });

        getNetData(); //刚进入界面先刷新一次
        //刷新时执行的事件
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNetData();
            }
        });
    }

    //RecyclerView向下滑动事件
    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == layoutManager.getItemCount()) {
                getNetData();
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


    private void getNetData() {
        Observable.combineLatest(NetWork.getMyApi().getOrder(),NetWork.getMyApi().getOrder(),
                new Func2<MyResult, MyResult, Void>() {
                    @Override
                    public Void call(MyResult PicResult, MyResult TextResult) {
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

}
