/*
 * Copyright (c) 2016. pokermman Inc. All rights reserved.
 */

package com.huasky.elderyun.common.utils.httpClient;
//
//import static com.huasky.doctoryun.common.Global.*;
//import static com.huasky.doctoryun.common.Global.ERROR_CODE_ACCOUNT_EXIST;
//import static com.huasky.doctoryun.common.Global.ERROR_CODE_BUSINESS;
//import static com.huasky.doctoryun.common.Global.ERROR_CODE_ELDER_HAS_SAMETYPE_DEVICE;
//import static com.huasky.doctoryun.common.Global.ERROR_CODE_INVALID_PARAM;
//import static com.huasky.doctoryun.common.Global.ERROR_CODE_INVALID_PWD;
//import static com.huasky.doctoryun.common.Global.ERROR_CODE_INVALID_SMSCODE;
//import static com.huasky.doctoryun.common.Global.ERROR_CODE_NOACCOUNT;
//import static com.huasky.doctoryun.common.Global.ERROR_CODE_NOTLOGIN;
//import static com.huasky.doctoryun.common.Global.ERROR_CODE_PWD_NOTMATCH;
//import static com.huasky.doctoryun.common.Global.ERROR_CODE_SYSTEM;
//import static com.huasky.doctoryun.common.Global.ERROR_MSG_ACCOUNT_EXIST;
//import static com.huasky.doctoryun.common.Global.ERROR_MSG_BUSINESS;
//import static com.huasky.doctoryun.common.Global.ERROR_MSG_ELDER_HAS_SAMETYPE_DEVICE;
//import static com.huasky.doctoryun.common.Global.ERROR_MSG_INVALID_PARAM;
//import static com.huasky.doctoryun.common.Global.ERROR_MSG_INVALID_PWD;
//import static com.huasky.doctoryun.common.Global.ERROR_MSG_INVALID_SMSCODE;
//import static com.huasky.doctoryun.common.Global.ERROR_MSG_NOACCOUNT;
//import static com.huasky.doctoryun.common.Global.ERROR_MSG_NOTLOGIN;
//import static com.huasky.doctoryun.common.Global.ERROR_MSG_PWD_NOTMATCH;
//import static com.huasky.doctoryun.common.Global.ERROR_MSG_SYSTEM;

/**
 * Created by pokermman on 2016/12/23.
 */

public class ApiException extends RuntimeException {

    private static String message;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(HttpResult result) {
        this(getApiExceptionMessage(result));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code 错误码
     * @return 返回错误信息
     */
    private static String getApiExceptionMessage(int code) {
        switch (code) {
//            case ERROR_CODE_SYSTEM:
//                message = ERROR_MSG_SYSTEM;
//                break;
//            case ERROR_CODE_BUSINESS:
//                message = ERROR_MSG_BUSINESS;
//                break;
//            case ERROR_CODE_INVALID_PARAM:
//                message = ERROR_MSG_INVALID_PARAM;
//                break;
//            case ERROR_CODE_PWD_NOTMATCH:
//                message = ERROR_MSG_PWD_NOTMATCH;
//                break;
//            case ERROR_CODE_INVALID_SMSCODE:
//                message = ERROR_MSG_INVALID_SMSCODE;
//                break;
//            case ERROR_CODE_NOTLOGIN:
//                message = ERROR_MSG_NOTLOGIN;
//                break;
//            case ERROR_CODE_INVALID_PWD:
//                message = ERROR_MSG_INVALID_PWD;
//                break;
//            case ERROR_CODE_NOACCOUNT:
//                message = ERROR_MSG_NOACCOUNT;
//                break;
//            case ERROR_CODE_ACCOUNT_EXIST:
//                message = ERROR_MSG_ACCOUNT_EXIST;
//                break;
//            case ERROR_CODE_ELDER_HAS_SAMETYPE_DEVICE:
//                message = ERROR_MSG_ELDER_HAS_SAMETYPE_DEVICE;
//                break;
            default:
                message = "errorCode：" + code;
        }
        return message;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param codeResult 错误信息
     * @return 返回错误信息
     */
    private static String getApiExceptionMessage(HttpResult codeResult) {

        switch (codeResult.getErrorCode()) {
//            case ERROR_CODE_SYSTEM:
//                message = ERROR_MSG_SYSTEM;
//                break;
//            case ERROR_CODE_BUSINESS:
//                message = ERROR_MSG_BUSINESS;
//                break;
//            case ERROR_CODE_INVALID_PARAM:
//                message = ERROR_MSG_INVALID_PARAM;
//                break;
//            case ERROR_CODE_PWD_NOTMATCH:
//                message = ERROR_MSG_PWD_NOTMATCH;
//                break;
//            case ERROR_CODE_INVALID_SMSCODE:
//                message = ERROR_MSG_INVALID_SMSCODE;
//                break;
//            case ERROR_CODE_NOTLOGIN:
//                message = ERROR_MSG_NOTLOGIN;
//                break;
//            case ERROR_CODE_INVALID_PWD:
//                message = ERROR_MSG_INVALID_PWD;
//                break;
//            case ERROR_CODE_NOACCOUNT:
//                message = ERROR_MSG_NOACCOUNT;
//                break;
//            case ERROR_CODE_ACCOUNT_EXIST:
//                message = ERROR_MSG_ACCOUNT_EXIST;
//                break;
//            case ERROR_CODE_ELDER_HAS_SAMETYPE_DEVICE:
//                message = ERROR_MSG_ELDER_HAS_SAMETYPE_DEVICE;
//                break;
            default:
                message = codeResult.getErrorMsg();
        }
        return message;
    }
}
