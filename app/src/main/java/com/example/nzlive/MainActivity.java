package com.example.nzlive;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;

import com.example.nzlive.fragment.FriendFragment;
import com.example.nzlive.fragment.HomePageFragment;
import com.example.nzlive.fragment.InforMationFragment;
import com.example.nzlive.fragment.PersonageFragment;

public class MainActivity extends AppCompatActivity {

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


}
