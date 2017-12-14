package com.example.wudelin.cstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wudelin.cstudy.widget.LinedEditText;

/**
 * Created by wudelin on 2017/12/14.
 */

public class LookNoteActivity extends AppCompatActivity {

    private TextView diaryDate;
    private EditText diaryTitle;
    private LinedEditText diaryContent;
    public static final String DIARY_DATE = "diary_date";
    public static final String DIARY_TITLE = "diary_title";
    public static final String DIARY_CONTENT = "diary_content";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_personal_makenote);
        diaryDate = findViewById(R.id.look_diary_tv_date);
        diaryTitle = findViewById(R.id.look_diary_et_title);
        diaryContent = findViewById(R.id.look_diary_et_content);
        Intent intent = getIntent();
        diaryDate.setText(intent.getStringExtra(DIARY_DATE));
        diaryTitle.setText(intent.getStringExtra(DIARY_TITLE));
        diaryContent.setText(intent.getStringExtra(DIARY_CONTENT));
        Toolbar toolbar = findViewById(R.id.toolbar_xml);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("查看");
            actionBar.setDisplayHomeAsUpEnabled(true);
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
}
