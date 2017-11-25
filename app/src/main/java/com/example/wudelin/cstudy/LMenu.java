package com.example.wudelin.cstudy;

/**
 * Created by wudelin on 2017/11/25.
 */


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;


public class LMenu extends RelativeLayout implements View.OnClickListener {
    private ImageView home, next, last, submit;

    private List<ImageView> oViews;

    private boolean mFlag = true;

    private float mHiddenViewMeasuredHeight;

    public LMenu(Context context) {
        this(context, null);
    }

    public LMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.float_action_button, this);
        home = findViewById(R.id.home);
        next = findViewById(R.id.next_quest);
        last = findViewById(R.id.last_quest);
        submit = findViewById(R.id.submit_btn);

        //将四个Imageview放在集合里，方便管理
        oViews = new ArrayList<>();
        oViews.add(home);
        oViews.add(next);
        oViews.add(last);
        oViews.add(submit);

        home.setOnClickListener(this);
        next.setOnClickListener(this);
        last.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHiddenViewMeasuredHeight = (int)(w/12);
        Anim(mHiddenViewMeasuredHeight, -mHiddenViewMeasuredHeight, 0.5f, 1f, 0f, 90f);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                if (mFlag) {
                    Anim(-mHiddenViewMeasuredHeight, mHiddenViewMeasuredHeight, 1f, 0.5f, 90f, 0f);
                    mFlag = false;
                } else {
                    Anim(mHiddenViewMeasuredHeight, -mHiddenViewMeasuredHeight, 0.5f, 1f, 0f, 90f);
                    mFlag = true;
                }
                break;
            case R.id.next_quest:
                if (listener != null) {
                    listener.Next();
                }
                break;
            case R.id.last_quest:
                if (listener != null) {
                    listener.Last();
                }
                break;

            case R.id.submit_btn:
                if (listener != null) {
                    listener.Submit();
                }
                break;

            default:
                break;
        }
    }

    private void Anim(float mHiddenViewMeasuredHeightBegin, float mHiddenViewMeasuredHeightyClose, float x, float y, float anglex, float angley) {

        //设置动画。用于弹出和收回
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(oViews.get(0), "alpha", x, y);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(oViews.get(1), "translationY", mHiddenViewMeasuredHeightBegin);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(oViews.get(2), "translationY", mHiddenViewMeasuredHeightBegin);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(oViews.get(3), "translationY", mHiddenViewMeasuredHeightBegin);


        //设置动画，用于旋转效果
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(oViews.get(0), "rotation", anglex, 120f, angley);

        ObjectAnimator animator5 = ObjectAnimator.ofFloat(oViews.get(1), "rotationX", anglex, 120f, angley);

        ObjectAnimator animator6 = ObjectAnimator.ofFloat(oViews.get(2), "rotationY", anglex, 120f, angley);

        ObjectAnimator animator7 = ObjectAnimator.ofFloat(oViews.get(3), "rotationX", anglex, 120f, angley);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.setInterpolator(new OvershootInterpolator());
        set.playTogether(animator0, animator1, animator2, animator3, animator4, animator5, animator6, animator7);
        set.start();


    }

    onMenuClickListener listener;

    //定义回调接口
    public interface onMenuClickListener {
        void Next();

        void Last();

        void Submit();
    }

    //设置事件回调
    public void setonMenuClickListener(onMenuClickListener listener) {
        this.listener = listener;
    }
}