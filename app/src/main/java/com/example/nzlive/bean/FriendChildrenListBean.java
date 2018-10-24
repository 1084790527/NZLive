package com.example.nzlive.bean;

public class FriendChildrenListBean {
    private String text;

    @Override
    public String toString() {
        return "FriendChildrenListBean{" +
                "text='" + text + '\'' +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
