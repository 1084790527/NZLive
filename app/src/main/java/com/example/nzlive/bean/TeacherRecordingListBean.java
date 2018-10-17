package com.example.nzlive.bean;

public class TeacherRecordingListBean {
    private String date;
    private String time;

    @Override
    public String toString() {
        return "TeacherRecordingListBean{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
