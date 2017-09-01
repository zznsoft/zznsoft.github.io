package com.huasky.elderyun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.huasky.elderyun.R;
import com.huasky.elderyun.common.StatusBarUtils;
import com.huasky.elderyun.common.base.CommonActivity;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MedicalChooserActivity extends CommonActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_chooser);
        ButterKnife.bind(this);

        StatusBarUtils.setStatusBarColor(this,R.color.colorStatus);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MedicalChooserActivity.this.finish();
            }
        });

    }

    @OnClick(R.id.rl_medical_medicalgimen)
    void setRl_medical() {
        ConsultSource source = new ConsultSource("uri", "source_title", "custom");
        Unicorn.openServiceActivity(this, "医疗", source);
    }

    @OnClick(R.id.rl_gimen_medicalgimen)
    void setRl_gimen() {
        startActivity(new Intent(this, LongevityActivity.class));
    }

}
