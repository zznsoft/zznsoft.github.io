package com.huasky.elderyun.bean.mediaBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cj on 2017/4/1.
 */

public class CommentInfo implements Serializable {
    private List<CommentBean> commentList;
    private int totalCount;

    public List<CommentBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentBean> commentList) {
        this.commentList = commentList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
