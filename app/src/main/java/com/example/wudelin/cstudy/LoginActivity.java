package com.example.wudelin.cstudy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wudelin.cstudy.application.MyApplication;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by wudelin on 2017/11/27.
 */

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button loginBtn;
    private TextView regBtn;
    private TextView updBtn;
    private EditText loginEdUsername;
    private EditText loginEdPassword;
    boolean isLogin;
    private String roomId = "34053888737282";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_login);
        toolbar = findViewById(R.id.toolbar_xml);
        loginBtn = findViewById(R.id.login_btn);
        regBtn = findViewById(R.id.reg_btn);
        updBtn = findViewById(R.id.upd_btn);
        loginEdUsername = findViewById(R.id.ed_username);
        loginEdPassword = findViewById(R.id.ed_password);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        isLogin = pref.getBoolean("IS_LOGIN",false);
        Log.d("wdl", "onCreate: "+!isLogin);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //设置home键可用
            actionBar.setTitle("登录账号");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLogin) {
                    signIn();
                } else {
                    finish();
                }
            }
        });

    }

    private void signIn() {
        showProgressDialog();
        EMClient.getInstance().login(loginEdUsername.getText().toString().trim(),
                loginEdPassword.getText().toString().trim(), new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        joinRoom();
                        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                        prefs.putString(PersonalFragment.USERNAME, loginEdUsername.getText().toString().trim());
                        prefs.putBoolean("IS_LOGIN",true);
                        prefs.putString("USERNAME",loginEdUsername.getText().toString().trim());
                        prefs.apply();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                colseProgressDialog();
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onError(final int i,final String s) {
                        //Log.d("wdl", "onError: " + i + " " + s);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (i) {
                                    // 网络异常 2
                                    case EMError.NETWORK_ERROR:
                                        Toast.makeText(LoginActivity.this, "网络错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                        break;
                                    // 无效的用户名 101
                                    case EMError.INVALID_USER_NAME:
                                        Toast.makeText(LoginActivity.this, "无效的用户名 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                        break;
                                    // 无效的密码 102
                                    case EMError.INVALID_PASSWORD:
                                        Toast.makeText(LoginActivity.this, "无效的密码 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                        break;
                                    // 用户认证失败，用户名或密码错误 202
                                    case EMError.USER_AUTHENTICATION_FAILED:
                                        Toast.makeText(LoginActivity.this, "用户认证失败，用户名或密码错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                        break;
                                    // 用户不存在 204
                                    case EMError.USER_NOT_FOUND:
                                        Toast.makeText(LoginActivity.this, "用户不存在 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                        break;
                                    // 无法访问到服务器 300
                                    case EMError.SERVER_NOT_REACHABLE:
                                        Toast.makeText(LoginActivity.this, "无法访问到服务器 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                        break;
                                    // 等待服务器响应超时 301
                                    case EMError.SERVER_TIMEOUT:
                                        Toast.makeText(LoginActivity.this, "等待服务器响应超时 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                        break;
                                    // 服务器繁忙 302
                                    case EMError.SERVER_BUSY:
                                        Toast.makeText(LoginActivity.this, "服务器繁忙 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                        break;
                                    // 未知 Server 异常 303 一般断网会出现这个错误
                                    case EMError.SERVER_UNKNOWN_ERROR:
                                        Toast.makeText(LoginActivity.this, "未知的服务器异常 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        Toast.makeText(LoginActivity.this, "ml_sign_in_failed code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                        break;
                                }
                                colseProgressDialog();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                }
        );
    }

    private void joinRoom() {
            try {
                EMClient.getInstance().groupManager().joinGroup(roomId);//需异步处理
                Log.d("wdl", "joinRoom: " + "成功");
            } catch (HyphenateException e) {
                e.printStackTrace();
            }

    }
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("正在登录...");
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
