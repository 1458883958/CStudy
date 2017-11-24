package com.example.wudelin.cstudy.application;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by wudelin on 2017/11/24.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
