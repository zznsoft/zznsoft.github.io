package com.huasky.elderyun.bean;

import java.io.Serializable;

/** 看护群组联系人列表model
 * Created by pokermman on 2017/2/5.
 */

public class ContactBean implements Serializable {

    private String id;//string 是 id
    private String name;//string 是 姓名
    private String nickname;// string 是 称呼
    private String mobile;//string 是 手机
    private Integer type;// number 是 1-监护人 2-亲属邻里 3-老人
    private Integer flag;//老人是否已经加入群组 0-否 1-是
    private String headImgUrl;//头像地址

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
