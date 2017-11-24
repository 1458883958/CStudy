package com.example.wudelin.cstudy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wudelin.cstudy.util.HttpUtil;
import com.example.wudelin.cstudy.util.URL;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wudelin on 2017/11/24.
 */

public class DetailTaskActivity extends AppCompatActivity {
    public static final String TASK_NAME = "task_name";
    public static final String TASK_IMAGE_ID = "task_image_id";
    public static final String TASK_CONTENT_ID = "task_content_id";
    private  String taskName;
    private int taskImageId;
    private int position;
    private ImageView imageView;
    private TextView textView;
    private int index = 0;
    private static final int UPDATE_TEXT = 1;
    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case UPDATE_TEXT:
                    String responseText = message.getData().getString("response_text");
                    textView.setText(responseText);
                    break;
                default:
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail);
        Intent intent = getIntent();
        taskName = intent.getStringExtra(TASK_NAME);
        taskImageId = intent.getIntExtra(TASK_IMAGE_ID,0);
        position = intent.getIntExtra(TASK_CONTENT_ID,0);
        Log.d("wdl", ""+position);
        Toolbar toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.task_image_view);
        textView = findViewById(R.id.task_content_view);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            //设置home键可用
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(taskName);

        Glide.with(this)
                .load(taskImageId)
                .into(imageView);
        getTaskContent();

    }
    /*
    * 获取服务器题目
    * */
    private void getTaskContent() {
        String httpUrl = URL.HTTP_URL_PROBLEM+position+"&pro_id="+index;
        Log.d("wdl", ""+httpUrl);
        HttpUtil.sendOkHttpRequest(httpUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //warn();
                Log.d("wdl", "onFailure: "+e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Log.d("wdl", ""+responseText);
                if(!TextUtils.isEmpty(responseText)) {
                    Message message = new Message();
                    message.what = UPDATE_TEXT;
                    Bundle bundle = new Bundle();
                    bundle.putString("response_text", responseText);
                    message.setData(bundle);
                    myHandler.sendMessage(message);
                }else{
                    warn();
                }
            }
        });
    }

    private void warn() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DetailTaskActivity.this, "获取题目失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
