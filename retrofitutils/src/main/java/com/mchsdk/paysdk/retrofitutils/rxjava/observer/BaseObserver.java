package com.mchsdk.paysdk.retrofitutils.rxjava.observer;

import android.support.annotation.CallSuper;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.mchsdk.paysdk.retrofitutils.CommonException;
import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<T> implements Observer<T> {
    private final String TAG = BaseObserver.class.getSimpleName();


    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }


//      if (e instanceof HttpException) {
//        HttpException httpException = (HttpException) e;
//        responseException = new HttpResponseException("网络请求出错", String.valueOf(httpException.code()));
//    } else
    @Override
    public void onError(Throwable e) {
        HttpResponseException responseException;
       if (e instanceof HttpResponseException) {
            responseException = (HttpResponseException) e;
        } else if (e instanceof JsonSyntaxException) {
            responseException = new HttpResponseException(CommonException.JSON_PARSE_EXCEPTION_MSG + " : " + e.getMessage(), String.valueOf(CommonException.JSON_PARSE_EXCEPTION));//json解析数据异常
        } else {//其他或者没网会走这里
            responseException = new HttpResponseException("网络异常,请稍后重试", "-1024");
        }

        onFailed(responseException);
    }

    @Override
    public void onComplete() {
    }

    protected abstract void onSuccess(T t);

    @CallSuper
    protected void onFailed(HttpResponseException responseException) {
        Log.i(TAG, "--- HttpResponseException ---" + responseException.getMessage() + "(" + responseException.getStatus() + ")");
//        ToastUtils.show(responseException.getMessage() + "(" + responseException.getStatus() + ")");
    }
}
