package com.example.wudelin.cstudy;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wudelin.cstudy.util.HttpUtil;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.wudelin.cstudy.util.URL.HTTP_URL_REG;

/**
 * Created by wudelin on 2017/11/27.
 */

public class RegActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edRegUsername;
    private EditText edRegPassword;
    private Button regBtn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_reg);
        toolbar = findViewById(R.id.toolbar_xml);
        edRegUsername = findViewById(R.id.ed_reg_username);
        edRegPassword = findViewById(R.id.ed_reg_password);
        regBtn = findViewById(R.id.start_reg_btn);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("注册");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String url = HTTP_URL_REG+"username="+edRegUsername.getText().toString()+"&password="+edRegPassword.getText().toString().trim();
                            EMClient.getInstance().createAccount(edRegUsername.getText().toString().trim(),
                                    edRegPassword.getText().toString().trim());
                            HttpUtil.sendOkHttpRequest(url, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    Log.d("wdl", "注册成功");
                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    colseProgressDialog();
                                    Toast.makeText(RegActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                            finish();
                        } catch (final HyphenateException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int errorCode = e.getErrorCode();
                                    if (errorCode == EMError.NETWORK_ERROR) {
                                        Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
                                    } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                                        Toast.makeText(getApplicationContext(), "用户已存在！", Toast.LENGTH_SHORT).show();
                                    } else if (errorCode == EMError.USER_PERMISSION_DENIED) {
                                        Toast.makeText(getApplicationContext(), "注册失败，无权限！", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "注册失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    colseProgressDialog();
                                }
                            });

                        }
                    }
                }).start();
            }
        });
    }
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(RegActivity.this);
            progressDialog.setMessage("正在注册...");
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


    //返回键的监听事件
    @Override
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
