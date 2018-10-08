package com.example.nzlive;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.nzlive.login.login;
import com.example.nzlive.util.ConstantValue;
import com.example.nzlive.util.SharePreUtil;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
/*
    闪屏页面
 */

public class Splash extends AppCompatActivity {

    private Boolean isFirst;
    private String TAG="AAA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        init();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirst){
                    startActivity(new Intent(Splash.this, login.class));
                    SharePreUtil.saveBoolean(getApplication(), ConstantValue.ISFIRST, false);
                    //将isFirst改为false,并且在本地持久化
                } else {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                }
                finish();
            }
        }, 1000);
    }


    private void init() {
        isFirst = SharePreUtil.getBoolean(getApplication(), ConstantValue.ISFIRST, true);
    }
}
