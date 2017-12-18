package com.example.wudelin.cstudy.db;

import org.litepal.crud.DataSupport;

/**
 * Created by wudelin on 2017/12/18.
 */

public class Progress extends DataSupport{
    private int id;

    private int chapter;

    private String pro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }
}
