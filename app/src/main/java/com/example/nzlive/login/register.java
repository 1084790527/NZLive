package com.example.nzlive.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nzlive.R;

public class register extends AppCompatActivity implements View.OnClickListener{

    private EditText entry_num;
    private Button register_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initData();
    }


    private void initData(){
        entry_num = findViewById(R.id.entry_num);
        register_next = findViewById(R.id.register_next);

        register_next.setOnClickListener(this);
    }

    /**
     *查找服务器是否有学号教工号，然后跳转设置密码
     */

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), entry_pwd.class));
    }
}
