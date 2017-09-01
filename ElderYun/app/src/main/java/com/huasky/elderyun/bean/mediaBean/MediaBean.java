package com.huasky.elderyun.bean.mediaBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cj on 2017/3/27.
 */

public class MediaBean implements Serializable {
    private List<LongevityBean> Media;

    public List<LongevityBean> getMedia() {
        return Media;
    }

    public void setMedia(List<LongevityBean> media) {
        Media = media;
    }
}
