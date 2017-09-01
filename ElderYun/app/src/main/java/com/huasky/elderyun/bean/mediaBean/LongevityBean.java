package com.huasky.elderyun.bean.mediaBean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by cj on 2017/3/27.
 */

public class LongevityBean implements MultiItemEntity,Serializable {
    public static final int TYPE_SEARCH=0;
    public static final int TYPE_VIDEO=1;

    private int itemType=1;
    private String Description;
    private String Size;
    private String Fps;
    private String Title;
    private String CoverURL;
    private String MediaId;
    private LongevityBeanFile File;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public LongevityBeanFile getFile() {
        return File;
    }

    public void setFile(LongevityBeanFile file) {
        File = file;
    }

    public String getCoverURL() {
        return CoverURL;
    }

    public void setCoverURL(String coverURL) {
        CoverURL = coverURL;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getFps() {
        return Fps;
    }

    public void setFps(String fps) {
        Fps = fps;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public LongevityBean(int itemType) {
        this.itemType = itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public class LongevityBeanFile {
        private String State;
        private String URL;

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }
    }
}
