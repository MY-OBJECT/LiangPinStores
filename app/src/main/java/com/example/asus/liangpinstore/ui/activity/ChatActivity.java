package com.example.asus.liangpinstore.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.ui.base.BaseActivity;


public class ChatActivity extends BaseActivity {


    public static ChatActivity activityInstance;
    public String toChatUsername;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityInstance = this;
        toChatUsername = getIntent().getExtras().getString("userId");

    }

    @Override
    protected void initData() {

    }
    public String getToChatUsername(){
        return toChatUsername;
    }
}
