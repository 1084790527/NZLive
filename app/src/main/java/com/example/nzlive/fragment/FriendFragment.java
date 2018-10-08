package com.example.nzlive.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nzlive.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {


    private static final String TAG = "AAA";

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_friend, container, false);
        TextView textView=view.findViewById(R.id.textview);
        textView.setText("f3");

        okHttp_asynchronousGet();

        return view;
    }
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


}
