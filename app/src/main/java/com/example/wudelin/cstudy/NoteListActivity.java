package com.example.wudelin.cstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.wudelin.cstudy.db.Diary;
import com.example.wudelin.cstudy.recyclerviewadapter.Note;
import com.example.wudelin.cstudy.recyclerviewadapter.NoteAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wudelin on 2017/12/14.
 */

public class NoteListActivity extends AppCompatActivity{

    private List<Note> mNoteList = new ArrayList<>();
    private List<Diary> mDiaryList;
    private NoteAdapter noteAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_content);
        Toolbar toolbar = findViewById(R.id.toolbar_xml);
       setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
       if(actionBar!=null){
           actionBar.setTitle("笔记");
           actionBar.setDisplayHomeAsUpEnabled(true);
       }
        recyclerView = findViewById(R.id.recycler_view_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        inits();
        noteAdapter = new NoteAdapter(mNoteList);
        recyclerView.setAdapter(noteAdapter);
    }

    private void inits() {
        mDiaryList = DataSupport.findAll(Diary.class);
        if(mDiaryList.size()>0){
            mNoteList.clear();
            for(Diary diary:mDiaryList){
                Note note = new Note(diary.getDate(),diary.getTitle(),diary.getDiaryContent());
                mNoteList.add(note);
            }
            //noteAdapter.notifyDataSetChanged();
        }else{
            //从服务器获取
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
