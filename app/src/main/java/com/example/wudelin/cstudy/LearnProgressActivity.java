package com.example.wudelin.cstudy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.wudelin.cstudy.db.Progress;
import com.example.wudelin.cstudy.util.HttpUtil;
import com.example.wudelin.cstudy.util.URL;
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wudelin on 2017/12/17.
 */

public class LearnProgressActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ChartProgressBar mChart;
    private SwipeRefreshLayout refreshLayout;
    private List<Progress> progressList;
    private ArrayList<BarData> dataList = new ArrayList<>();
    private String[] title = new String[]{"One","Two","Three"
                        ,"Four","Five","Six","Seven"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_progress);
        inits();
    }

    private void inits() {
        mChart = findViewById(R.id.ChartProgressBar);
        refreshLayout = findViewById(R.id.swipe_refresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        Toolbar toolbar = findViewById(R.id.toolbar_xml);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("学习进度");
        }
        queryLite();

    }

    private void queryFromService(String url) {

        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                responseText = responseText.substring(0,responseText.length()-1);
                String[] res = responseText.split("/");
                for (int i = 0;i<res.length;i++){
                    Progress progress = new Progress();
                    progress.setId(i);
                    progress.setChapter(i);
                    progress.setPro(res[i]);
                    progress.save();
                    Log.d("wdl", "onResponse: "+res[i]);
                }
            }
        });
    }

    private void queryLite() {
        progressList = DataSupport.findAll(Progress.class);
        if(progressList.size()>0){
            Log.d("wdl", "queryLite: "+progressList.size());
            dataList.clear();
            int i = 0;
            for (Progress progress:progressList){
                dataList.add(new BarData(title[i],
                        Float.parseFloat(progress.getPro().replace("%","")),
                        progress.getPro()));
                i++;
            }
            mChart.setDataList(dataList);
            mChart.build();
        }else{
            SharedPreferences spf = PreferenceManager.
                    getDefaultSharedPreferences(getApplicationContext());
            String username = spf.getString("USERNAME","");
            if(!TextUtils.isEmpty(username)) {
                String url = URL.HTTP_URL_REQ_PRO +username;
                queryFromService(url);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:finish();break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        Log.d("wdl", "onRefresh: ");
        DataSupport.deleteAll(Progress.class);
        queryLite();
        refreshLayout.setRefreshing(false);
    }
}
