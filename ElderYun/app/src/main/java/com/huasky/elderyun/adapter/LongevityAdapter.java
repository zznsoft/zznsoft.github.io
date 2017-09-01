package com.huasky.elderyun.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huasky.elderyun.R;
import com.huasky.elderyun.activity.PlaySearchActivity;
import com.huasky.elderyun.bean.mediaBean.LongevityBean;

import java.util.List;

/**
 * Created by cj on 2017/3/24.
 */

public class LongevityAdapter extends BaseMultiItemQuickAdapter<LongevityBean,BaseViewHolder> {
    private float density= Resources.getSystem().getDisplayMetrics().density;
    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public LongevityAdapter(List<LongevityBean> data, Context context) {
        super(data);
        addItemType(LongevityBean.TYPE_SEARCH, R.layout.item_longevity_search);
        addItemType(LongevityBean.TYPE_VIDEO,R.layout.item_longevity);
        this.context=context;
    }


    @Override
    protected void convert(BaseViewHolder helper, LongevityBean item) {
        if(helper.getLayoutPosition()==1){
            RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (180*density));
            lp.bottomMargin= (int) (15*density);
            lp.leftMargin= (int) (7.5*density);
            lp.rightMargin= (int) (7.5*density);
            helper.getConvertView().setLayoutParams(lp);

        }else if(helper.getLayoutPosition()>1){
            GridLayoutManager.LayoutParams lp= (GridLayoutManager.LayoutParams) helper.convertView.getLayoutParams();
            lp.height=(int)(100*density);
            helper.getConvertView().setLayoutParams(lp);
        }
        switch (item.getItemType()){
            case 0:  //搜索
                LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) ((ViewGroup)helper.convertView).getChildAt(0).getLayoutParams();
                lp.topMargin= (int) (15*density);
                lp.bottomMargin= (int) (15*density);
                ((ViewGroup)helper.convertView).getChildAt(0).setLayoutParams(lp);
                SearchView sv= (SearchView) helper.getConvertView().findViewById(R.id.sv_longevity);
                sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        Intent intent=new Intent(context, PlaySearchActivity.class);
                        intent.putExtra("SearchContent",s);
                        context.startActivity(intent);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });
                break;
            case 1:
                helper.setText(R.id.txt_longevity_title,item.getTitle());
                Glide.with(context).load(item.getCoverURL())
                        .placeholder(R.drawable.pic_loading)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into((ImageView) helper.getView(R.id.img_longevity_bg));
                break;
        }

    }
}
