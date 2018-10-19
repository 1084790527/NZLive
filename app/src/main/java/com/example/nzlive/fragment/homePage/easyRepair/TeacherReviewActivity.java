package com.example.nzlive.fragment.homePage.easyRepair;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

import com.example.nzlive.R;
import com.example.nzlive.bean.TeacherReviewListBean;
import com.example.nzlive.fragment.homePage.adapter.TeacherReviewListAdapter;
import com.example.nzlive.util.LogUtil;
import com.example.nzlive.util.Variable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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

public class TeacherReviewActivity extends AppCompatActivity implements TeacherReviewListAdapter.onItemDeleteListener {

    private static final String TAG = "AAA";
    private ListView lv_teacher_review;
    private TeacherReviewListAdapter adapter;
    private List<TeacherReviewListBean> list;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher_review);

        init();

        setdata();
    }

    private void setdata() {
        /*
        TeacherReviewListBean bean=new TeacherReviewListBean();
        bean.setUserid("2016041113");
        bean.setUsername("展示");
        bean.setDormroom("5#506");
        bean.setNum("13675062969");
        bean.setDate("20162032166");
        bean.setData("adasdfnbauisjnfoiwhenfoemiognwoiemgoinseogkl");
        bean.setIs(true);
        list.add(bean);
        */

        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),"");

        final Request request = new Request.Builder()
                .url(Variable.ServiceIP+"getTeacherReview")//请求的url
                .post(requestBody)
                .build();

        //创建/Call
        Call call = okHttpClient.newCall(request);
        //加入队列 异步操作
        call.enqueue(new Callback() {
            //请求错误回调方法
            @Override
            public void onFailure(Call call, IOException e) {
//                System.out.println("连接失败");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.Logd(getApplicationContext(),"网络连接错误！");
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                Log.d(TAG, ""+s);
                try {
                    JSONArray jsonArray=new JSONArray(s);
                    setData(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setData(JSONArray jsonArray) throws JSONException {
        for (int i=0;i<jsonArray.length();i++){
            JSONObject object=jsonArray.getJSONObject(i);
            TeacherReviewListBean bean=new TeacherReviewListBean();
            bean.setUserid(object.getString("userid"));
            bean.setUsername(object.getString("username"));
            bean.setDormroom(object.getString("dormroom"));
            bean.setNum(object.getString("num"));
            bean.setDate(object.getString("date"));
            bean.setData(object.getString("data"));
            bean.setIs(object.getInt("schedule") == 0 ? true : false);
            list.add(bean);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void init() {
        lv_teacher_review=findViewById(R.id.lv_teacher_review);
        list=new ArrayList<>();
        adapter=new TeacherReviewListAdapter(getApplicationContext(),list);
        adapter.setOnItemDeleteClickListener(this);
        lv_teacher_review.setAdapter(adapter);
        handler=new Handler(Looper.getMainLooper());
    }

    @Override
    public void onDeleteClick(int i, String btn) {
        LogUtil.Logd(getApplicationContext(),i+":"+btn);
        if ("agree".equals(btn)){
            agree(i);
        }else if ("withdraw".equals(btn)){

        }
    }

    private void agree(final int i) {
        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",list.get(i).getUserid());
            jsonObject.put("date",list.get(i).getDate());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();

        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);

        final Request request = new Request.Builder()
                .url(Variable.ServiceIP+"updataChangeReview")//请求的url
                .post(requestBody)
                .build();

        //创建/Call
        Call call = okHttpClient.newCall(request);
        //加入队列 异步操作
        call.enqueue(new Callback() {
            //请求错误回调方法
            @Override
            public void onFailure(Call call, IOException e) {
//                System.out.println("连接失败");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.Logd(getApplicationContext(),"网络连接错误！");
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String s=response.body().string();
                Log.d(TAG, ""+s);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object=new JSONObject(s);
                            if ("0".equals(object.getString("status"))){
                                list.get(i).setIs(false);
                                adapter.notifyDataSetChanged();
                            }else {
                                LogUtil.Logd(getApplicationContext(),"状态更改失败，服务器错误！");
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
