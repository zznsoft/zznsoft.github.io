package com.huasky.elderyun.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.apkfuns.logutils.LogUtils;
import com.huasky.elderyun.R;

/**
 * Created by cj on 2017/4/25.
 */

public class CircleView extends RelativeLayout {
    private float angle=-90;
    private Bitmap bitmap;
    private Bitmap targetBitmap;
    private Paint paint;
    private Context context;
    private float density=Resources.getSystem().getDisplayMetrics().density;
    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
        init2();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(new BitmapDrawable(targetBitmap));
        }
    }

    private void init() {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(context, R.color.colorBase));
        paint.setStrokeWidth(10*density);
        paint.setStyle(Paint.Style.STROKE);
        bitmap=((BitmapDrawable)context.getResources().getDrawable(R.drawable.bg_circle_medicallist2)).getBitmap();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(bitmap!=null){
            setMeasuredDimension((int)(bitmap.getWidth()+20* density),(int)(bitmap.getHeight()+20*density));
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void init2(){
        Canvas canvas=new Canvas(targetBitmap=Bitmap.createBitmap((int)(bitmap.getWidth()+20*density),(int)(bitmap.getHeight()+20*density), Bitmap.Config.ARGB_8888));
        Rect rect=new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        RectF rectF=new RectF(10*density,10*density,10*density+bitmap.getWidth(),10*density+bitmap.getHeight());
        canvas.drawBitmap(bitmap,rect,rectF,paint);

        //paint circle
        paintCircle(canvas);
    }

    private void paintCircle(Canvas canvas) {
        if(!isDrawCircle){
            return;
        }
        RectF rectF=new RectF(5*density,5*density,getWidth()-5*density,getHeight()-5*density);
        canvas.drawArc(rectF,-90,angle,false,paint);
    }

    private boolean isDrawCircle=false;
    public void stop(){
        isDrawCircle=false;
        init2();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(new BitmapDrawable(targetBitmap));
        }
        if(animator!=null){
            animator.cancel();
        }
    }

    private ValueAnimator animator;
    public void start(){
        isDrawCircle=true;
        animator=ValueAnimator.ofFloat(0,362);
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                angle= (float) animation.getAnimatedValue();
                LogUtils.d(angle);
                init2();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    setBackground(new BitmapDrawable(targetBitmap));
                }
                if(Math.abs(angle-362)<=0.0001){
                    stop();
                }
            }
        });
        animator.start();
    }
}
