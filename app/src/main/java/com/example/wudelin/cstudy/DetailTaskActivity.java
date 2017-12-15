package com.example.wudelin.cstudy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wudelin.cstudy.spans.ReplaceSpan;
import com.example.wudelin.cstudy.spans.SpansManager;
import com.example.wudelin.cstudy.util.HttpUtil;
import com.example.wudelin.cstudy.util.URL;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wudelin on 2017/11/24.
 */

public class DetailTaskActivity extends AppCompatActivity implements View.OnClickListener, ReplaceSpan.OnClickListener {
    public static final String TASK_NAME = "task_name";
    public static final String TASK_IMAGE_ID = "task_image_id";
    public static final String TASK_CONTENT_ID = "task_content_id";
    private SpansManager mSpansManager;
    private String taskName;
    private int taskImageId;
    private int position;
    private ImageView imageView;
    private LMenu menu;
    private FloatingActionButton fabNote;
    private int count;
    //问题题目
    private TextView textView;
    //输入答案的编辑框
    private EditText editText;
    private int index = 0;
    private static final int UPDATE_TEXT = 1;

    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case UPDATE_TEXT:
                    String responseText = message.getData().getString("response_text");
                    //Log.d("wdl", ""+responseText);
                    textView.setText(responseText);
                    mSpansManager = new SpansManager(DetailTaskActivity.this, textView, editText);
                    mSpansManager.doFillBlank(textView.getText().toString());
                    //mSpansManager.getMyAnswer().get(0);
                    //Log.d("wdl", "handleMessage: "+ mSpansManager.getMyAnswer().get(0));
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_detail);
        count = 0;
        Intent intent = getIntent();
        taskName = intent.getStringExtra(TASK_NAME);
        taskImageId = intent.getIntExtra(TASK_IMAGE_ID, 0);
        position = intent.getIntExtra(TASK_CONTENT_ID, 0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.task_image_view);
        textView = findViewById(R.id.task_content_view);
        editText = findViewById(R.id.et_input);
        menu = findViewById(R.id.lMenu);
        fabNote = findViewById(R.id.make_note);
        fabNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent intent = new Intent(DetailTaskActivity.this,MakeNoteActivity.class);
                  startActivity(intent);
            }
        });
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //设置home键可用
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(taskName);
        Glide.with(this)
                .load(taskImageId)
                .into(imageView);
        getTaskContent();
        intiEvent();
    }

    private void intiEvent() {
        menu.setonMenuClickListener(new LMenu.onMenuClickListener() {
            @Override
            public void Next() {
                index++;
                editText.setText("");
                getTaskContent();
            }

            @Override
            public void Last() {
                index--;
                editText.setText("");
                getTaskContent();
            }

            @Override
            public void Submit() {
                getTaskAnswer();
            }
        });
    }

    private void getTaskAnswer() {
        String httpUrl = URL.HTTP_URL_ANSWER + position + "&ans_id=" + index;
        HttpUtil.sendOkHttpRequest(httpUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                String answer = getMyAnswerStr();
                if (!TextUtils.isEmpty(responseText)) {
                    if (answer.contains(responseText)) {
                        count++;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DetailTaskActivity.this, "回答正确...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DetailTaskActivity.this, "回答错误...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    private String getMyAnswerStr() {
        mSpansManager.setLastCheckedSpanText(editText.getText().toString());
        return mSpansManager.getMyAnswer().toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor spf = PreferenceManager.
                getDefaultSharedPreferences(getApplicationContext()).edit();
        spf.putInt("SCORE", count * 2);
        spf.apply();
        count = 0;
    }


    //填空题点击响应事件
    @Override
    public void OnClick(TextView v, int id, ReplaceSpan span) {
        mSpansManager.setData(editText.getText().toString(), null, mSpansManager.mOldSpan);
        mSpansManager.mOldSpan = id;
        //如果当前span身上有值，先赋值给et身上
        editText.setText(TextUtils.isEmpty(span.mText) ? "" : span.mText);
        editText.setSelection(span.mText.length());
        span.mText = "";
        //通过rf计算出et当前应该显示的位置
        RectF rf = mSpansManager.drawSpanRect(span);
        //设置EditText填空题中的相对位置
        mSpansManager.setEtXY(rf);
        mSpansManager.setSpanChecked(id);
    }

    /*
    * 获取服务器题目
    * */
    private void getTaskContent() {
        String httpUrl = URL.HTTP_URL_PROBLEM + position + "&pro_id=" + index;
        HttpUtil.sendOkHttpRequest(httpUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("wdl", "onFailure: "+e.getMessage());
                warn();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                if (!TextUtils.isEmpty(responseText)) {
                    Message message = new Message();
                    message.what = UPDATE_TEXT;
                    Bundle bundle = new Bundle();
                    bundle.putString("response_text", responseText);
                    message.setData(bundle);
                    myHandler.sendMessage(message);
                } else {
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

    //返回键的监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SharedPreferences pref = PreferenceManager.
                        getDefaultSharedPreferences(DetailTaskActivity.this);
                String name = pref.getString("USERNAME","");
                Log.d("wdl", "onOptionsItemSelected: "+name);
                String url = URL.HTTP_URL_UPD_SCORE+name+"&sco="+count*2;
                HttpUtil.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("wdl", "onResponse: 更新分数失败");
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("wdl", "onResponse: 更新分数成功");
                    }
                });
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }
}
