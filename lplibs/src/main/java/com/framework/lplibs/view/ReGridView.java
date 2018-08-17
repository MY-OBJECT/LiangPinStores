package com.framework.lplibs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by asus on 2018/3/15.
 */

public class ReGridView extends GridView {


    public ReGridView(Context context) {
        super(context);
    }

    public ReGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //测量方法
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){

        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
