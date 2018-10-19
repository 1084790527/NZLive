package com.example.nzlive.bean;

public class TeacherReviewListBean {
    private String userid;
    private String username;
    private String dormroom;
    private String num;
    private String date;
    private String data;
    private boolean is;

    @Override
    public String toString() {
        return "TeacherReviewListBean{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", dormroom='" + dormroom + '\'' +
                ", num='" + num + '\'' +
                ", date='" + date + '\'' +
                ", data='" + data + '\'' +
                ", is=" + is +
                '}';
    }

    public boolean isIs() {
        return is;
    }

    public void setIs(boolean is) {
        this.is = is;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDormroom() {
        return dormroom;
    }

    public void setDormroom(String dormroom) {
        this.dormroom = dormroom;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
