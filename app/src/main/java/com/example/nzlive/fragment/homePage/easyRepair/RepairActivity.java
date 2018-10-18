package com.example.nzlive.fragment.homePage.easyRepair;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.nzlive.R;
import com.example.nzlive.util.LogUtil;
import com.example.nzlive.util.SharePreUtil;
import com.example.nzlive.util.Variable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RepairActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AAA";
    private EditText et_userName,et_dormRoom,et_num,et_data,et_userId;
    private Button btn_submit,btn_schedule;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_repair);

        init();

        setETData();
    }

    private void setETData() {
        String s=SharePreUtil.getData(getApplicationContext(),"user","data","");
        try {
            JSONObject object=new JSONObject(s);
            et_userId.setText(object.getString("userid")+"");
            et_userName.setText(object.getString("username")+"");
            et_dormRoom.setText(object.getString("dormroom")+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        handler=new Handler(Looper.getMainLooper());
        et_userName=findViewById(R.id.et_userName);
        et_dormRoom=findViewById(R.id.et_dormRoom);
        et_num=findViewById(R.id.et_num);
        et_data=findViewById(R.id.et_data);
        et_userId=findViewById(R.id.et_userId);
        btn_submit=findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        btn_schedule=findViewById(R.id.btn_schedule);
        btn_schedule.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                submit();
                break;
            case R.id.btn_schedule:
                break;
        }
    }

    private void submit() {
        String userId=et_userId.getText().toString();
        String userName=et_userName.getText().toString();
        String dormRoom=et_dormRoom.getText().toString();
        String num=et_num.getText().toString();
        String data=et_data.getText().toString();
        if (userId.equals("")){
            LogUtil.Logd(getApplicationContext(),"请填写学号！");
        }else if (userName.equals("")){
            LogUtil.Logd(getApplicationContext(),"请填写姓名！");
            return;
        }else if (dormRoom.equals("")){
            LogUtil.Logd(getApplicationContext(),"请填写宿舍！");
            return;
        }else if (num.equals("")){
            LogUtil.Logd(getApplicationContext(),"请填写手机号！");
            return;
        }else if (data.equals("")){
            LogUtil.Logd(getApplicationContext(),"请填写需要报修的物品和原因！");
            return;
        }

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date=new Date(System.currentTimeMillis());

        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",userId);
            jsonObject.put("username",userName);
            jsonObject.put("dormroom",dormRoom);
            jsonObject.put("num",num);
            jsonObject.put("data",data);
            jsonObject.put("date",simpleDateFormat.format(date)+"");
            jsonObject.put("schedule",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();

        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);

        final Request request = new Request.Builder()
                .url(Variable.ServiceIP+"setRepairData")//请求的url
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
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.Logd(getApplicationContext(),"提交失败请重新提交！");
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String s=response.body().string();
//                Log.d(TAG, ""+s);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object=new JSONObject(s);
                            if ("0".equals(object.getString("status").toString()+"")){
                                LogUtil.Logd(getApplicationContext(),"提交成功！");
                                finish();
                            }else {
                                LogUtil.Logd(getApplicationContext(),"提交失败请重新提交！");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });

    }
}
