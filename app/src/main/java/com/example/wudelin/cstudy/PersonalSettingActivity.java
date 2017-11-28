package com.example.wudelin.cstudy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hyphenate.chat.EMClient;

/**
 * Created by wudelin on 2017/11/28.
 */

public class PersonalSettingActivity extends AppCompatActivity{
    private Button logOut;
    boolean isLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_setting);
        logOut = findViewById(R.id.logout);
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(PersonalSettingActivity.this);
        isLogin = pref.getBoolean("IS_LOGIN",false);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(isLogin) {
                   EMClient.getInstance().logout(true);
                   SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(PersonalSettingActivity.this).edit();
                   prefs.putBoolean("IS_LOGIN",false);
                   prefs.apply();
                   finish();
               }
            }
        });
    }
}
