package com.example.nzlive.fragment.homePage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.nzlive.R;

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher);

        init();

    }

    private void init() {
        ll_return=findViewById(R.id.ll_return);
        ll_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                finish();
                break;
        }
    }
}
