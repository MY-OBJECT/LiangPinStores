package com.example.asus.liangpinstore.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.constants.MyConstants;
import com.example.asus.liangpinstore.ui.base.BaseActivity;
import com.example.asus.liangpinstore.ui.base.BaseFragment;
import com.example.asus.liangpinstore.ui.fragments.setfragment.FrSetName;
import com.example.asus.liangpinstore.ui.fragments.setfragment.FrSetPhone;
import com.example.asus.liangpinstore.ui.fragments.setfragment.FrSetPwd;
import com.example.asus.liangpinstore.ui.fragments.setfragment.FrSetSex;
import com.example.asus.liangpinstore.ui.fragments.setfragment.FrSetSingnature;

public class UserSetActivity extends BaseActivity {

    private BaseFragment fragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_set;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        int type = getIntent().getIntExtra(MyConstants.bundle_type,MyConstants.user_edit_name);
        switch (type){
            case MyConstants.user_edit_name:
                setLpTtitle("修改名字");
                fragment = new FrSetName();
                break;
            case MyConstants.user_edit_pwd:
                setLpTtitle("修改密码");
                fragment = new FrSetPwd();
                break;
            case MyConstants.user_edit_sex:
                setLpTtitle("修改性别");
                fragment = new FrSetSex();
                break;
            case MyConstants.user_edit_signature:
                setLpTtitle("修改签名");
                fragment = new FrSetSingnature();
                break;
            case MyConstants.user_edit_tel_phone:
                setLpTtitle("修改手机号");
                fragment = new FrSetPhone();
                break;
                default:
                    break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.tab_fragment, fragment).commit();
        onBackImagButton();
        onRightBtn("完成");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRightBtnClick(View view) {
        super.onRightBtnClick(view);
        fragment.httpData();
    }
}
