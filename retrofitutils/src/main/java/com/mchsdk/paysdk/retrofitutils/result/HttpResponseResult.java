package com.mchsdk.paysdk.retrofitutils.result;


public class HttpResponseResult<T> {

    private static final int SUCCESS_STATUS = 1;

    private String msg;
    private Integer state;
    private T result;

    public boolean isSuccess() {
        return state != null && state == SUCCESS_STATUS;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getState() {
        return state;
    }

    public T getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "msg='" + msg + '\'' +
                ", state=" + state +
                ", result=" + result +
                '}';
    }
}
