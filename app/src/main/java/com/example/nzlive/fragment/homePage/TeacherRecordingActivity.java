package com.example.nzlive.fragment.homePage;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

import com.example.nzlive.R;
import com.example.nzlive.bean.TeacherRecordingListBean;
import com.example.nzlive.fragment.homePage.adapter.TeacherRecordingListAdapter;
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

public class TeacherRecordingActivity extends AppCompatActivity {

    private static final String TAG = "AAA";
    private ListView lv_teacher_recording_list;
    private TeacherRecordingListAdapter mAdapter;
    private List<TeacherRecordingListBean> mList;
    private Handler handler;
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher_recording);

        init();

        setData();

        getInitiateNameRecord();
    }

    private void getInitiateNameRecord() {
        if (userid==null){
            LogUtil.Logd(getApplicationContext(),"获取数据失败，请重启app！");
            return;
        }
        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        final JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userid",userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();

        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);

        final Request request = new Request.Builder()
                .url(Variable.ServiceIP+"getInitiateNameRecord")//请求的url
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
                final String s=response.body().string();
//                Log.d(TAG, ""+s);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray=new JSONArray(s);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                TeacherRecordingListBean bean=new TeacherRecordingListBean();
                                bean.setTime(object.getString("time"));
                                bean.setDate(object.getString("date"));
                                mList.add(bean);
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LogUtil.Logd(getApplicationContext(),"加载数据错误！");
                        }

                    }
                });
            }
        });
    }

    private void setData() {
        TeacherRecordingListBean bean=new TeacherRecordingListBean();
        bean.setDate("日期");
        bean.setTime("时间");
        mList.add(bean);
        mAdapter=new TeacherRecordingListAdapter(mList,getApplicationContext());
        lv_teacher_recording_list.setAdapter(mAdapter);

    }

    private void init() {
        lv_teacher_recording_list=findViewById(R.id.lv_teacher_recording_list);
        mList=new ArrayList<>();
        handler=new Handler(Looper.getMainLooper());

        String s=SharePreUtil.getData(getApplicationContext(),"user","data","");
        try {
            JSONObject data=new JSONObject(s);
            userid=data.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
            userid=null;
        }


    }
}
