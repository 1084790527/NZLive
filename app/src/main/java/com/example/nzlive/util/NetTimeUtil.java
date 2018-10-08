package com.example.nzlive.util;

import android.content.Context;
import android.util.Log;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class NetTimeUtil {
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public void setNetTime(Context context){
        try {
            //url = new URL("http://www.baidu.com");
//            URL url = new URL("http://www.ntsc.ac.cn");//中国科学院国家授时中心
            URL url = new URL("http://www.bjtime.cn");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            calendar.setTimeInMillis(ld);
            String format = formatter.format(calendar.getTime());
            //Log.d("AAA", "run: "+format);
            new Util().saveSetting(format,context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getNetTime(Context context) {
        return Util.loadSetting(context).getTime();
    }
}
