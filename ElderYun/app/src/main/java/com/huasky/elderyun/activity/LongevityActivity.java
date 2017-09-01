package com.huasky.elderyun.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huasky.elderyun.R;
import com.huasky.elderyun.adapter.LongevityAdapter;
import com.huasky.elderyun.bean.mediaBean.LongevityBean;
import com.huasky.elderyun.bean.mediaBean.MediaInfo;
import com.huasky.elderyun.common.StatusBarUtils;
import com.huasky.elderyun.common.base.ActivityLifeCycleEvent;
import com.huasky.elderyun.common.base.CommonActivity;
import com.huasky.elderyun.common.utils.HttpUtil;
import com.huasky.elderyun.common.utils.httpClient.ProgressSubscriber;
import com.huasky.elderyun.common.utils.httpClient.RetrofitManger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class LongevityActivity extends CommonActivity implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.rcv_longevity)RecyclerView rcv;

    private LongevityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longevity);
        ButterKnife.bind(this);

        StatusBarUtils.setStatusBarColor(this,R.color.colorStatus);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LongevityActivity.this.finish();
            }
        });

        initData();


    }


    private int pageNum=1;
    private int pageSize=9;
    private int totalNum=1;
    private void initData() {

        rcv.setLayoutManager(new GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false));

        Observable ob= RetrofitManger.getDefault().searchMedia(String.valueOf(pageNum), String.valueOf(pageSize),"");
        HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber<MediaInfo>(this,true) {
            @Override
            protected void _onNext(MediaInfo mediaInfo) {
                LogUtils.d(mediaInfo);
                totalNum=mediaInfo.getTotalNum();
                List<LongevityBean> datas=mediaInfo.getMediaList().getMedia();
                for(LongevityBean bean:datas){
                    bean.setItemType(1);
                }
                datas.add(0,new LongevityBean(0));
                rcv.setAdapter(adapter=new LongevityAdapter(datas,LongevityActivity.this));
                setUpAdapter();
            }

            @Override
            protected void _onError(String message) {
                LogUtils.d(message);
                adapter.loadMoreFail();
            }
        },"cacheKey", ActivityLifeCycleEvent.PAUSE,lifecycleSubject,false,false);

    }

    private void setUpAdapter() {
        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                if(position==0||position==1){
                    return 2;
                }
                return 1;
            }
        });
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this,rcv);
        adapter.setOnItemClickListener(this);
    }


    @Override
    public void onLoadMoreRequested() {
        if(pageNum*pageSize<totalNum){
            Observable ob=RetrofitManger.getDefault().searchMedia(String.valueOf(pageNum+1), String.valueOf(pageSize),"");
            HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber<MediaInfo>(this,false) {
                @Override
                protected void _onNext(MediaInfo mediaInfo) {
                    pageNum++;
                    List<LongevityBean> datas=mediaInfo.getMediaList().getMedia();
                    for(LongevityBean bean:datas){
                        bean.setItemType(1);
                    }
                    adapter.addData(datas);
                    adapter.loadMoreComplete();
                }

                @Override
                protected void _onError(String message) {
                    adapter.loadMoreFail();
                }
            },"cacheKey",ActivityLifeCycleEvent.PAUSE,lifecycleSubject,false,false);
        }else{
            adapter.loadMoreEnd();
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LongevityBean bean= (LongevityBean) adapter.getData().get(position);
        Intent intent=new Intent(this,PlayerTwoActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("VideoName",bean.getTitle());
        LogUtils.d(bean);
        bundle.putString("URL",((LongevityBean) adapter.getData().get(position)).getMediaId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
