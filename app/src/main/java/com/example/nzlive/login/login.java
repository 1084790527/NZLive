package com.example.nzlive.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nzlive.MainActivity;
import com.example.nzlive.R;
public class login extends AppCompatActivity implements View.OnClickListener{

    private EditText login_num,login_pwd;
    private Button login_btn,login_forget,login_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initData();


    }

    private void initData(){
        login_btn = findViewById(R.id.login_btn);
        login_num = findViewById(R.id.login_num);
        login_pwd = findViewById(R.id.login_pwd);
        login_forget = findViewById(R.id.login_forget);
        login_register = findViewById(R.id.login_register);

        login_btn.setOnClickListener(this);
        login_forget.setOnClickListener(this);
        login_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:        //登录到MainActivity
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            case R.id.login_forget:     //忘记密码
                break;
            case R.id.login_register:       //注册
                startActivity(new Intent(getApplicationContext(), register.class));
                finish();
                break;
            default:
                break;
        }
    }

}
