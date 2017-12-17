package com.example.wudelin.cstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;

import java.util.ArrayList;

/**
 * Created by wudelin on 2017/12/17.
 */

public class LearnProgressActivity extends AppCompatActivity {

    private ChartProgressBar mChart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_progress);
        inits();
    }

    private void inits() {
        ArrayList<BarData> dataList = new ArrayList<>();
        BarData data;
        data = new BarData("One", 8f, "8€");
        dataList.add(data);
        data = new BarData("Two", 1.8f, "1.8€");
        dataList.add(data);
        data = new BarData("Three", 7.3f, "7.3€");
        dataList.add(data);
        data = new BarData("Four", 6.2f, "6.2€");
        dataList.add(data);
        data = new BarData("Five", 3.3f, "3.3€");
        dataList.add(data);
        data = new BarData("Six", 3.3f, "3.3€");
        dataList.add(data);
        data = new BarData("Seven", 3.3f, "3.3€");
        dataList.add(data);


        mChart = findViewById(R.id.ChartProgressBar);
        Toolbar toolbar = findViewById(R.id.toolbar_xml);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("学习进度");
        }

        mChart.setDataList(dataList);
        mChart.build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:finish();break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
