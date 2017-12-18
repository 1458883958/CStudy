package com.example.wudelin.cstudy;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wudelin.cstudy.db.Content;
import com.example.wudelin.cstudy.util.DataSource;
import com.example.wudelin.cstudy.util.HttpUtil;
import com.example.wudelin.cstudy.util.URL;
import com.example.wudelin.cstudy.util.Utility;
import com.oragee.banners.BannerView;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import chuangyuan.ycj.videolibrary.listener.LoadModelType;
import chuangyuan.ycj.videolibrary.listener.OnGestureBrightnessListener;
import chuangyuan.ycj.videolibrary.listener.OnGestureProgressListener;
import chuangyuan.ycj.videolibrary.listener.OnGestureVolumeListener;
import chuangyuan.ycj.videolibrary.video.ManualPlayer;
import chuangyuan.ycj.videolibrary.widget.VideoPlayerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wudelin on 2017/11/25.
 */

public class DetailLearnActivity extends AppCompatActivity {

   // private List<Content> contentList;
    private TextView exo_video_dialog_pro_text;
    private ManualPlayer exoPlayerManager;
    private VideoPlayerView videoPlayerView;
    private ImageView videoAudioImg, videoBrightnessImg;
    private long currPosition = 0;
    private ProgressDialog progressDialog;
    private int time = 0;
    /***显示音频和亮度***/
    private ProgressBar videoAudioPro, videoBrightnessPro;
    public static final String VIEW_NAME_HEADER_IMAGE = "123";
    private boolean isEnd;
    public int position;
    private TextView tvContent;
    private int[] totalTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_detail);
        initTime();
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        videoPlayerView = findViewById(R.id.exo_play_context_id);
        exo_video_dialog_pro_text = findViewById(R.id.exo_video_dialog_pro_text);
        videoAudioImg = findViewById(R.id.exo_video_audio_img);
        videoAudioPro = findViewById(R.id.exo_video_audio_pro);
        videoBrightnessImg = findViewById(R.id.exo_video_brightness_img);
        videoBrightnessPro = findViewById(R.id.exo_video_brightness_pro);
        tvContent = findViewById(R.id.learn_content);
        tvContent.setMovementMethod(new ScrollingMovementMethod());
        ViewCompat.setTransitionName(videoPlayerView, VIEW_NAME_HEADER_IMAGE);
        exoPlayerManager = new ManualPlayer(this, videoPlayerView, new DataSource(this));
        exoPlayerManager.setPosition(currPosition);
        exoPlayerManager.setTitle("返回");
        //设置加载显示模式
        exoPlayerManager.setLoadModel(LoadModelType.SPEED);
        exoPlayerManager.setPlayUri(URL.HTTP_URL_VEDIO + position + ".mp4");
        exoPlayerManager.setOnPlayClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailLearnActivity.this, "", Toast.LENGTH_LONG).show();
                //处理业务操作 完成后，
                exoPlayerManager.startPlayer();//开始播放

            }
        });
        Glide.with(this)
                .load(intent.getIntExtra("imageView", 0))
                .into(videoPlayerView.getPreviewImage());

        //自定义布局使用
        videoPlayerView.getReplayLayout().findViewById(R.id.replay_btn_imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailLearnActivity.this, "自定义分享", Toast.LENGTH_SHORT).show();
            }
        });
        videoPlayerView.getErrorLayout().findViewById(R.id.exo_player_error_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailLearnActivity.this, "自定义错误", Toast.LENGTH_SHORT).show();
            }
        });
        videoPlayerView.getPlayHintLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailLearnActivity.this, "自定义提示", Toast.LENGTH_SHORT).show();
            }
        });

        exoPlayerManager.setOnGestureBrightnessListener(new OnGestureBrightnessListener() {
            @Override
            public void setBrightnessPosition(int mMax, int currIndex) {
                //显示你的布局
                videoPlayerView.getGestureBrightnessLayout().setVisibility(View.VISIBLE);
                //为你布局显示内容自定义内容
                videoBrightnessPro.setMax(mMax);
                videoBrightnessImg.setImageResource(chuangyuan.ycj.videolibrary.R.drawable.ic_brightness_6_white_48px);
                videoBrightnessPro.setProgress(currIndex);
            }
        });
        exoPlayerManager.setOnGestureProgressListener(new OnGestureProgressListener() {
            @Override
            public void showProgressDialog(long seekTimePosition, long duration, String seekTime, String totalTime) {
                //显示你的布局
                videoPlayerView.getGestureProgressLayout().setVisibility(View.VISIBLE);
                exo_video_dialog_pro_text.setTextColor(Color.RED);
                exo_video_dialog_pro_text.setText(seekTime + "/" + totalTime);
            }
        });
        exoPlayerManager.setOnGestureVolumeListener(new OnGestureVolumeListener() {
            @Override
            public void setVolumePosition(int mMax, int currIndex) {
                //显示你的布局
                videoPlayerView.getGestureAudioLayout().setVisibility(View.VISIBLE);
                //为你布局显示内容自定义内容
                videoAudioPro.setMax(mMax);
                videoAudioPro.setProgress(currIndex);
                videoAudioImg.setImageResource(currIndex == 0 ? R.drawable.ic_volume_off_white_48px : R.drawable.ic_volume_up_white_48px);
            }
        });
        getLearnContent();

    }

    private void initTime() {
        totalTime = new int[7];
        totalTime[0] = 208;totalTime[1] = 116;
        totalTime[2] = 204;totalTime[3] = 226;
        totalTime[4] = 193;totalTime[5] = 559;
        totalTime[6] = 126;
    }

    private void getLearnContent() {
        showProgressDialog();
        String address = URL.HTTP_URL_CONTENT + position;
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        colseProgressDialog();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if (!TextUtils.isEmpty(responseText)) {
                    result = true;
                    final Spanned responseString;
                    if (android.os.Build.VERSION.SDK_INT >= 25) {
                        responseString = Html.fromHtml(responseText, Html.FROM_HTML_MODE_LEGACY);
                    } else {
                        responseString = Html.fromHtml(responseText);
                    }
                    if (result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                colseProgressDialog();
                                tvContent.setText(responseString.toString());
                            }
                        });
                    }
                }
            }
        });
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(DetailLearnActivity.this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void colseProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        exoPlayerManager.onResume();
        new Thread(new MyThread()).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        exoPlayerManager.onPause();
    }

    @Override
    protected void onDestroy() {
        exoPlayerManager.onDestroy();
        super.onDestroy();
        SharedPreferences spf = PreferenceManager.
                getDefaultSharedPreferences(getApplicationContext());
        float progress = spf.getFloat("PROGRESS", 0.0f);

        float current = (float)(time*100/totalTime[position]);

        //Log.d("wdl", "onDestroy: "+progress+" "+current);
        if(progress<current)
            updProgress();
    }

    private void updProgress() {
        SharedPreferences spf = PreferenceManager.
                getDefaultSharedPreferences(getApplicationContext());
        String username = spf.getString("USERNAME", "");
        String url = URL.HTTP_URL_UPD_PRO+username+"&chapter="+position+
                "&percent="+(float)(time*100/totalTime[position]);
        Log.d("wdl", "updProgress: "+url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("wdl", "update progress success");
                SharedPreferences.Editor sprf = PreferenceManager.
                        getDefaultSharedPreferences(getApplicationContext()).edit();
                sprf.putFloat("PROGRESS",(float)(time*100/totalTime[position]));
                sprf.apply();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        exoPlayerManager.onConfigurationChanged(newConfig);//横竖屏切换
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (exoPlayerManager.onBackPressed()) {//使用播放返回键监听
            Toast.makeText(DetailLearnActivity.this, "返回", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra("isEnd", isEnd);
            intent.putExtra("currPosition", exoPlayerManager.getCurrentPosition());
            setResult(RESULT_OK, intent);
            ActivityCompat.finishAfterTransition(this);
            finish();
        }

    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {          // handle
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    time++;
                    break;
                default:
            }
            super.handleMessage(msg);
        }
    };

    public class MyThread implements Runnable {      // thread
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);     // sleep 1000ms
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                }
            }
        }
    }
}
