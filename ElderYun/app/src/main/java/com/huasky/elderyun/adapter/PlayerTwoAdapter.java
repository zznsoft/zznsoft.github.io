package com.huasky.elderyun.adapter;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huasky.elderyun.R;
import com.huasky.elderyun.bean.mediaBean.CommentBean;

import java.util.List;

/**
 * Created by cj on 2017/3/29.
 */

public class PlayerTwoAdapter extends BaseMultiItemQuickAdapter<CommentBean,BaseViewHolder> implements TextView.OnEditorActionListener {
    private Context context;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PlayerTwoAdapter(List data, Context context) {
        super(data);
        this.context=context;
        addItemType(CommentBean.EDIT, R.layout.item_playtwo_commenttitle);
        addItemType(CommentBean.COMMENTS_CONTENT,R.layout.item_playtwo_commentcontent);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentBean item) {
        switch (item.getItemType()){
            case CommentBean.EDIT:
                EditText et=(EditText) helper.getConvertView().findViewById(R.id.et_playtwo);
                et.setOnEditorActionListener(this);
                break;
            case CommentBean.COMMENTS_CONTENT:
                helper.setText(R.id.txt_playtwo_comenter,item.getMemberName())
                        .setText(R.id.txt_playtwo_content,item.getComment())
                        .setText(R.id.txt_playtwo_time,CommentBean.getMeaningfulTime(item.getCommentTime()))
                        ;
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        String content=textView.getText().toString();
        ((IPlayTwoView)context).sendComment(content);
        if(i== EditorInfo.IME_ACTION_SEND){
            InputMethodManager im= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(im!=null&&im.isActive()){
                im.hideSoftInputFromWindow(textView.getWindowToken(),0);
                //发送评论
                textView.setText("");
            }
        }
        return false;
    }

    public interface IPlayTwoView{
        public void sendComment(String content);
    }
}
