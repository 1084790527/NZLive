package com.example.nzlive.bean;

public class TeacherListBean {
    private String userid;
    private String username;
    private String dormroom;
    private String status;

    @Override
    public String toString() {
        return "TeacherListBean{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", dormroom='" + dormroom + '\'' +
                ", status='" + status + '\'' +
                '}';
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
