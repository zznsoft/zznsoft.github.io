package com.example.zzn.nuomi.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by ZZN on 2017/8/31.
 */

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MyResult.Results> picData = new ArrayList<>();
    private List<MyResult.Results> textData = new ArrayList<>();
    private MyItemOnClickListener mMyItemOnClickListener = null;
    private OrderInfoAdapter orderInfoAdapter;
    //type
    public static final int TYPE_TYPE3 = 0xff03;


    public OrderAdapter(Context context) {
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
        return new HolderType(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderinfo, parent, false));
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
        private TextView orderinfo,time,price,state,order_merchant_name;
        private ImageView order_merchant_img;
        private String id;
        private RecyclerView recyclerView;

        public HolderType(View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.order_info_rv);
            orderInfoAdapter=new OrderInfoAdapter();

            orderinfo = (TextView) itemView.findViewById(R.id.order_info);
            time=itemView.findViewById(R.id.order_time);
            price=itemView.findViewById(R.id.order_price);
            state=itemView.findViewById(R.id.order_state);
            order_merchant_img=itemView.findViewById(R.id.order_merchant_img);
            order_merchant_name=itemView.findViewById(R.id.order_merchant_name);
            itemView.setOnClickListener(this);
        }

        public void setDataAndRefreshUI(MyResult.Results picdata, MyResult.Results textdata) {
            id=textdata.getId();
//            orderinfo.setText(textdata.getFood());
            time.setText("下单时间："+textdata.getTime());
            recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3, GridLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(orderInfoAdapter);
            Log.e("orderInfoAdapter",textdata.getFood());
            orderInfoAdapter.setOrderinfo(textdata.getFood());
            price.setText("总价："+textdata.getPrice());
            state.setText(textdata.getState());
            order_merchant_name.setText(textdata.getName());
            Picasso.with(context)
                    .load(picdata.getImg())
                    .error(R.mipmap.home)
                    .fit()
                    .centerCrop()
                    .into(order_merchant_img);
        }

        @Override
        public void onClick(View view) {
            if(mMyItemOnClickListener!=null){
                mMyItemOnClickListener.onItemOnClick(view,id);
            }
        }
    }
}

