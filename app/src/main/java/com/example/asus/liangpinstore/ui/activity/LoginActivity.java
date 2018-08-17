package com.example.asus.liangpinstore.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.http.LoginHttp;
import com.example.asus.liangpinstore.ui.base.BaseActivity;
import com.example.asus.liangpinstore.utils.DjhJumpUtil;
import com.example.asus.liangpinstore.utils.Utils;
import com.framework.lplibs.json.GsonUtil;


import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.user_phone)
    EditText userPhone;
    @BindView(R.id.user_pwd)
    EditText userPwd;
    private String phone;
    private String pwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        userPhone.setSelection(userPhone.getText().length());
        userPwd.setSelection(userPwd.getText().length());
    }

    @Override
    protected void initData() {
        onBackImagButton();
    }


    @OnClick({R.id.forget_pwd, R.id.login_btn, R.id.wechat, R.id.qq, R.id.weibo,R.id.go_registe,})
    public void onViewClicked(View view) {
        phone = userPhone.getText().toString();
        pwd = userPwd.getText().toString();
        switch (view.getId()) {
            case R.id.forget_pwd:
                break;
            case R.id.go_registe:
                DjhJumpUtil.getInstance().startBaseActivity(this, RegisterActivity.class);
                break;
            case R.id.login_btn:
                if (!isEdit(phone,pwd)) return;
                if (!Utils.isNetworkAvailable(activity)){
                    Toast.makeText(activity,"网络异常～",Toast.LENGTH_SHORT).show();
                    return;
                }
                showLpDialog("登陆中");
                new LoginHttp(new LoginHttp.OnLogin() {
                    @Override
                    public void error() {

                    }

                    @Override
                    public void successful() {
                        dismissfxDialog();
                        finishActivity();
                    }
                },LoginActivity.this).httpData(phone,pwd);

                break;
            case R.id.wechat:
                break;
            case R.id.qq:
                break;
            case R.id.weibo:
                break;
                default:
                    break;
        }
    }

    private boolean isEdit(String phone,String pwd){
        boolean isfalse = true;
        if (GsonUtil.isEmpty(phone)){
            Toast.makeText(activity,"请输入手机号",Toast.LENGTH_SHORT).show();
            isfalse = false;
        }else if (!Utils.isMobileNO(phone)){
            Toast.makeText(activity,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
            isfalse = false;
        }else if (GsonUtil.isEmpty(pwd)){
            Toast.makeText(activity,"请输入密码",Toast.LENGTH_SHORT).show();
            isfalse = false;
        }else if (Utils.hasChinese(pwd)){
            Toast.makeText(activity,"输入密码不允许有中文",Toast.LENGTH_SHORT).show();
            isfalse = false;
        }
        return isfalse;
    }
}
