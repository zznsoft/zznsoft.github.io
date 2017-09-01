package com.example.zzn.nuomi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zzn.nuomi.R;
import com.example.zzn.nuomi.http.GankUrl;
import com.example.zzn.nuomi.http.NetWork;
import com.example.zzn.nuomi.model.AllResult;
import com.example.zzn.nuomi.model.WeatherResult;
import com.example.zzn.nuomi.util.Localize;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by ZZN on 2017/8/9.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private CurriculumAdapter curriculumAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<AllResult.Results> picData = new ArrayList<>();
    private List<AllResult.Results> textData = new ArrayList<>();
    private int addposition=0;
    //type
    public static final int TYPE_TYPE1 = 0xff01;
    public static final int TYPE_TYPE2 = 0xff02;
    public static final int TYPE_TYPE3 = 0xff03;
    public static final int TYPE_HEAD = 0xff04;
    public static final int TYPE_HEAD2 = 0xff05;
    public static final int TYPE_HEAD3 = 0xff06;

    public MainAdapter(Context context, CurriculumAdapter curriculumAdapter, RecyclerView.LayoutManager layoutManager) {
        this.context = context;
        this.curriculumAdapter = curriculumAdapter;
        this.layoutManager=layoutManager;
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
        switch (viewType) {
            case TYPE_TYPE1:
                return new HolderType4(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_curriculum, parent, false));
            case TYPE_TYPE2:
                return new HolderType2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false));
            case TYPE_TYPE3:
                return new HolderType(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foods, parent, false));
            case TYPE_HEAD:
                return new HolderType3(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_head, parent, false));
            case TYPE_HEAD2:
                return new HolderType3(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_head2, parent, false));
            case TYPE_HEAD3:
                return new HolderType3(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_head3, parent, false));
            default:
                Log.d("error", "viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HolderType2) {
            bindType2((HolderType2) holder, position);
        }
        if (holder instanceof HolderType) {
            bindType((HolderType) holder, position);
        }
        if (holder instanceof HolderType3) {
            bindType3((HolderType3) holder, position);
        }
        if (holder instanceof HolderType4) {
            bindType4((HolderType4) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (picData != null)
            return picData.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (1 <= position && position <= 1+addposition) {
            return TYPE_TYPE1;
        } else if (position == 2+addposition) {
            return TYPE_HEAD2;
        } else if (position == 3+addposition) {
            return TYPE_TYPE2;
        } else if (position == 4+addposition) {
            return TYPE_HEAD3;
        } else {
            return TYPE_TYPE3;
        }
    }

//    @Override
//    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    int type = getItemViewType(position);
//                    switch (type) {
//                        case TYPE_TYPE1:
//                        case TYPE_TYPE3:
//                            return gridManager.getSpanCount();
//                        case TYPE_TYPE2:
//                            return 3;
//                        default:
//                            return gridManager.getSpanCount();
//                    }
//                }
//            });
//        }
//    }

    /////////////////////////////
    private void bindType2(HolderType2 holder, int position) {
//        holder.setDataAndRefreshUI(picData.get(position), textData.get(position));
        holder.checkAppLogin();
    }

    private void bindType(HolderType holder, int position) {
        holder.setDataAndRefreshUI(picData.get(position), textData.get(position));
    }

    private void bindType3(HolderType3 holder, int position) {
    }
    private void bindType4(HolderType4 holder, int position) {
        holder.initData();
    }
    /////////////////////////////
    public class HolderType4 extends RecyclerView.ViewHolder{
        private RecyclerView recyclerview_test;
        public HolderType4(View itemView) {
            super(itemView);
            recyclerview_test=itemView.findViewById(R.id.text_recyclerView);
        }
        public void initData(){
            recyclerview_test.setLayoutManager(new GridLayoutManager(recyclerview_test.getContext(), 1, GridLayoutManager.VERTICAL, false));
            recyclerview_test.setAdapter(curriculumAdapter);
            getNetDataTest();
        }

        //刷新获取网络数据
        private void getNetDataTest() {
            int contentQuantity=1;
            Observable.combineLatest(NetWork.getGankApi().getAllDate(GankUrl.FLAG_GIRLS, contentQuantity, 1), NetWork.getGankApi().getAllDate(GankUrl.FLAG_Android, contentQuantity, 1),
                    new Func2<AllResult, AllResult, Void>() {
                        @Override
                        public Void call(AllResult PicResult, AllResult TextResult) {
                            curriculumAdapter.setPicData(PicResult.getResults());
                            curriculumAdapter.setTextData(TextResult.getResults());
                            return null;
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            curriculumAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }

    public class HolderType3 extends RecyclerView.ViewHolder {

        public HolderType3(View itemView) {
            super(itemView);
        }
    }

    public class HolderType2 extends RecyclerView.ViewHolder {
        private TextView city,temp,weather,wind,humidity,sendibleTemp;
        private LinearLayout weatherbg;
        private String imgid;

        public HolderType2(View itemView) {
            super(itemView);
            city = (TextView) itemView.findViewById(R.id.city);
            temp=itemView.findViewById(R.id.temp);
            weather=itemView.findViewById(R.id.weather);
            wind=itemView.findViewById(R.id.wind);
            humidity=itemView.findViewById(R.id.humidity);
            sendibleTemp=itemView.findViewById(R.id.sendibleTemp);
            weatherbg=itemView.findViewById(R.id.weatherbg);
        }

        public  void checkAppLogin() {
            Call<WeatherResult> call;
            String localize;
            try {
                localize= Localize.getLngAndLat(context);
            }catch (Exception e){
                localize="120.612214,29.982864";
            }
            call = (Call<WeatherResult>) NetWork.getWeatherApi().getWeather(localize.split(",")[1]+","+
                    localize.split(",")[0] );
            call.enqueue(new Callback<WeatherResult>() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onResponse(Call<WeatherResult> call, retrofit2.Response<WeatherResult> response) {
                    if (response.body().getMsg()!="ok"){
                        Log.e("success", response.message()+response.body().getMsg()+
                                response.body().getResult().getCity());
                        city.setText(response.body().getResult().getCity());
                        temp.setText(response.body().getResult().getTemp()+"°");
                        temp.setTextColor(context.getResources().getColor(R.color.white));
                        weather.setText(response.body().getResult().getWeather()+" | 空气"+response.body().getResult().getAqi().getQuality()+" "+response.body().getResult().getAqi().getAqi());
                        wind.setText(response.body().getResult().getWinddirect()+"\n"+response.body().getResult().getWindpower());
                        humidity.setText("相对湿度\n"+response.body().getResult().getHumidity()+"%");
                        int sendibleTemptv= Integer.parseInt(response.body().getResult().getTemp())+(int)(5-Math.random()*10);
                        sendibleTemp.setText("体感温度\n"+sendibleTemptv+"°");
                        Log.e("random", String.valueOf((int)(5-Math.random()*10)));
                        switch (Integer.parseInt(response.body().getResult().getImg())){
                            case 0:imgid="1.png";
                                break;
                            default:imgid="2.png";break;
                        }
                        Picasso.with(context)
                                .load("http://namiapp.oss-cn-beijing.aliyuncs.com/weather/"+imgid)
                                .into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        weatherbg.setBackground(new BitmapDrawable(context.getResources(),bitmap));
                                    }

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {
                                        Log.e("MainActivity","-----------onBitmapFailed--------------");
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                                        Log.e("MainActivity","-----------onPrepareLoad--------------");
                                    }
                                });
                    }
                }
                @Override
                public void onFailure(Call<WeatherResult> call, Throwable t) {
                    System.out.println("请求失败");
                    System.out.print(t.getMessage());
                }
            });}
    }

    public class HolderType extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;

        public HolderType(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_img_type1);
            mTextView = (TextView) itemView.findViewById(R.id.item_tv_type1);
        }

        public void setDataAndRefreshUI(AllResult.Results picdata, AllResult.Results textdata) {
            mTextView.setText(textdata.getDesc());
            // 自动加载图片
            Picasso.with(context)
                    .load(picdata.getUrl())
                    .error(R.mipmap.home)
                    .fit()
                    .centerCrop()
                    .into(mImageView);
        }
    }

}

