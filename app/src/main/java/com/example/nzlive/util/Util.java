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

    /**
     *
     * @param userid
     * @return
     */
    public static JSONObject getSystemAdnClass(String userid){
        String year=userid.substring(2,4);
        String system=userid.substring(4,6);
        String clas=userid.substring(6,8);

        system=system(system);
        clas=clas(clas);

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("system",system);
            jsonObject.put("class",year+clas);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String system(String system){
        switch (system){
            case "01":
                return "信息技术与工程系";
            case "02":
                return "信息技术与工程系";
            case "03":
                return "信息技术与工程系";
            case "04":
                return "信息技术与工程系";
            case "05":
                return "信息技术与工程系";

        }
        return "-----------系";
    }
    private static String clas(String clas){
        switch (clas){
            case "11":
                return "应用";
        }
        return "";
    }
}
