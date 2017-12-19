package com.example.wudelin.cstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by wudelin on 2017/12/19.
 */

public class BannerWebActivity extends AppCompatActivity{
    private WebView webView;
    public final static String BANNER_POSITION = "banner_position";
    private int position;
    private String[] url = {"https://www.imooc.com/learn/249",
            "https://www.imooc.com/learn/124",
            "http://blog.csdn.net/lmj623565791/",
            "https://github.com"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banner_webview);
        webView = findViewById(R.id.web_view);
        Toolbar toolbar = findViewById(R.id.toolbar_xml);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("QuQ");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        webView.getSettings().setJavaScriptEnabled(true);//支持js脚本
        webView.setWebViewClient(new WebViewClient());//新页面仍在webview中显示
        webView.canGoBack();
        Intent intent = getIntent();
        position = intent.getIntExtra(BANNER_POSITION,-1);
        if(position!=-1) {
            webView.loadUrl(url[position]);
        }


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
