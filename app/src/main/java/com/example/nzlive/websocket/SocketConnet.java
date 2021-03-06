package com.example.nzlive.websocket;

import android.content.Context;
import android.util.Log;

import com.example.nzlive.fragment.homePage.checkTheBed.TeacherActivity;
import com.example.nzlive.util.LogUtil;
import com.example.nzlive.util.NotificationManagerUtil;
import com.example.nzlive.util.SharePreUtil;
import com.example.nzlive.util.Variable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by 10847 on 10/11/2018.
 */

public class SocketConnet {

    private static final String TAG = "AAA";
    private static Context context;
    private static WebSocketConnection webSocketConnection;
    private static int x;

    public static void connet(final Context cox) throws WebSocketException {
        context=cox;
        webSocketConnection=new WebSocketConnection();
        webSocketConnection.connect(Variable.WebsocketUrl,new WebSocketHandler(){

            @Override
            public void onOpen() {
                x=0;
//                webSocketConnection.sendTextMessage("连接成功");
                String s= SharePreUtil.getData(context,"user","data","");
                try {
                    JSONObject data=new JSONObject(s);
//                    LogUtil.Logd(context,""+data.toString());
                    String userid=data.getString("userid");
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("type","userid");
                    jsonObject.put("userid",userid);
                    webSocketConnection.sendTextMessage(jsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //心跳检测
                Executors.newScheduledThreadPool(3).scheduleWithFixedDelay(new Runnable() {
                    @Override
                    public void run() {
//                        Log.d(TAG, "run: "+"心跳检测");

                        int y=(int)(Math.random()*10);
                        while (y==x){
                            y=(int)(Math.random()*10);
                        }
                        x=y;
                        JSONObject object=new JSONObject();
                        try {
                            object.put("type","heartbeatDetection");
                            object.put("data",x);
                            webSocketConnection.sendTextMessage(object.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },0,5, TimeUnit.SECONDS);
            }

            @Override
            public void onClose(int code, String reason) {
                LogUtil.Logd(context,"连接关闭");
            }

            @Override
            public void onTextMessage(String payload) {
                LogUtil.Logd(context,payload+"");
                JSONObject object=null;
                try {
                    object=new JSONObject(payload);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (object==null){
                    LogUtil.Logd(context,"数据获取错误！");
                    return;
                }
                String type="";
                try {
                    type=object.getString("type");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (type){
                    case "":
                        LogUtil.Logd(context,"数据获取错误！");
                        break;
                    case "checkTheBed":
                        NotificationManagerUtil.NMUtil(context,"点名哪！！！","点名开始，请尽快完成任务！");
                        break;
                    case "returnCheckTheBed":
                        Log.d("AAA", "onTextMessage: "+object.toString());;
                        try {
                            TeacherActivity.returnCheckTheBed(context,object.getString("data"),object.getString("userid"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

        });

    }

    public static boolean sendTextMessage(String message){
        if (booleanconnet()){
            webSocketConnection.sendTextMessage(message);
            return true;
        }else {
            return false;
        }
    }

    public SocketConnet(){

    }

    public static void closeWebSocketConnection(){
        webSocketConnection.disconnect();
    }
    public static boolean booleanconnet(){
        if (webSocketConnection==null){
            return false;
        }
//        if (!webSocketConnection.isConnected()){
//            return false;
//        }else {
//            return true;
//        }
        return webSocketConnection.isConnected();

    }
}
