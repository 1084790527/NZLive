package com.example.nzlive.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.nzlive.MainActivity;

/**
 * Created by 10847 on 10/9/2018.
 */

public class AlertDialogUtil {
    public static void Dialog(final Context context, String msg){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("我是对话框")//设置对话框的标题
                .setMessage("我是对话框的内容")//设置对话框的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "点击了确定的按钮", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();

    }
}
