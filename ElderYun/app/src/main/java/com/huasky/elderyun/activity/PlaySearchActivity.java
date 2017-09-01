package com.huasky.elderyun.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huasky.elderyun.R;
import com.huasky.elderyun.adapter.PlaySearchAdapter;
import com.huasky.elderyun.bean.mediaBean.LongevityBean;
import com.huasky.elderyun.bean.mediaBean.MediaInfo;
import com.huasky.elderyun.common.StatusBarUtils;
import com.huasky.elderyun.common.base.ActivityLifeCycleEvent;
import com.huasky.elderyun.common.base.CommonActivity;
import com.huasky.elderyun.common.utils.HttpUtil;
import com.huasky.elderyun.common.utils.httpClient.ProgressSubscriber;
import com.huasky.elderyun.common.utils.httpClient.RetrofitManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

import static com.huasky.elderyun.common.utils.utils.ClipboardUtils.getIntent;

public class PlaySearchActivity extends CommonActivity implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.rcv_playsearch)RecyclerView rcv;

    private PlaySearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarColor(this,R.color.colorStatus);

        init();
        initData();
    }

    private int pageNum=1;
    private int pageSize=10;
    private int totalNum;
    private void initData() {
        String searchContent=getIntent().getStringExtra("SearchContent");
        Observable ob= RetrofitManger.getDefault().searchMedia(String.valueOf(pageNum), String.valueOf(pageSize),searchContent);
        HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber<MediaInfo>(this,true) {
            @Override
            protected void _onNext(MediaInfo info) {
                LogUtils.d(info);
                totalNum=info.getTotalNum();
                List<LongevityBean> datas=info.getMediaList().getMedia();
                if(datas.size()==0){
                    TextView tv=new TextView(PlaySearchActivity.this);
                    ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    tv.setText("暂无数据");
                    tv.setGravity(Gravity.CENTER);
                    tv.setLayoutParams(lp);
                    adapter.setEmptyView(tv);
                    return;
                }
                adapter.addData(datas);
                adapter.loadMoreComplete();
            }

            @Override
            protected void _onError(String message) {
                adapter.loadMoreFail();
            }
        },"cacheKey", ActivityLifeCycleEvent.PAUSE,lifecycleSubject,false,false);
    }

    private void init() {
        toolbar.setTitle("搜索");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaySearchActivity.this.finish();
            }
        });
        rcv.setLayoutManager(new GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false));
        rcv.setAdapter(adapter=new PlaySearchAdapter(R.layout.item_longevity,new ArrayList<LongevityBean>(),this));
        adapter.setOnItemClickListener(this);
        adapter.setOnLoadMoreListener(PlaySearchActivity.this,rcv);
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
