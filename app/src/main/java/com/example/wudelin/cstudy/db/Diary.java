package com.example.wudelin.cstudy.db;

import org.litepal.crud.DataSupport;

/**
 * Created by wudelin on 2017/12/14.
 */

public class Diary extends DataSupport{
    private int id;

    private String date;

    private String title;

    private String diaryContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDiaryContent() {
        return diaryContent;
    }

    public void setDiaryContent(String diaryContent) {
        this.diaryContent = diaryContent;
    }
}
