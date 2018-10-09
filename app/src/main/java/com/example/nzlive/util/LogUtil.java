package com.example.nzlive.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 10847 on 10/9/2018.
 */

public class LogUtil {

    private static String TAG="AAA";
    public static void Logd(Context context,String msg){
        Log.d(TAG, msg);
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
