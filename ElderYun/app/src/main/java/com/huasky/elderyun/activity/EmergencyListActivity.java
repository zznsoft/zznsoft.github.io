package com.huasky.elderyun.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huasky.elderyun.R;
import com.huasky.elderyun.bean.BaseListBean;
import com.huasky.elderyun.bean.ContactBean;
import com.huasky.elderyun.bean.LoginBean;
import com.huasky.elderyun.common.Global;
import com.huasky.elderyun.common.StatusBarUtils;
import com.huasky.elderyun.common.base.ActivityLifeCycleEvent;
import com.huasky.elderyun.common.base.CommonActivity;
import com.huasky.elderyun.common.utils.HttpUtil;
import com.huasky.elderyun.common.utils.httpClient.ProgressSubscriber;
import com.huasky.elderyun.common.utils.httpClient.RetrofitManger;
import com.huasky.elderyun.widget.ListViewForScrollView;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class EmergencyListActivity extends CommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_watch)
    ListViewForScrollView listWatch;
    @BindView(R.id.list_near)
    ListViewForScrollView listNear;
    @BindView(R.id.list_old)
    ListViewForScrollView listOld;
    @BindView(R.id.workername)
    TextView workerName;
    @BindView(R.id.workertel)
    TextView workertel;

    private String elderId;
    private ArrayList<ContactBean> watchList = null;
    private ArrayList<ContactBean> nearList = null;
    private ArrayList<ContactBean> oldList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_list);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarColor(this,R.color.colorStatus);

        init();
    }

    private void init() {
        toolbar.setTitle("紧急求助");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmergencyListActivity.this.finish();
            }
        });
        elderId = ((LoginBean) Hawk.get(Global.LOGIN_KEY)).getElderId();
        watchList = new ArrayList<>();
        nearList = new ArrayList<>();
        oldList = new ArrayList<>();

        Observable ob = RetrofitManger.getDefault().getEmergencyContactList(elderId);
        HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber<BaseListBean<ContactBean>>(this, true) {
            @Override
            protected void _onNext(BaseListBean<ContactBean> contactBeanBaseListBean) {
                if (contactBeanBaseListBean.getTotalCount() > 0) {
                    for (ContactBean cb : contactBeanBaseListBean.getContactList()) {
                        if (cb.getType() == 1) {
                            watchList.add(cb);
                        } else if (cb.getType() == 2) {
                            nearList.add(cb);
                        } else if (cb.getType() == 3) {
                            oldList.add(cb);
                        } else if (cb.getType() == 4) {
                            workerName.setText(cb.getName());
                            workertel.setText(cb.getMobile());
                        }
                    }
                    MyListAdapter adapt = new MyListAdapter(getApplicationContext(), watchList);
                    listWatch.setAdapter(adapt);

                    MyListAdapter adapt2 = new MyListAdapter(getApplicationContext(), nearList);
                    listNear.setAdapter(adapt2);

                    MyListAdapter adapt3 = new MyListAdapter(getApplicationContext(), oldList);
                    listOld.setAdapter(adapt3);
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }, "cacheKey", ActivityLifeCycleEvent.PAUSE, lifecycleSubject, false, false);
    }

    private void call(String tel) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + tel));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    @OnClick({R.id.call1, R.id.call2, R.id.call3, R.id.call4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.call1:
                call("120");
                break;
            case R.id.call2:
                call("110");
                break;
            case R.id.call3:
                call("119");
                break;
            case R.id.call4:
                call(workertel.getText().toString());
                break;
        }
    }

    private class MyListAdapter extends ArrayAdapter<ContactBean> {
        /**
         * コンストラクタ
         */
        public MyListAdapter(Context context, ArrayList<ContactBean> pMapList) {
            super(context, R.layout.emergency_list_list_body, pMapList);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.emergency_list_list_body, parent, false);
            }
            final ContactBean bean = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.workername);
            TextView tel = (TextView) view.findViewById(R.id.workertel);
            ImageView call = (ImageView) view.findViewById(R.id.call);

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call(bean.getMobile());
                }
            });

            name.setText(bean.getName());
            tel.setText(bean.getMobile());

            return view;
        }
    }
}
