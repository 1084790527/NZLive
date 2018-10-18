package com.example.nzlive;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;

import com.example.nzlive.fragment.FriendFragment;
import com.example.nzlive.fragment.HomePageFragment;
import com.example.nzlive.fragment.InforMationFragment;
import com.example.nzlive.fragment.PersonageFragment;
import com.example.nzlive.util.LogUtil;
import com.example.nzlive.websocket.SocketConnet;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AAA";
    private RadioButton rb_homepage,rb_information,rb_friend,rb_personage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         *
         * 10/08
         */
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        init();

        rbListener();

        webSocketConnet();

        Executors.newScheduledThreadPool(3).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
//                Log.d(TAG, "run: ");
                if (!SocketConnet.booleanconnet()){
                    webSocketConnet();
                }
            }
        },5,5, TimeUnit.SECONDS);

    }

    private void webSocketConnet() {
        if (SocketConnet.booleanconnet()){
            LogUtil.Logd(getApplicationContext(),"已连接");
            return;
        }
        try {
            SocketConnet.connet(getApplication());
        } catch (WebSocketException e) {
            e.printStackTrace();
            LogUtil.Logd(getApplicationContext(),"连接失败");
        }
    }

    private void rbListener() {
        rb_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,new HomePageFragment()).commit();
            }
        });
        rb_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,new InforMationFragment()).commit();
            }
        });
        rb_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,new FriendFragment()).commit();
            }
        });
        rb_personage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,new PersonageFragment()).commit();
            }
        });
    }

    private void init() {
        rb_homepage=findViewById(R.id.rb_homepage);
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent,new HomePageFragment()).commit();
        rb_information=findViewById(R.id.rb_information);
        rb_friend=findViewById(R.id.rb_friend);
        rb_personage=findViewById(R.id.rb_personage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (SocketConnet.booleanconnet()){
            SocketConnet.closeWebSocketConnection();
        }
    }
}
