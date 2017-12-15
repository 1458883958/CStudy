package com.example.wudelin.cstudy;

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
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                } else {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    finish();
                }
            }
        });

    }

    private void signIn() {
        EMClient.getInstance().login(loginEdUsername.getText().toString().trim(),
                loginEdPassword.getText().toString().trim(), new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                        prefs.putString(PersonalFragment.USERNAME, loginEdUsername.getText().toString().trim());
                        prefs.putBoolean("IS_LOGIN",true);
                        prefs.putString("USERNAME",loginEdUsername.getText().toString().trim());
                        prefs.apply();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.d("wdl", "onError: " + i + " " + s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                }
        );
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
