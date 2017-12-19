package com.example.wudelin.cstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wudelin.cstudy.recyclerviewadapter.Learn;
import com.example.wudelin.cstudy.recyclerviewadapter.LearnAdapter;
import com.example.wudelin.cstudy.widget.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wudelin on 2017/11/23.
 */

public class LearnFragment extends PageFragment {
    private List<Integer> viewList;
    //private List<Integer> imgs;
    private Integer[] imgs = {R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d};
    private Banner banner;
    //界面
    private List<Learn> learnList;
    private RecyclerView recyclerView;
    private LearnAdapter learnAdapter;
    private int[] learnImgs = {R.mipmap.l1,R.mipmap.l2,R.mipmap.l3,R.mipmap.l4,R.mipmap.l5,R.mipmap.l7,R.mipmap.l8};
    private String[] learnTitle =  {"基础","逻辑思维",
            "循环语句","函数",
            "数组","指针·链表","结构体"};
    private String[] learnTime = {"3分28秒","1分56秒",
            "3分24秒","3分46秒",
            "3分13秒","9分19秒","2分06秒"};

    private LinearLayoutManager layoutManager;
    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.viewpage_fragment_learn,container,false);
        recyclerView = mView.findViewById(R.id.recycler_view);
        viewList = new ArrayList<>();
        learnList = new ArrayList<>();
        inits();
        layoutManager = new LinearLayoutManager(mView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        learnAdapter = new LearnAdapter(learnList);
        recyclerView.setAdapter(learnAdapter);
        setBanner();
        return mView;
    }

    private void setBanner() {
        for (int i = 0; i < imgs.length; i++)
            viewList.add(imgs[i]);
        banner =  mView.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.ZoomOut);
        banner.setDelayTime(4000);
        //设置图片集合
        banner.setImages(viewList);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(mView.getContext(),BannerWebActivity.class);
                intent.putExtra(BannerWebActivity.BANNER_POSITION,position);
                startActivity(intent);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void inits() {
        for(int i = 0;i<learnImgs.length;i++){
            Learn learn = new Learn(learnImgs[i],learnTime[i],learnTitle[i]);
            learnList.add(learn);
        }
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
