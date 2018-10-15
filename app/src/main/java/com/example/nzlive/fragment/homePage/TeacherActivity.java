package com.example.nzlive.fragment.homePage;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.nzlive.R;
import com.example.nzlive.bean.TeacherListBean;
import com.example.nzlive.fragment.homePage.adapter.TeacherListAdapter;
import com.example.nzlive.util.LogUtil;
import com.example.nzlive.util.SharePreUtil;
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

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AAA";
    private LinearLayout ll_return,ll_startPointName;
    private ListView lv_teacher_list;
    private Handler handler;

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
        final List<TeacherListBean> mList=new ArrayList<>();
        TeacherListBean bean=new TeacherListBean();
        bean.setUserid("学号");
        bean.setUsername("姓名");
        bean.setDormroom("宿舍");
        bean.setStatus("到时");
        mList.add(bean);
        final TeacherListAdapter adapter=new TeacherListAdapter(mList,getApplicationContext());
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
                        Log.d(TAG, "onResponse: "+jsonArray.getString(i));
                        JSONObject object=jsonArray.getJSONObject(i);
                        TeacherListBean listBean=new TeacherListBean();
                        listBean.setUserid(object.getString("userid")+"");
                        listBean.setUsername(object.getString("username")+"");
                        listBean.setDormroom(object.getString("dormroom")+"");
                        listBean.setStatus("未到");
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

        handler=new Handler(Looper.getMainLooper());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                finish();
                break;
            case R.id.ll_startPointName:

                break;
        }
    }
}
