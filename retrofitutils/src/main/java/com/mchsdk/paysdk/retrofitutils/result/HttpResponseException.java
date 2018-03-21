package com.mchsdk.paysdk.retrofitutils.result;

/**
 * Created by dingwei on 2017/6/16 0017.
 */

public class HttpResponseException extends RuntimeException {

    private String status;

    public HttpResponseException(String message, String status) {
        super(message);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
