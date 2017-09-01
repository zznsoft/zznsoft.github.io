package com.huasky.elderyun.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huasky.elderyun.R;
import com.huasky.elderyun.bean.mediaBean.LongevityBean;

import java.util.List;

/**
 * Created by cj on 2017/4/1.
 */

public class PlaySearchAdapter extends BaseQuickAdapter<LongevityBean,BaseViewHolder> {
    private Context context;
    public PlaySearchAdapter(int layoutResId, List<LongevityBean> data, Context context) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LongevityBean item) {
        helper.setText(R.id.txt_longevity_title,item.getTitle());
        Glide.with(context).load(item.getCoverURL())
                .placeholder(R.drawable.pic_loading)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((ImageView) helper.getView(R.id.img_longevity_bg));
    }
}
