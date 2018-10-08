package com.example.nzlive.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nzlive.MainActivity;
import com.example.nzlive.R;

/**
 * 设置密码
 */
public class entry_pwd extends AppCompatActivity implements View.OnClickListener{

    private EditText entry_password;
    private Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_pwd);

        init();
    }

    private void init(){
        entry_password = findViewById(R.id.entry_password);
        finish =findViewById(R.id.entry_pwd_finish);

        finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
