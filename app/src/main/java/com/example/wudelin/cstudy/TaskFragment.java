package com.example.wudelin.cstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.wudelin.cstudy.recyclerviewadapter.Section;
import com.example.wudelin.cstudy.recyclerviewadapter.SectionAdapter;

/**
 * Created by wudelin on 2017/11/23.
 */

public class TaskFragment extends PageFragment{
    private View mView;
    private List<Section> mSectionList = new ArrayList<>();
    private SectionAdapter mAdapter;
    private Section[] mSection= {new Section(R.mipmap.r1,"第一章"),
            new Section(R.mipmap.r2,"第二章"),
            new Section(R.mipmap.r3,"第三章"),
            new Section(R.mipmap.r4,"第四章"),
            new Section(R.mipmap.r5,"第五章"),
            new Section(R.mipmap.r6,"第六章"),
            new Section(R.mipmap.r7,"第七章"),
            new Section(R.mipmap.r8,"第八章")};;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.viewpage_fragment_task,container,false);
        recyclerView = mView.findViewById(R.id.recycler_view_task);
        inits();
        GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        mAdapter = new SectionAdapter(mSectionList);
        recyclerView.setAdapter(mAdapter);
        return mView;
    }

    private void inits() {
        mSectionList.clear();
        Collections.addAll(mSectionList, mSection);
    }
}
