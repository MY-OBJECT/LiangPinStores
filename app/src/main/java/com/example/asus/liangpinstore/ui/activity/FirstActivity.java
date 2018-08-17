package com.example.asus.liangpinstore.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.asus.liangpinstore.DemoHelper;
import com.example.asus.liangpinstore.MainActivity;
import com.example.asus.liangpinstore.R;
import com.hyphenate.chat.EMClient;

public class FirstActivity extends AppCompatActivity {

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView im = new ImageView(this);
        im.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        im.setLayoutParams(params);
        im.setImageResource(R.mipmap.ico_firstpager);
        setContentView(im);
        DemoHelper.getInstance().initHandler(this.getMainLooper());
        MyHandler myHandler = new MyHandler();
        myHandler.sendEmptyMessageDelayed(0,3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DemoHelper.getInstance().isLoggedIn()){
            EMClient.getInstance().chatManager().loadAllConversations();
            EMClient.getInstance().groupManager().loadAllGroups();
        }
    }
}
