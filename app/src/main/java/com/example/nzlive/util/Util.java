package com.example.nzlive.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * 项目名称：
 * 类描述：
 * 创建人：zhaowei
 * 创建时间：2017/4/19 9:20
 * 修改人：Administrator
 * 修改时间：2017/4/19 9:20
 * 修改备注：
 */
public class Util {
    /**
     * 描述：保存数据到SharedPreferences对象中
     */

    public static void saveSetting(String time, Context context) {
        SharedPreferences spSettingSave = context.getSharedPreferences("NetTime", MODE_PRIVATE);// 将需要记录的数据保存在setting.xml文件中
        SharedPreferences.Editor editor = spSettingSave.edit();
        editor.putString("time", time);
        editor.commit();
    }

    /**
     * 描述：获取数据到SharedPreferences对象中
     * @return
     */
    public static NetTimeUtil loadSetting(Context context) {
        NetTimeUtil netTimeUtil=new NetTimeUtil();

        SharedPreferences loadSettingLoad = context.getSharedPreferences("NetTime", MODE_PRIVATE);
        //这里是将setting.xml 中的数据读出来
        netTimeUtil.setTime( loadSettingLoad.getString("time", "") );

//        String urlSetting = "http://" + urlHttp+ ":" + urlPort + "/";
        return netTimeUtil;
    }
}
