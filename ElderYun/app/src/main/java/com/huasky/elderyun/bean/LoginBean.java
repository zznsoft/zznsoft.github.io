/*
 * Copyright (c) 2016. pokermman Inc. All right reserved.
 */

package com.huasky.elderyun.bean;

/**
 * Created by pokermman on 2016/12/30.
 */

public class LoginBean {

    private String headImgUrl;//头像url地址

    private String token;   //有效期在服务端配置，默认30天。在token有效期内，任何客户端通过此接口登录，均会重新计算token有效期，token值不改变。若token超过有效期失效了，用户再通过此接口登录，系统将重新生成token并计算有效期，token值会改变。
    private String elderId;    //帐号Id
    private String elderName;  //姓名
    private String mobile;  //手机号码
    //    private int sex;     //性别
//    private int age;     //年龄
    private String sex;     //性别
    private String age;     //年龄
    private String weight;  //体重
    private String height;  //升高

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getElderId() {
        return elderId;
    }

    public void setElderId(String elderId) {
        this.elderId = elderId;
    }

    public String getElderName() {
        return elderName;
    }

    public void setElderName(String elderName) {
        this.elderName = elderName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public int getSex() {
//        return sex;
//    }
//
//    public void setSex(int sex) {
//        this.sex = sex;
//    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
