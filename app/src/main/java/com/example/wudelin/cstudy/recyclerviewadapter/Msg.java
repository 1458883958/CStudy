package com.example.wudelin.cstudy.recyclerviewadapter;

/**
 * Created by wudelin on 2017/11/4.
 */

public class Msg {
    public static final int TYPE_RECEICE = 1;
    public static final int TYPE_SEND = 0;
    private String contain;
    private int type;

    public Msg(String contain,int type) {
        this.contain = contain;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getContain() {
        return contain;
    }
}
