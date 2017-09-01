package com.example.zzn.nuomi;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zzn.nuomi.fragment.MainFragment;
import com.example.zzn.nuomi.fragment.MeFragment;
import com.example.zzn.nuomi.fragment.OrdeFragment;
import com.example.zzn.nuomi.fragment.ShopFragment;
import com.orhanobut.hawk.Hawk;

public class MainActivity extends AppCompatActivity {
    private Fragment MainFragment,ShopFragment,OrdeFragment,MeFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction,transaction2;
    private RadioGroup rg;
    private RadioButton rb_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        rg= (RadioGroup) findViewById(R.id.rg);
        rb_main= (RadioButton) findViewById(R.id.rb_main);
    }

    private void initData() {
        Hawk.put("isLogin",true);
        Log.e("MAIN_isLogin", String.valueOf(Hawk.contains("isLogin")));
        rb_main.setChecked(true);
        manager=getSupportFragmentManager();
        MainFragment=new MainFragment();
        transaction=manager.beginTransaction();
        transaction.add(R.id.fragment_main,MainFragment);
        transaction.commit();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                transaction2=manager.beginTransaction();
                if (MainFragment!=null){
                    transaction2.hide(MainFragment);
                }if (ShopFragment!=null){
                    transaction2.hide(ShopFragment);
                }
                if (OrdeFragment!=null){
                    transaction2.remove(OrdeFragment);
                }
                if (MeFragment!=null){
                    transaction2.hide(MeFragment);
                }
                switch (i){
                    case R.id.rb_main:
                        if (MainFragment!=null){
                            transaction2.show(MainFragment);
                        }else {
                            MainFragment=new MainFragment();
                            transaction2.add(R.id.fragment_main,MainFragment);
                        }
                        break;
                    case R.id.rb_shop:
                        if (ShopFragment!=null){
                            transaction2.show(ShopFragment);
                        }else{
                            ShopFragment=new ShopFragment();
                            transaction2.add(R.id.fragment_main,ShopFragment);
                        }
                        break;
                    case R.id.rb_order:
//                        if (OrdeFragment!=null){
//                            transaction2.show(OrdeFragment);
//                        }else{
                            OrdeFragment=new OrdeFragment();
                            transaction2.add(R.id.fragment_main,OrdeFragment);
//                        }
                        break;
                    case R.id.rb_me:
                        if (MeFragment!=null){
                            transaction2.show(MeFragment);
                        }else{
                            MeFragment=new MeFragment();
                            transaction2.add(R.id.fragment_main,MeFragment);
                        }
                        break;
                }
                transaction2.commit();
            }
        });
    }

}
