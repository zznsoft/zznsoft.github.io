package com.huasky.elderyun.bean.mediaBean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by cj on 2017/3/29.
 */

public class CommentBean implements MultiItemEntity,Serializable {
    public static final int EDIT=0;
    public static final int COMMENTS_CONTENT=1;

    private int itemType;
    private String memberName;
    private int up;
    private int down;
    private String commentTime;
    private String comment;

    public CommentBean(int itemType) {
        this.itemType = itemType;
    }

    public CommentBean(int itemType, String commenter, int up, int down, String comment) {
        this.itemType = itemType;
        this.memberName = commenter;
        this.up = up;
        this.down = down;
        this.comment = comment;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public static String getMeaningfulTime(String time){
        if(time.length()!=14){
            return "";
        }
        int year;
        int month;
        int day;
        int hour;
        int minute;
        int seconds;
        try{
            year= Integer.parseInt(time.substring(0,4));
            month= Integer.parseInt(time.substring(4,6));
            day= Integer.parseInt(time.substring(6,8));
            hour= Integer.parseInt(time.substring(8,10));
            minute= Integer.parseInt(time.substring(10,12));
            seconds= Integer.parseInt(time.substring(12,14));
        }catch (Exception e){
            return "";
        }
        Calendar calendar= Calendar.getInstance();
        int year1=calendar.get(Calendar.YEAR);
        if(year1!=year){
            return (year1-year)+"年前";
        }
        int month1=calendar.get(Calendar.MONTH)+1;
        if(month1!=month){
            return (month1-month)+"月前";
        }
        int day1=calendar.get(Calendar.DAY_OF_MONTH);
        if(day1!=day){
            return (day1-day)+"日前";
        }
        int hour1=calendar.get(Calendar.HOUR_OF_DAY);
        if(hour1!=hour){
            return (hour1-hour)+"小时前";
        }
        int minute1=calendar.get(Calendar.MINUTE);
        if(minute1!=minute){
            return (minute1-minute)+"分钟前";
        }
        return "刚刚";
    }
}
