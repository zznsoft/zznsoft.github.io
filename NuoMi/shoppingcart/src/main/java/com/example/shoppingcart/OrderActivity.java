package com.example.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView orderRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String arr,orderinfo,cost,merchantID;
    private Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        pay= (Button) findViewById(R.id.pay);
        Intent intent=getIntent();
        Log.e("OrderActivity",intent.getStringExtra("arr"));
        arr=intent.getStringExtra("arr");
        orderinfo=intent.getStringExtra("orderinfo");
        cost=intent.getStringExtra("cost");
        merchantID=intent.getStringExtra("merchantID");

        orderRecyclerView= (RecyclerView) findViewById(R.id.orderRecyclerView);
        mLayoutManager = new GridLayoutManager(this,3, GridLayoutManager.VERTICAL, false);
        mAdapter = new MyAdapter(getData());
        // 设置布局管理器
        orderRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        orderRecyclerView.setAdapter(mAdapter);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        post(orderinfo,cost,merchantID);
                    }
                }).start();
//               finish();
            }
        });
    }

    private String post(String orderinfo, String cost,String merchantID) {
        String path = "http://106.15.198.49/nuomi/order.php";
        Log.e("post","开始");
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");
            Log.e("post","连接设置");
            //数据准备
            String data =orderinfo.replaceAll("￥","")+":"+cost.split("￥")[1]+":"+merchantID;
            //至少要设置的两个请求头
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            //post的方式提交实际上是留的方式提交给服务器
            Log.e("post","数据提交"+data);
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            //获得结果码
            int responseCode = connection.getResponseCode();
            if(responseCode ==200){
                //请求成功
                InputStream is = connection.getInputStream();
                Log.e("post","请求成功"+convertStreamToString(is));

                finish();

                return convertStreamToString(is);
            }else {
                //请求失败
                Log.e("post","请求失败");
                Toast.makeText(OrderActivity.this,"提交失败，请检查您的网络是否正常",Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    public  String convertStreamToString(InputStream is) {
        /*
          * To convert the InputStream to String we use the BufferedReader.readLine()
          * method. We iterate until the BufferedReader return null which means
          * there's no more data to read. Each line will appended to a StringBuilder
          * and returned as String.
          */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        for(int i = 0; i < arr.split(",").length; i++) {
            data.add(arr.split(",")[i]);
        }
        return data;
    }
}

 class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private ArrayList<String> mData;

    public MyAdapter(ArrayList<String> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        holder.mTv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}