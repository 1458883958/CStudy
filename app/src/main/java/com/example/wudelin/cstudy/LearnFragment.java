package com.example.wudelin.cstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.viewpage_fragment_learn,container,false);
        bannerView = mView.findViewById(R.id.spanner_bar);
        viewList = new ArrayList<>();
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
}
