package com.example.wudelin.cstudy;

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

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by wudelin on 2017/11/27.
 */

public class RegActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edRegUsername;
    private EditText edRegPassword;
    private Button regBtn;
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
        if(actionBar!=null){
            actionBar.setTitle("注册");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().createAccount(edRegUsername.getText().toString().trim(),
                                    edRegPassword.getText().toString().trim());
                            Log.d("wdl","注册成功");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            Log.d("wdl", ""+e.getErrorCode()+" "+e.getMessage());
                        }
                    }
                }).start();
            }
        });
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
