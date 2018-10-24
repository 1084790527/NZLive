package com.example.nzlive.bean;

public class FriendGroupListBean {
    private String title;

    @Override
    public String toString() {
        return "FriendGroupListBean{" +
                "title='" + title + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
