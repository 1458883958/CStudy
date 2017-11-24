package com.example.wudelin.cstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wudelin on 2017/11/23.
 */

public class TitleFragment extends Fragment {

    private View mRootView;
    private TextView mTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.viewpage_fragment_title, container, false);
        mTitle = mRootView.findViewById(R.id.fragment_title_txt);
        return mRootView;
    }

    public void setCurrentTab(int positon) {
        if (mTitle != null) {
            switch (positon) {
                case 0:
                    mTitle.setText("学习");
                    break;
                case 1:
                    mTitle.setText("任务");
                    break;
                case 2:
                    mTitle.setText("互动");
                    break;
                case 3:
                    mTitle.setText("个人中心");
                    break;
                default:
                    break;
            }
        }
    }
}
