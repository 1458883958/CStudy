package com.example.wudelin.cstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wudelin.cstudy.db.Diary;
import com.example.wudelin.cstudy.recyclerviewadapter.Note;
import com.example.wudelin.cstudy.recyclerviewadapter.NoteAdapter;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wudelin on 2017/12/14.
 */

public class NoteListActivity extends AppCompatActivity {

    private List<Note> mNoteList = new ArrayList<>();
    private List<Diary> mDiaryList;
    private NoteAdapter noteAdapter;
    private RecyclerView recyclerView;
    //private SearchView searchView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_content);
        Toolbar toolbar = findViewById(R.id.toolbar_xml);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("笔记");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        inits();

        recyclerView = findViewById(R.id.recycler_view_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(mNoteList);
        noteAdapter.setmOnItemOnClickListener(new NoteAdapter.OnItemOnClickListener() {
            @Override
            public void onItemOnClick(View view, int pos) {
                Note note = mNoteList.get(pos);
                Intent intent = new Intent(NoteListActivity.this, LookNoteActivity.class);
                intent.putExtra(LookNoteActivity.DIARY_DATE, note.getDate());
                intent.putExtra(LookNoteActivity.DIARY_TITLE, note.getTitle());
                intent.putExtra(LookNoteActivity.DIARY_CONTENT, note.getContent());
                startActivity(intent);
            }

            @Override
            public void onItemLongOnClick(View view, int pos) {
                showPopMenu(view, pos);
            }
        });
        recyclerView.setAdapter(noteAdapter);
    }

    private void showPopMenu(View view, final int pos) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.note_del:
                        noteAdapter.removeItem(pos);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        popupMenu.show();
    }

    private void inits() {
        mDiaryList = DataSupport.findAll(Diary.class);
        //Log.d("wdl", "inits: "+mDiaryList.size());
        if (mDiaryList.size() > 0) {
            mNoteList.clear();
            for (Diary diary : mDiaryList) {
                Note note = new Note(diary.getDate(), diary.getTitle(), diary.getDiaryContent());
                mNoteList.add(note);
            }
            //noteAdapter.notifyDataSetChanged();
        } else {
            //从服务器获取
        }
    }

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
