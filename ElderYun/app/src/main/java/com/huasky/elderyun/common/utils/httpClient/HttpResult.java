/*
 * Copyright (c) 2016. pokermman Inc. All rights reserved.
 */

package com.huasky.elderyun.common.utils.httpClient;



public class HttpResult<T> {

    private int requestId;//requestId  请求Id
    private int errorCode;// errorCode 错误码
    private String errorMsg;// errorMsg 错误信息
    private T responseParams;//responseParams  响应参数

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getResponseParams() {
        return responseParams;
    }

    public void setResponseParams(T responseParams) {
        this.responseParams = responseParams;
    }
}
