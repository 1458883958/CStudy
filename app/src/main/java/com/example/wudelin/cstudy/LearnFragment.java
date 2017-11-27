package com.example.wudelin.cstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wudelin.cstudy.recyclerviewadapter.Learn;
import com.example.wudelin.cstudy.recyclerviewadapter.LearnAdapter;
import com.oragee.banners.BannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wudelin on 2017/11/23.
 */

public class LearnFragment extends PageFragment {
    private BannerView bannerView;
    private List<View> viewList;
    private int[] imgs = {R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d};

    //界面
    private List<Learn> learnList;
    private RecyclerView recyclerView;
    private LearnAdapter learnAdapter;
    private int[] learnImgs = {R.mipmap.l1,R.mipmap.l2,R.mipmap.l3,R.mipmap.l4,R.mipmap.l5,R.mipmap.l7,R.mipmap.l8};
    private String[] learnTitle =  {"二维数组","指针","函数","结构体","字符串","逻辑思维","循环语句","链表"};

    private String[] learnTime = {"3分30秒","7分35秒","8分40秒","15分35秒","9分25秒","6分35秒","9分55秒","9分35秒"};

    private LinearLayoutManager layoutManager;
    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.viewpage_fragment_learn,container,false);
        bannerView = mView.findViewById(R.id.spanner_bar);
        recyclerView = mView.findViewById(R.id.recycler_view);
        viewList = new ArrayList<>();
        learnList = new ArrayList<>();
        inits();
        layoutManager = new LinearLayoutManager(mView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        learnAdapter = new LearnAdapter(learnList);
        recyclerView.setAdapter(learnAdapter);
        for (int i = 0; i < imgs.length; i++) {
            ImageView image = new ImageView(mView.getContext());
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(imgs[i]);
            viewList.add(image);
        }
        bannerView.setViewList(viewList);
        bannerView.startLoop(true);
        return mView;
    }

    private void inits() {
        for(int i = 0;i<learnImgs.length;i++){
            Learn learn = new Learn(learnImgs[i],learnTime[i],learnTitle[i]);
            learnList.add(learn);
        }
    }
}
