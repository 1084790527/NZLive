package com.example.nzlive.util;

public class Variable {

    private static String ip="110.89.150.171";
    //服务器IP地址
//    public static String ServiceIP="http://192.168.152.1:8888/";
//    public static String ServiceIP="http://192.168.110.2:8888/";
//    public static String ServiceIP="http://192.168.0.177:8888/";
//    public static String ServiceIP="http://110.89.149.43:8888/";
    public static String ServiceIP="http://"+ip+":8888/";

    //websocket链接地址
//    public static String WebsocketUrl="ws://192.168.0.177:8888/websocket";
    public static String WebsocketUrl="ws://"+ip+":8888/websocket";
//    public static String WebsocketUrl="ws://110.89.149.43:8888/websocket";

}
