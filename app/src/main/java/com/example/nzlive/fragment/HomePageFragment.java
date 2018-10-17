package com.example.nzlive.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nzlive.R;
import com.example.nzlive.fragment.homePage.KnowingActivity;
import com.example.nzlive.fragment.homePage.TeacherActivity;
import com.example.nzlive.util.GETHttp;
import com.example.nzlive.util.LogUtil;
import com.example.nzlive.util.SharePreUtil;
import com.example.nzlive.viewPager.FiveFragment;
import com.example.nzlive.viewPager.FourFragment;
import com.example.nzlive.viewPager.OneFragment;
import com.example.nzlive.viewPager.ThreeFragment;
import com.example.nzlive.viewPager.TwoFragment;
import com.example.nzlive.websocket.SocketConnet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.tavendo.autobahn.WebSocketException;
import okhttp3.OkHttpClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment implements View.OnClickListener {

    private String TAG="AAA";
    private View view;
    private ViewPager home_viewPager;
    private OneFragment f1;
    private TwoFragment f2;
    private ThreeFragment f3;
    private FourFragment f4;
    private FiveFragment f5;
    private List fragmentContainter;
    private boolean isAutoPlay  ;
    private Handler handler;
    private View ll_knowing;
//    private String strUrl = "http://wthrcdn.etouch.cn/weather_mini?city=福安";
    private TextView tv_week,tv_years,tv_temperature,tv_city;
    private ImageView img_weather;

    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_home_page,container,false);
        home_viewPager = (ViewPager) view.findViewById(R.id.home_viewPager);

        init();

        handler = new Handler();
        handler.postDelayed(new TimerRunnable(), 1000);

        home_viewPager.setCurrentItem(1000*fragmentContainter.size());

        home_viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
//                return (Fragment) fragmentContainter.get(position);
                return (Fragment) fragmentContainter.get(position % fragmentContainter.size());
            }

            @Override
            public int getCount() {
                return 10000;
            }

        });

//        weather(new GETHttp().getResponse(getContext()));
        weather();
        return view;
    }


    private void weather() {
//        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, strUrl, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                // TODO Auto-generated method stu
//                Log.d("AAA", "onResponse: "+response.toString());
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("AAA", "onErrorResponse: "+volleyError.toString());
//            }
//        });
//        mQueue.add(jsonObjectRequest);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//yyyy年MM月dd日 HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        tv_years.setText(simpleDateFormat.format(date)+"");
        img_weather.setImageDrawable(getResources().getDrawable(R.drawable.overcastsky));

        simpleDateFormat=new SimpleDateFormat("E");
        String week="星期"+(simpleDateFormat.format(date)).substring(1,2);
        LogUtil.Logd(getActivity(),week);
        tv_week.setText(week);

        try {
//            Log.i("wxy","main thread id is "+Thread.currentThread().getId());
            String url = "http://www.weather.com.cn/data/sk/101230306.html";
            OkHttpClient client = new OkHttpClient();
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    // 注：该回调是子线程，非主线程
//                    Log.i(TAG,"callback thread id is "+Thread.currentThread().getId());
                    String s=response.body().string();
                    Log.i(TAG,s);
                    try {
                        JSONObject object=new JSONObject(s).getJSONObject("weatherinfo");
                        String city=object.getString("city");
                        tv_city.setText(city);
                        String temp=object.getString("temp");
                        tv_temperature.setText(temp+"℃");
                        Log.d(TAG, "onResponse: "+city+":"+temp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void weather(JSONObject jsonObject) {
//        Log.d("AAA", "weather: "+new GETHttp().getResponse(getContext()));
//        Log.d("AAA", "weather: "+jsonObject.toString());
        try {
            JSONObject dataObjest=jsonObject.getJSONObject("data");
            tv_city.setText(dataObjest.getString("city"));
            JSONObject forecastObjst=dataObjest.getJSONArray("forecast").getJSONObject(0);
            tv_week.setText(forecastObjst.getString("date").substring(3));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//yyyy年MM月dd日 HH:mm:ss
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            tv_years.setText(simpleDateFormat.format(date));
            tv_temperature.setText(forecastObjst.getString("low").substring(3)+"~"+forecastObjst.getString("high").substring(3));
            img_weather.setImageDrawable(getResources().getDrawable(R.drawable.overcastsky));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_knowing:
                String data=SharePreUtil.getData(getActivity(),"user","data","");
                boolean b=true;
                try {
                    JSONObject jsonObject=new JSONObject(data);
                    String userid=jsonObject.getString("userid")+"";
                    if (userid.length()==6){
                        b=false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (b){
                    Intent intent=new Intent(getActivity(), KnowingActivity.class);
                    startActivity(intent);
                }else {
//                    LogUtil.Logd(getActivity(),"ls");
                    Intent intent=new Intent(getActivity(), TeacherActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }


    class TimerRunnable implements Runnable{

        @Override
        public void run() {
            int curItem = home_viewPager.getCurrentItem();
            home_viewPager.setCurrentItem(curItem+1);
            if (handler!= null){
                handler.postDelayed(this, 1000);
            }
        }
    }

    private void init() {
        f1 = new OneFragment();
        f2 = new TwoFragment();
        f3 = new ThreeFragment();
        f4 = new FourFragment();
        f5 = new FiveFragment();
        fragmentContainter = new ArrayList<Fragment>();
        fragmentContainter.add(f1);
        fragmentContainter.add(f2);
        fragmentContainter.add(f3);
        fragmentContainter.add(f4);
        fragmentContainter.add(f5);
        ll_knowing=view.findViewById(R.id.ll_knowing);
        ll_knowing.setOnClickListener(this);
//        new GETHttp().setResponse(strUrl,getContext());
        tv_week=view.findViewById(R.id.tv_week);
        tv_years=view.findViewById(R.id.tv_years);
        tv_temperature=view.findViewById(R.id.tv_temperature);
        tv_city=view.findViewById(R.id.tv_city);
        img_weather=view.findViewById(R.id.img_weather);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler = null;
    }
}
