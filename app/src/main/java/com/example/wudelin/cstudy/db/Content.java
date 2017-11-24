package com.example.wudelin.cstudy.db;

import org.litepal.crud.DataSupport;

/**
 * Created by wudelin on 2017/11/24.
 */

public class Content extends DataSupport{

    private int id;

    private int l_name;

    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getL_name() {
        return l_name;
    }

    public void setL_name(int l_name) {
        this.l_name = l_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
