package com.mchsdk.paysdk.retrofitutils.okhttp.interceptor;

import android.util.Log;

import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public final class ReplaceRespBodyInterceptor implements Interceptor {

    private static final String TAG = ReplaceRespBodyInterceptor.class.getName();

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String msg = "";
        long t1 = System.nanoTime();
        try {
            if (request.method().compareToIgnoreCase("post") == 0) {
                msg += String.format("request\n%s\n", request.url());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = chain.proceed(request);
        int responseCode = response.code();

        long t2 = System.nanoTime();
        String bodyString = response.body().string();
        msg += String.format(Locale.getDefault(), "response\ntime: %.1fms", ((t2 - t1) / 1e6d));
        Log.d(TAG,msg);
        Log.d(TAG,bodyString);

        if(responseCode!=200){
            throw new HttpResponseException("网络异常", String.valueOf(responseCode));
        }

        try {
            JSONObject jsonObject=new JSONObject(bodyString);
            int state=jsonObject.optInt("state");
            if (state == 1) {
                return response.newBuilder().body(ResponseBody.create(response.body().contentType(), jsonObject.optString("result"))).build();
            } else {
                throw new HttpResponseException(jsonObject.optString("messgae"), String.valueOf(state));
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

        throw new HttpResponseException("网络异常","-1024");
    }
}