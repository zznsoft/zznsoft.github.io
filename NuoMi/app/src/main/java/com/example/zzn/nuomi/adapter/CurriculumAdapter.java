package com.example.zzn.nuomi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zzn.nuomi.R;
import com.example.zzn.nuomi.model.AllResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZZN on 2017/8/10.
 */

public class CurriculumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<AllResult.Results> picData = new ArrayList<>();
    private List<AllResult.Results> textData = new ArrayList<>();
    //type
    public static final int TYPE_TYPE = 0xff01;

    public CurriculumAdapter(Context context) {
        this.context = context;
    }

    public List<AllResult.Results> getPicData() {
        return picData;
    }

    public void setPicData(List<AllResult.Results> picData) {
        this.picData = picData;
    }

    public List<AllResult.Results> getTextData() {
        return textData;
    }

    public void setTextData(List<AllResult.Results> textData) {
        this.textData = textData;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderType(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curriculum, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            bindType((HolderType) holder, position);
    }

    @Override
    public int getItemCount() {
        if (picData != null)
            return picData.size();
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
            return TYPE_TYPE;
    }

    /////////////////////////////
    private void bindType(HolderType holder, int position) {
//        holder.setDataAndRefreshUI( textData.get(position));
    }
    /////////////////////////////
    public class HolderType extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public HolderType(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.course_name);
        }

        public void setDataAndRefreshUI( AllResult.Results textdata) {
            mTextView.setText(textdata.getDesc());
        }
    }}


