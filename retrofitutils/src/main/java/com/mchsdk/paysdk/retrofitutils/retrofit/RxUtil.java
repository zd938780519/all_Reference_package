package com.mchsdk.paysdk.retrofitutils.retrofit;


import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class RxUtil {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    public static RequestBody getBody(JSONObject parama){
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),parama.toString());
        return body;
    }

    public static RequestBody getMapBody(Map parama){
        JSONObject paramas = new JSONObject(parama);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),paramas.toString());
        return body;
    }

    public static String getBodySoucre(ResponseBody responseBody){
        String src = null;
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if(contentType != null){
            charset = contentType.charset(UTF8);
            src =  buffer.clone().readString(charset);
        }
        return src;
    }
}
