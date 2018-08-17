package com.example.asus.liangpinstore.ui.activity;

import android.os.Bundle;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.ui.base.BaseActivity;

public class NotifyActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notify;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setLpTtitle("通知");
        onBackImagButton();
    }

    @Override
    protected void initData() {

    }

}
