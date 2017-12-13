package com.example.wudelin.cstudy;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wudelin on 2017/11/23.
 */

public class TabFragment extends Fragment{
    private View mView;
    private TextView learnText;
    private TextView taskText;
    private TextView interactText;
    private TextView personalText;

    private int mDefaultTab = 0;
    private int mCurrentTab = -1;

    private OnTabClickInternalListener mTabClickInternalListener = new OnTabClickInternalListener();
    private OnTabClickListenser mOnTabClickListenser;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.viewpage_fragment_tab,container,false);
        initView();
        return mView;
    }

    private void initView() {
        learnText = mView.findViewById(R.id.viewpager_fragment_tab_learn);
        learnText.setTag(0);
        learnText.setOnClickListener(mTabClickInternalListener);
        taskText = mView.findViewById(R.id.viewpager_fragment_tab_task);
        taskText.setTag(1);
        taskText.setOnClickListener(mTabClickInternalListener);
        interactText = mView.findViewById(R.id.viewpager_fragment_tab_interact);
        interactText.setTag(2);
        interactText.setOnClickListener(mTabClickInternalListener);
        personalText = mView.findViewById(R.id.viewpager_fragment_tab_personal);
        personalText.setTag(3);
        personalText.setOnClickListener(mTabClickInternalListener);

    }
    public interface OnTabClickListenser {
        public void onTabClick(int tab);
    }

    private class OnTabClickInternalListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v != null && v.getTag() instanceof Integer) {
                final int tab = (Integer)v.getTag();
                if (tab != mCurrentTab && mOnTabClickListenser != null) {
                    setCurrentTab(tab);
                    mOnTabClickListenser.onTabClick(tab);
                }
            }
        }
    }

    public void setOnTabClickListenser(OnTabClickListenser listenser) {
        mOnTabClickListenser = listenser;
    }

    public void setCurrentTab(int tab) {
        if (learnText != null && taskText != null && interactText != null && personalText!=null) {
            mCurrentTab = tab;
            learnText.setTextColor(getResources().getColor(R.color.viewpager_fragment_tab_normal));
            learnText.setCompoundDrawablesWithIntrinsicBounds(null,
                    getResources().getDrawable(R.mipmap.tab_better_normal) , null, null);

            taskText.setTextColor(getResources().getColor(R.color.viewpager_fragment_tab_normal));
            taskText.setCompoundDrawablesWithIntrinsicBounds(null,
                    getResources().getDrawable(R.mipmap.tab_channel_normal), null, null);

            interactText.setTextColor(getResources().getColor(R.color.viewpager_fragment_tab_normal));
            interactText.setCompoundDrawablesWithIntrinsicBounds(null,
                    getResources().getDrawable(R.mipmap.tab_message_normal), null, null);

            personalText.setTextColor(getResources().getColor(R.color.viewpager_fragment_tab_normal));
            personalText.setCompoundDrawablesWithIntrinsicBounds(null,
                    getResources().getDrawable(R.mipmap.tab_my_normal), null, null);
            Drawable top;
            switch (tab) {
                case 0:
                    learnText.setTextColor(getResources().getColor(R.color.viewpager_fragment_tab_current));
                    top = getResources().getDrawable(R.mipmap.tab_better_pressed);
                    learnText.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                    break;
                case 1:
                    taskText.setTextColor(getResources().getColor(R.color.viewpager_fragment_tab_current));
                    top = getResources().getDrawable(R.mipmap.tab_channel_pressed);
                    taskText.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                    break;
                case 2:
                    interactText.setTextColor(getResources().getColor(R.color.viewpager_fragment_tab_current));
                    top = getResources().getDrawable(R.mipmap.tab_message_pressed);
                    interactText.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                    break;
                case 3:
                    personalText.setTextColor(getResources().getColor(R.color.viewpager_fragment_tab_current));
                    top = getResources().getDrawable(R.mipmap.tab_my_pressed);
                    personalText.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                    break;
                default:
                    break;
            }
        } else {
            //TabFragment在Activity的onResume之后才会onCreateView
            //setCurrentTab的时候控件还没初始化，存一下初始值在initView里再初始化
            mDefaultTab = tab;
        }
    }
}
