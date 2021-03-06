package com.example.wudelin.cstudy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private FragmentManager mFragment = getSupportFragmentManager();
    private ViewPager mViewpage;
    private TitleFragment mTitleFragment;
    private TabFragment mTabFragment;
    private ViewPagerAdapter mAdapter;
    private static final int DEFAULT_PAGE = 1; //默认页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTitleFragment = (TitleFragment) mFragment.findFragmentById(R.id.viewpager_fragment_title);
        mTabFragment = new TabFragment();
        mTabFragment.setOnTabClickListenser(new ViewPageTabClickListenser());
        mFragment.beginTransaction().replace(R.id.viewpager_fragment_container,mTabFragment).commit();
        mViewpage = findViewById(R.id.viewpager_fragment_page);
        mViewpage.setAdapter(mAdapter = new ViewPagerAdapter(mFragment));
        mViewpage.addOnPageChangeListener(new ViewPageChangeListener());
        setCurrentItem(DEFAULT_PAGE);
    }
    private class ViewPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            setCurrentItem(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<PageFragment> mFragments = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments.add(new LearnFragment());
            mFragments.add(new TaskFragment());
            mFragments.add(new InteractFragment());
            mFragments.add(new PersonalFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    private class ViewPageTabClickListenser implements TabFragment.OnTabClickListenser {
        @Override
        public void onTabClick(int tab) {
            setCurrentItem(tab);
        }
    }

    private void setCurrentItem(int item) {
        if (item == mViewpage.getCurrentItem()) {
            //此时是源于initView或onPageSelected的调用
            notifyPageChangeToFragments(item);
        } else {
            //此时是源于initView或onTabClick的调用，后续会自动触发一次onPageSelected
            mViewpage.setCurrentItem(item);
        }
    }

    private void notifyPageChangeToFragments(int item) {
        for (int page = 0; page != mAdapter.getCount(); ++page) {
            final Fragment fragment = mAdapter.getItem(page);
            if (fragment instanceof PageFragment) {
                if (page == item) {
                    ((PageFragment)fragment).onPageIn();
                } else {
                    ((PageFragment)fragment).onPageOut();
                }
            }
        }
        mTitleFragment.setCurrentTab(item);
        mTabFragment.setCurrentTab(item);
    }
}
