package com.example.zzn.nuomi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zzn.nuomi.R;

/**
 * Created by ZZN on 2017/9/1.
 */

public class OrderInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String[] str;
    public void setOrderinfo(String orderinfo) {
        str=orderinfo.split(",");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderInfoAdapter.HolderType(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderinfo_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindType((OrderInfoAdapter.HolderType) holder, position);
    }

    @Override
    public int getItemCount() {
        if (str!=null){
            return str.length;
        }
        return 0;
    }

    private void bindType(OrderInfoAdapter.HolderType holder, int position) {
        holder.setDataAndRefreshUI(str[position]);
    }
    /////////////////////////////
    public class HolderType extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public HolderType(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_orderinfo_rv_tv);
        }

        public void setDataAndRefreshUI(String str) {
            mTextView.setText(str);
        }

    }
}
