package com.example.asus.liangpinstore.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by asus on 2018/1/17.
 */

public class MyTopViewPager extends ViewPager {
    public MyTopViewPager(Context context) {
        super(context);
    }

    public MyTopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // true 申请父控件不拦截我的touch事件,false 默认父类先拦截事件
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
