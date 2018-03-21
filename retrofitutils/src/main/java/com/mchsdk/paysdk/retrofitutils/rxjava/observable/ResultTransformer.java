package com.mchsdk.paysdk.retrofitutils.rxjava.observable;


import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;
import com.mchsdk.paysdk.retrofitutils.result.HttpResponseResult;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


public class ResultTransformer {

    private static final String TAG = ResultTransformer.class.getSimpleName();

    public static <T> ObservableTransformer<HttpResponseResult<T>, T> transformer() {
        return new ObservableTransformer<HttpResponseResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpResponseResult<T>> upstream) {
                return upstream
                        .flatMap(ResultTransformer.<T>flatMap())
                        .compose(SchedulerTransformer.<T>transformer());
            }
        };
    }

    private static <T> Function<HttpResponseResult<T>, ObservableSource<T>> flatMap() {
        return new Function<HttpResponseResult<T>, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(@NonNull final HttpResponseResult<T> tHttpResponseResult) throws Exception {
                return new Observable<T>() {
                    @Override
                    protected void subscribeActual(Observer<? super T> observer) {
                        if (tHttpResponseResult.isSuccess()) {
//                            String jsonData = (String) tHttpResponseResult.getResult();
//                            Log.i(TAG,"jsonData====="+jsonData);
                            observer.onNext(tHttpResponseResult.getResult());
                            observer.onComplete();
                        } else {
                            observer.onError(new HttpResponseException(tHttpResponseResult.getMsg(), String.valueOf(tHttpResponseResult.getState())));
                        }
                    }
                };
            }
        };
    }







}
