package com.example.wudelin.cstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wudelin on 2017/11/23.
 */

public class PersonalFragment extends PageFragment{
    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.viewpage_fragment_personal,container,false);
        return mView;
    }
}
