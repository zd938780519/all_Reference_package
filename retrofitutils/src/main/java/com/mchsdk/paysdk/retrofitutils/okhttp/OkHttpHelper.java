package com.mchsdk.paysdk.retrofitutils.okhttp;


import com.mchsdk.paysdk.retrofitutils.okhttp.interceptor.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by zhangdi on 2017/6/16
 */
public class OkHttpHelper {

    private static OkHttpClient okHttpClient;

    /**
     * 连接超时
     */
    private static final int CONNECT_TIMEOUT = 10;
    /**
     * 读取超时
     */
    private static final int READ_TIMEOUT = 25;
    /**
     * 写入超时
     */
    private static final int WRITE_TIMEOUT = 25;


    private OkHttpHelper() {
    }

    static {

//        CustomHttpsTrust customHttpsTrust = new CustomHttpsTrust(CertificateManager.trustedCertificatesInputStream());//证书授权

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .addInterceptor(new HeaderInterceptor()) //添加 header
                .addInterceptor(new LoggingInterceptor())  //请求信息的打印 ，可在 release 时关闭
//                .sslSocketFactory(customHttpsTrust.sSLSocketFactory, customHttpsTrust.x509TrustManager)// https 配置
                .build();
    }

    public static OkHttpClient getClient() {
        return okHttpClient;
    }

}
