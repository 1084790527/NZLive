package com.example.nzlive.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.nzlive.R;
import com.example.nzlive.fragment.homePage.checkTheBed.KnowingActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationManagerUtil {
    public static void NMUtil(Context context,String title,String text){
        //创建通知管理类
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //创建通知建设类
        Notification.Builder builder = new Notification.Builder(context);
        //设置跳转的页面
        PendingIntent intent = PendingIntent.getActivity(context, 100, new Intent(context, KnowingActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        //设置通知栏标题
        builder.setContentTitle(title);
        //设置通知栏内容
        builder.setContentText(text);
        //设置跳转
        builder.setContentIntent(intent);
        //设置图标
        builder.setSmallIcon(R.drawable.logo);
        //设置
        builder.setDefaults(Notification.DEFAULT_ALL);
        //创建通知类
        Notification notification = builder.build();
        //显示在通知栏
        manager.notify(0, notification);
    }
}
