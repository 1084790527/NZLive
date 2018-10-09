package com.example.nzlive.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nzlive.MainActivity;
import com.example.nzlive.R;

import org.json.JSONArray;
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

public class login extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "AAA";
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
                String num=login_num.getText().toString();
                String pwd=login_pwd.getText().toString();
                if ("".equals(num)){
                    Toast.makeText(getApplicationContext(),"账号不允许为空",Toast.LENGTH_LONG).show();
                    return;
                }else if ("".equals(pwd)){
                    Toast.makeText(getApplicationContext(),"密码不允许为空",Toast.LENGTH_LONG).show();
                    return;
                }
                OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10,TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .build();

                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("username",num);
                    jsonObject.put("userpwd",pwd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = jsonObject.toString();

                //MediaType  设置Content-Type 标头中包含的媒体类型值
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                        , json);

                final Request request = new Request.Builder()
                        .url("http://192.168.0.177:8888/login")//请求的url
                        .post(requestBody)
                        .build();

                //创建/Call
                Call call = okHttpClient.newCall(request);
                //加入队列 异步操作
                call.enqueue(new Callback() {
                    //请求错误回调方法
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(getApplicationContext(),"服务器错误！",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String status="0";
                        try {
                            JSONObject object=new JSONObject(response.body().string());
//                            Log.d(TAG, ""+object.toString());
                            status=object.getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        Log.d(TAG, ""+status);
                        switch (status){
                            case "0":
                                Toast.makeText(getApplicationContext(),"账号不存在！",Toast.LENGTH_LONG).show();
                                break;
                            case "1":
                                Toast.makeText(getApplicationContext(),"密码错误！",Toast.LENGTH_LONG).show();
                                break;
                            case "2":
                                Toast.makeText(getApplicationContext(),"登入成功",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                                break;
                        }
                    }
                });
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
