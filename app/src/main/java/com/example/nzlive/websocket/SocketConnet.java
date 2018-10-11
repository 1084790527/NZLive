package com.example.nzlive.websocket;

import android.content.Context;

import com.example.nzlive.util.LogUtil;
import com.example.nzlive.util.Variable;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by 10847 on 10/11/2018.
 */

public class SocketConnet {

    private static Context context;
    private static WebSocketConnection webSocketConnection;
    public static void connet(Context cox) throws WebSocketException {
        context=cox;
        webSocketConnection=new WebSocketConnection();
        webSocketConnection.connect(Variable.WebsocketUrl,new WebSocketHandler(){

            @Override
            public void onOpen() {
                webSocketConnection.sendTextMessage("连接成功");
            }

            @Override
            public void onClose(int code, String reason) {
                LogUtil.Logd(context,"连接关闭");
            }

            @Override
            public void onTextMessage(String payload) {
                LogUtil.Logd(context,payload+"");
            }

        });

    }

    public SocketConnet(){

    }

    public static void closeWebSocketConnection(){
        webSocketConnection.disconnect();
    }
    public static boolean booleanconnet(){
        if (webSocketConnection==null){
            return false;
        }else {
            return webSocketConnection.isConnected();
        }

    }
}
