package com.example.nzlive.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by YouZi on 2018/6/16.
 */

public class SharePreUtil {
    private static SharedPreferences sp;

    public static void saveBoolean(Context context, String key, boolean value){
        if (sp == null){
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }

    public static Boolean getBoolean(Context context, String key, boolean defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    public static void saveData(Context context,String name, String key, String value){
        if (sp == null){
            sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();
    }

    public static String getData(Context context,String name, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);
    }

}
