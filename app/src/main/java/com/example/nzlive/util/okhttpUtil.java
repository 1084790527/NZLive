package com.example.nzlive.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 10847 on 10/9/2018.
 */

public class okhttpUtil {
    private static final String TAG = "AAA";

    /**
     * okhttp3 异步 Get方法
     */
    private void okHttp_asynchronousGet(){
        try {
            Log.i("wxy","main thread id is "+Thread.currentThread().getId());
            String url = "http://www.weather.com.cn/data/sk/101230306.html";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    // 注：该回调是子线程，非主线程
                    Log.i(TAG,"callback thread id is "+Thread.currentThread().getId());
                    Log.i(TAG,response.body().string());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * okhttp POST方式请求（提交表单方式）
     */
    private void okHttp_postFromParameters() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 请求完整url：http://api.k780.com:88/?app=weather.future&weaid=1&&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json
                    String url = "http://192.168.0.177:8888/textjson";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder().add("a","1").add("b","2").build();
                    Request request = new Request.Builder().url(url).post(formBody).build();
                    okhttp3.Response response = okHttpClient.newCall(request).execute();
                    Log.i(TAG, response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * okhttp POST方式请求（提交json方式）
     */
    private void okHttp_postFromJson(){
        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("a","11");
            jsonObject.put("b","22");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();

        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);

        final Request request = new Request.Builder()
                .url("http://192.168.0.177:8888/textjson")//请求的url
                .post(requestBody)
                .build();

        //创建/Call
        Call call = okHttpClient.newCall(request);
        //加入队列 异步操作
        call.enqueue(new Callback() {
            //请求错误回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("连接失败");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, ""+response.body().string());
            }
        });

    }
}
