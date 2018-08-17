package com.example.asus.liangpinstore.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.constants.MyConstants;
import com.example.asus.liangpinstore.ui.adapter.ProductAdAdaoter;
import com.example.asus.liangpinstore.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class GoodsDetailsActivity extends BaseActivity {

    @BindView(R.id.details_banner)
    ViewPager detailsBanner;
    @BindView(R.id.index)
    TextView index;
    private ArrayList<String> img = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        String stringExtra = getIntent().getStringExtra(MyConstants.GOOD_ID);
        img.add("http://img4.hqbcdn.com/product/79/f3/79f3ef1b0b2283def1f01e12f21606d4.jpg");
        img.add("http://img14.hqbcdn.com/product/77/6c/776c63e6098f05fdc5639adc96d8d6ea.jpg");
        img.add("http://img13.hqbcdn.com/product/41/ca/41cad5139371e4eb1ce095e5f6224f4d.jpg");
        img.add("http://img10.hqbcdn.com/product/fa/ab/faab98caca326949b87b770c8080e6cf.jpg");
        img.add("http://img2.hqbcdn.com/product/6b/b8/6bb86086397a8cd0525c449f29abfaff.jpg");
        ProductAdAdaoter adAdaoter = new ProductAdAdaoter(activity,img);
        detailsBanner.setAdapter(adAdaoter);
        index.setText(1 + "/" + img.size());
        detailsBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index.setText(++position + "/" + img.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        onBackDetailsImg();

    }

    @Override
    protected void initData() {

    }

}
