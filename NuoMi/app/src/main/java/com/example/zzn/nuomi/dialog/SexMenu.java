package com.example.zzn.nuomi.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zzn.nuomi.R;

/**
 * Created by ZZN on 2017/8/17.
 */

public class SexMenu implements View.OnTouchListener,View.OnClickListener{
    private PopupWindow popupWindow;
    private View mMenuView;
    private Activity mContext;
    private TextView boy,gril;
    private View.OnClickListener clickListener;

    public SexMenu(Activity context, View.OnClickListener clickListener){
        LayoutInflater inflater=LayoutInflater.from(context);
        this.mContext=context;
        this.clickListener=clickListener;
        mMenuView=inflater.inflate(R.layout.popup_dialog,null);
        boy=mMenuView.findViewById(R.id.top);
        gril=mMenuView.findViewById(R.id.bottom);
        boy.setText("男");
        gril.setText("女");

        popupWindow=new PopupWindow(mMenuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        mMenuView.setOnTouchListener(this);
        boy.setOnClickListener(this);
        gril.setOnClickListener(this);
        backgroundAlpha(0.4f);
    }

    private void backgroundAlpha(float f) {
        WindowManager.LayoutParams lp =mContext.getWindow().getAttributes();
        lp.alpha = f;
        mContext.getWindow().setAttributes(lp);
    }

    public void show(){
        //得到当前activity的rootView
        View rootView=((ViewGroup)mContext.findViewById(android.R.id.content)).getChildAt(0);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int height = mMenuView.findViewById(R.id.rg_sex).getTop();
        int y=(int) motionEvent.getY();
        if(motionEvent.getAction()==MotionEvent.ACTION_UP){
            if(y<height){
                popupWindow. dismiss();
                backgroundAlpha(1);
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        popupWindow.dismiss();
        clickListener.onClick(view);
        backgroundAlpha(1);
    }

}
