package com.example.wudelin.cstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wudelin.cstudy.db.Diary;
import com.example.wudelin.cstudy.widget.LinedEditText;

import java.util.Date;

/**
 * Created by wudelin on 2017/12/7.
 */

public class MakeNoteActivity extends AppCompatActivity {

    //笔记内容
    private LinedEditText linedEditText;
    private TextView diaryDate;
    private EditText diaryTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_makenote);
        Toolbar toolbar = findViewById(R.id.toolbar_xml);
        linedEditText = findViewById(R.id.add_diary_et_content);
        diaryDate = findViewById(R.id.add_diary_tv_date);
        diaryTitle = findViewById(R.id.add_diary_et_title);
        diaryDate.setText(new Date().toString());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("添加笔记");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        findViewById(R.id.add_diary_fab_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = diaryDate.getText().toString();
                String title = diaryTitle.getText().toString();
                String content = linedEditText.getText().toString();
                if(!TextUtils.isEmpty(date)&&!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(content)) {
                    Diary diary = new Diary();
                    diary.setDate(date);
                    diary.setTitle(title);
                    diary.setDiaryContent(content);
                    diary.save();
                    Log.d("wdl", "onClick: success");
                    Toast.makeText(MakeNoteActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
        findViewById(R.id.add_diary_fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
            finish();break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
