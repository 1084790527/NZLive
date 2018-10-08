package com.example.nzlive.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by YouZi on 2018/6/16.
 */

public class SharePreUtil {
    private static SharedPreferences sp;

    public static void saveBoolean(Context ctx, String key, boolean value){
        if (sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }

    public static Boolean getBoolean(Context ctx, String key, boolean defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }
}
