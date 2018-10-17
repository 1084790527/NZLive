package com.example.nzlive.fragment.homePage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nzlive.R;
import com.example.nzlive.bean.TeacherListBean;
import com.example.nzlive.fragment.homePage.adapter.TeacherListAdapter;
import com.example.nzlive.util.LogUtil;
import com.example.nzlive.util.SharePreUtil;
import com.example.nzlive.util.Variable;
import com.example.nzlive.websocket.SocketConnet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "AAA";
    private LinearLayout ll_return,ll_startPointName,ll_teacherRecording;
    private ListView lv_teacher_list;
    private Handler handler;
    private static List<TeacherListBean> mList;
    private static TeacherListAdapter adapter;
    private ImageView iv_checkTheBed;
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher);

        init();

        //获取所有学生数据
        getStdentData();

    }

    private void getStdentData() {

        TeacherListBean bean=new TeacherListBean();
        bean.setUserid("学号");
        bean.setUsername("姓名");
        bean.setDormroom("宿舍");
        bean.setStatus("到时");
        mList.add(bean);
        adapter=new TeacherListAdapter(mList,getApplicationContext());
        lv_teacher_list.setAdapter(adapter);

        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        JSONObject jsonObject=new JSONObject();
        try {
            String s=SharePreUtil.getData(getApplicationContext(),"user","data","");
            JSONObject user=new JSONObject(s);
            jsonObject.put("userid",user.getString("userid")+"");
            jsonObject.put("userpwd",user.getString("userpwd")+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String json = jsonObject.toString();

        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);

        final Request request = new Request.Builder()
                .url(Variable.ServiceIP+"obtainDataStdent")//请求的url
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
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
//                Log.d(TAG, "onResponse: "+s);
                try {
                    JSONArray jsonArray=new JSONArray(s);
//                    Log.d(TAG, "onResponse: "+jsonArray.toString());
                    for (int i=0;i<jsonArray.length();i++){
//                        Log.d(TAG, "onResponse: "+jsonArray.getString(i));
                        JSONObject object=jsonArray.getJSONObject(i);
                        TeacherListBean listBean=new TeacherListBean();
                        listBean.setUserid(object.getString("userid")+"");
                        listBean.setUsername(object.getString("username")+"");
                        listBean.setDormroom(object.getString("dormroom")+"");
                        String date=SharePreUtil.getData(getApplicationContext(),"teacher",object.getString("userid"),"");

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
                        Date mDate=new Date(System.currentTimeMillis());
                        String time=simpleDateFormat.format(mDate);

//                        Log.d(TAG, "onResponse: "+object.getString("userid")+":"+time+":"+date);
                        if (date.equals("")){
                            listBean.setStatus("未到");
                        }else if (time.equals(date.substring(0,8))){
                            listBean.setStatus(date.substring(8));
                        }else {
                            listBean.setStatus("未到");
                        }

                        mList.add(listBean);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void init() {
        ll_return=findViewById(R.id.ll_return);
        ll_return.setOnClickListener(this);

        ll_startPointName=findViewById(R.id.ll_startPointName);
        ll_startPointName.setOnClickListener(this);

        lv_teacher_list=findViewById(R.id.lv_teacher_list);
        lv_teacher_list.setOnItemClickListener(this);

        handler=new Handler(Looper.getMainLooper());

        mList=new ArrayList<>();

        iv_checkTheBed=findViewById(R.id.iv_checkTheBed);
        iv_checkTheBed.setOnClickListener(this);

        ll_teacherRecording=findViewById(R.id.ll_teacherRecording);
        ll_teacherRecording.setOnClickListener(this);

        String s=SharePreUtil.getData(getApplicationContext(),"user","data","");
        try {
            JSONObject object=new JSONObject(s);
            userid=object.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
            userid=null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                finish();
                break;
            case R.id.ll_startPointName:
                if (SocketConnet.booleanconnet()){
                    JSONObject object=new JSONObject();
                    try {
                        object.put("type","checkTheBed");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    boolean b = SocketConnet.sendTextMessage(object.toString()+"");
                    if (!b){
                        LogUtil.Logd(getApplicationContext(),"连接服务器失败请重启app！");
                        return;
                    }

                    OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10,TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .build();
                    if (userid==null){
                        LogUtil.Logd(getApplicationContext(),"请重新登入！");
                        return;
                    }

                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
                    final Date date=new Date(System.currentTimeMillis());
                    String year=simpleDateFormat.format(date);
                    simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
                    String time=simpleDateFormat.format(date);
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("userid",userid);
                        jsonObject.put("date",year);
                        jsonObject.put("time",time);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String json = jsonObject.toString();

                    //MediaType  设置Content-Type 标头中包含的媒体类型值
                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);

                    final Request request = new Request.Builder()
                            .url(Variable.ServiceIP+"setInitiateNameRecord")//请求的url
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
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s=response.body().string();
                            Log.d(TAG, ""+s);
                            boolean t=true;
                            try {
                                JSONObject mObject=new JSONObject(s);
                                String status=mObject.getString("status");
                                if ("1".equals(status)){
                                    t=false;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                t=false;
                            }
                            if (!t){
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        LogUtil.Logd(getApplicationContext(),"点名错误，请重新登入重新尝试！");
                                    }
                                });
                            }
                        }
                    });

                }else {
                    LogUtil.Logd(getApplicationContext(),"连接服务器失败请重启app！");
                }
                break;
            case R.id.iv_checkTheBed:
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,0);
                iv_checkTheBed.setLayoutParams(params);
                break;
            case R.id.ll_teacherRecording:
                Intent intent=new Intent(getApplicationContext(),TeacherRecordingActivity.class);
                startActivity(intent);
                break;
        }
    }

    public static void returnCheckTheBed(Context context,String data,String userid){
        for (int i=0;i<mList.size();i++){
            if (userid.equals(mList.get(i).getUserid())){
                String hh=data.substring(8,10);
                String mm=data.substring(10,12);
                String ss=data.substring(12,14);
                String date=data.substring(0,8);
                mList.get(i).setStatus(hh+":"+mm+":"+ss);
                adapter.notifyDataSetChanged();
                SharePreUtil.saveData(context,"teacher",userid,date+hh+":"+mm+":"+ss);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.Logd(getApplicationContext(),"position:"+position);
        TextView tv_status=view.findViewById(R.id.tv_status);
        LogUtil.Logd(getApplicationContext(),tv_status.getText()+"");
        String status=tv_status.getText()+"";
        if ("未到".equals(status)){
            return;
        }
        TextView tv_userid=view.findViewById(R.id.tv_userid);
        String userid=tv_userid.getText()+"";

//        iv_checkTheBed.setBackground();

        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",userid);
            jsonObject.put("data","20181016");
            jsonObject.put("status",status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();

        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);

        final Request request = new Request.Builder()
                .url(Variable.ServiceIP+"getImageStream")//请求的url
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
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.d(TAG, ""+response.body().string());
                InputStream inputStream = response.body().byteStream();//得到图片的流
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iv_checkTheBed.setScaleType(ImageView.ScaleType.CENTER);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                        iv_checkTheBed.setLayoutParams(params);
                        iv_checkTheBed.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }
}
