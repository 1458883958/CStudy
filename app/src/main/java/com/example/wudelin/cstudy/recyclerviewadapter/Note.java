package com.example.wudelin.cstudy.recyclerviewadapter;

/**
 * Created by wudelin on 2017/12/14.
 */

public class Note {
    private String date;

    private String title;

    private String content;

    public Note(String date, String title, String content) {
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
