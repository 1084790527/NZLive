package com.example.nzlive.fragment.homePage.easyRepair;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nzlive.R;
import com.example.nzlive.bean.RepairScheduleListBean;
import com.example.nzlive.fragment.homePage.adapter.RepairScheduleListAdapter;
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

public class RepairScheduleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "AAA";
    private ListView lv_repairSchedule;
    private RepairScheduleListAdapter adapter;
    private List<RepairScheduleListBean> list;
    private String userid;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_repair_schedule);

        init();

        setListData();

        getData();
    }

    private void getData() {
        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        JSONObject jsonObject=new JSONObject();
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
                .url(Variable.ServiceIP+"getRepairData")//请求的url
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
//                Log.d(TAG, ""+s);
                try {
                    JSONArray jsonArray=new JSONArray(s);
                    setListdata(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setListdata(final JSONArray jsonArray) throws JSONException {
        for (int i=0;i<jsonArray.length();i++){
            JSONObject object=jsonArray.getJSONObject(i);
            RepairScheduleListBean bean=new RepairScheduleListBean();
            bean.setUserid(object.getString("userid"));
            bean.setUsername(object.getString("username"));
            bean.setDormroom(object.getString("dormroom"));
            bean.setNum(object.getString("num"));
            bean.setData(object.getString("data"));
            bean.setDate(object.getString("date"));
            bean.setSchedule(object.getInt("schedule"));
            list.add(bean);

        }

        handler.post(new Runnable() {
            @Override
            public void run() {
//                LogUtil.Logd(getApplicationContext(),jsonArray.toString()+"");
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setListData() {
        RepairScheduleListBean bean=new RepairScheduleListBean();
        bean.setSchedule(0);
        bean.setDate("时间");
        list.add(bean);
        adapter=new RepairScheduleListAdapter(getApplicationContext(),list);
        lv_repairSchedule.setAdapter(adapter);
//        for (int i=0;i<50;i++){
//            RepairScheduleListBean listBean=new RepairScheduleListBean();
//            listBean.setDate("20161019094710");
//            listBean.setSchedule(0);
//            list.add(listBean);
//            adapter.notifyDataSetChanged();
//        }
    }

    private void init() {
        lv_repairSchedule=findViewById(R.id.lv_repairSchedule);
        lv_repairSchedule.setOnItemClickListener(this);
        list=new ArrayList<>();

        String s=SharePreUtil.getData(getApplicationContext(),"user","data","");
        try {
            JSONObject object=new JSONObject(s);
            userid=object.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
            userid=null;
        }

        handler=new Handler(Looper.getMainLooper());

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position==0){
            return;
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        View mView= LayoutInflater.from(this).inflate(R.layout.repair_schedule_alert_dialog,null);
        ((TextView)mView.findViewById(R.id.tv_userid)).setText(list.get(position).getUserid());
        ((TextView)mView.findViewById(R.id.tv_username)).setText(list.get(position).getUsername());
        ((TextView)mView.findViewById(R.id.tv_dormroom)).setText(list.get(position).getDormroom());
        ((TextView)mView.findViewById(R.id.tv_num)).setText(list.get(position).getNum());
        ((TextView)mView.findViewById(R.id.tv_date)).setText(list.get(position).getDate());
        ((TextView)mView.findViewById(R.id.tv_schedule)).setText(judgeSchedule(list.get(position).getSchedule()+""));
        ((TextView)mView.findViewById(R.id.tv_data)).setText(list.get(position).getData());
        builder.setView(mView);
        builder.show();
    }
    private String judgeSchedule(String schedule){
        switch (schedule){
            case "0":
                return "等待通过审核";
            case "1":
                return "辅导员审批通过";
            case "2":
                return "教务处审批通过";
            case "3":
                return "已报修";
            default:
                return "状态错误";
        }
    }
}
