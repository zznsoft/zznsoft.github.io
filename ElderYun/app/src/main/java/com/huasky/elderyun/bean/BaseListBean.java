package com.huasky.elderyun.bean;

import java.util.List;

/**
 * Created by pokermman on 2017/2/5.
 */

public class BaseListBean<T> {
    private List<T> contactList;
    private Integer totalCount;
    private List<T> deviceList;
    private List<T> emergencyList;

    public List<T> getEmergencyList() {
        return emergencyList;
    }

    public void setEmergencyList(List<T> emergencyList) {
        this.emergencyList = emergencyList;
    }

    public List<T> getContactList() {
        return contactList;
    }

    public List<T> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<T> deviceList) {
        this.deviceList = deviceList;
    }

    public void setContactList(List<T> contactList) {
        this.contactList = contactList;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
