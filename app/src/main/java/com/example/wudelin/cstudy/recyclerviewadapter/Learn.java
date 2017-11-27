package com.example.wudelin.cstudy.recyclerviewadapter;

/**
 * Created by wudelin on 2017/11/26.
 */

public class Learn {
    private int imageViewId;
    private String time;
    private String title;

    public Learn(int imageViewId, String time, String title) {
        this.imageViewId = imageViewId;
        this.time = time;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageViewId() {
        return imageViewId;
    }

    public void setImageViewId(int imageViewId) {
        this.imageViewId = imageViewId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
