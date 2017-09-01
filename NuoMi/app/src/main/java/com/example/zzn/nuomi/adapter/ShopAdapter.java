package com.example.zzn.nuomi.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zzn.nuomi.R;
import com.example.zzn.nuomi.model.MyResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZZN on 2017/8/3.
 */

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MyResult.Results> picData = new ArrayList<>();
    private List<MyResult.Results> textData = new ArrayList<>();
    private MyItemOnClickListener mMyItemOnClickListener = null;
    //type
    public static final int TYPE_TYPE3 = 0xff03;


    public ShopAdapter(Context context) {
        this.context = context;
    }

    public List<MyResult.Results> getPicData() {
        return picData;
    }

    public void setPicData(List<MyResult.Results> picData) {
        this.picData = picData;
    }

    public List<MyResult.Results> getTextData() {
        return textData;
    }

    public void setTextData(List<MyResult.Results> textData) {
        this.textData = textData;
    }

    public void setItemOnClickListener(MyItemOnClickListener listener){
        mMyItemOnClickListener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderType(LayoutInflater.from(parent.getContext()).inflate(R.layout.complex_item2, parent, false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            bindType((HolderType) holder, position);
    }

    @Override
    public int getItemCount() {
        if (picData != null)
            return picData.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
            return TYPE_TYPE3;
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return gridManager.getSpanCount();
                }
            });
        }
    }

    private void bindType(HolderType holder, int position) {
        holder.setDataAndRefreshUI(picData.get(position), textData.get(position));
    }


    public class HolderType extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTextView;
        private ImageView mImageView;
        private String id;

        public HolderType(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_img_type1);
            mTextView = (TextView) itemView.findViewById(R.id.item_tv_type1);
            itemView.setOnClickListener(this);
        }

        public void setDataAndRefreshUI(MyResult.Results picdata, MyResult.Results textdata) {
            id=textdata.getId();
            mTextView.setText(textdata.getName());
            // 自动加载图片
            Picasso.with(context)
                    .load(picdata.getImg())
                    .error(R.mipmap.home)
                    .fit()
                    .centerCrop()
                    .into(mImageView);
        }

        @Override
        public void onClick(View view) {
            if(mMyItemOnClickListener!=null){
                mMyItemOnClickListener.onItemOnClick(view,id);
            }
        }
    }
}
