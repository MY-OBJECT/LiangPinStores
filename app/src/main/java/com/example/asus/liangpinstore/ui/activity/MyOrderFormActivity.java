package com.example.asus.liangpinstore.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.ui.adapter.MyOrderAdapter;
import com.example.asus.liangpinstore.ui.base.BaseActivity;
import com.example.asus.liangpinstore.ui.fragments.orderfragment.FrAllOrder;
import com.example.asus.liangpinstore.view.CustomViewpager;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;

public class MyOrderFormActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    CustomViewpager viewpager;
    @BindView(R.id.tab_indicator)
    TabLayout tabIndicator;
    private boolean shouldSendSpeed = true;
    private int position;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_dingdan;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        position = getIntent().getIntExtra("position",0);
        setLpTtitle("我的订单");
        final ArrayList<String> strings = new ArrayList<>();
        final ArrayList<Fragment> fragments = new ArrayList<>();

        strings.add(getString(R.string.tab_order_all));
        strings.add(getString(R.string.tab_order_wait_pay));
        strings.add(getString(R.string.tab_order_wait_fa));
        strings.add(getString(R.string.tab_order_wait_shou));
        strings.add(getString(R.string.tab_order_wait_ping));
        strings.add(getString(R.string.tab_order_wait_service));

        fragments.add(FrAllOrder.newInstance("order","waitPayList"));
        fragments.add(FrAllOrder.newInstance("order","waitPayList"));
        fragments.add(FrAllOrder.newInstance("order","waitFaList"));
        fragments.add(FrAllOrder.newInstance("order","waitShouList"));
        fragments.add(FrAllOrder.newInstance("order","waitPingList"));
        fragments.add(FrAllOrder.newInstance("order","service"));

        MyOrderAdapter adapter = new MyOrderAdapter(getSupportFragmentManager(),strings,fragments);
        viewpager.setAdapter(adapter);
        tabIndicator.setupWithViewPager(viewpager);
        reflex(tabIndicator);
        viewpager.setCurrentItem(position);
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                ((FrSetSingnature) fragments.get(tab.getPosition())).loaddingData();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        onBackImagButton();
    }


    @Override
    protected void initData() {

    }

    public void reflex(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dip2px(tabLayout.getContext(), 10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
