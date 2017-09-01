package com.huasky.elderyun.bean.mediaBean;

import java.io.Serializable;

/**
 * Created by cj on 2017/3/27.
 */

public class MediaInfo implements Serializable {
    private int TotalNum;
    private int PageSize;
    private String RequestId;
    private int PageNumber;
    private MediaBean MediaList;

    public int getTotalNum() {
        return TotalNum;
    }

    public void setTotalNum(int totalNum) {
        TotalNum = totalNum;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public int getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(int pageNumber) {
        PageNumber = pageNumber;
    }

    public MediaBean getMediaList() {
        return MediaList;
    }

    public void setMediaList(MediaBean mediaList) {
        this.MediaList = mediaList;
    }
}
