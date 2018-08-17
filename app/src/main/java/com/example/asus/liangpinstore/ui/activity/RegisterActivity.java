package com.example.asus.liangpinstore.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.http.RequestUtil;
import com.example.asus.liangpinstore.ui.base.BaseActivity;
import com.example.asus.liangpinstore.utils.DjhJumpUtil;
import com.example.asus.liangpinstore.utils.Utils;
import com.framework.lplibs.json.GsonUtil;
import com.framework.lplibs.utils.OkHttp;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.output_phone)
    EditText outputPhone;
    @BindView(R.id.output_btain_code)
    EditText outputBtainCode;
    @BindView(R.id.huoqu_code)
    TextView huoquCode;
    @BindView(R.id.output_pwd)
    EditText outputPwd;
    @BindView(R.id.output_pwd_ok)
    EditText outputPwdOk;
    @BindView(R.id.go_register)
    Button goRegister;
    @BindView(R.id.go_login)
    TextView gologin;
    private int time = 60;
    private MyHandler handler;
    private String phone;
    private String code;
    private String pwd;
    private String pwdOk;
    private String netcode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        outputPhone.setSelection(outputPhone.getText().length());
        outputPwd.setSelection(outputPwd.getText().length());
        outputPwdOk.setSelection(outputPwdOk.getText().length());
        setLpTtitle("注册");
    }

    @Override
    protected void initData() {
        handler = new MyHandler();
        onBackImagButton();
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if (time>0){
                        time--;
                        if (huoquCode!=null&&!huoquCode.equals("")){
                            huoquCode.setClickable(false);
                            huoquCode.setText("重新获取"+"("+String.valueOf(time)+")");
                        }
                    }else{
                        if (huoquCode!=null&&!huoquCode.equals("")){
                            time = 60;
                            huoquCode.setClickable(true);
                            huoquCode.setText("获取验证码");
                        }
                    }
                break;
                default:
                    break;
            }
        }
    }


    @OnClick({R.id.huoqu_code, R.id.go_register, R.id.go_login})
    public void onViewClicked(View view) {
        phone = outputPhone.getText().toString().trim();
        code = outputBtainCode.getText().toString();
        pwd = outputPwd.getText().toString();
        pwdOk = outputPwdOk.getText().toString();
        switch (view.getId()) {
            case R.id.huoqu_code:       //获取验证码
                if (GsonUtil.isEmpty(phone)){
                    Toast.makeText(activity,"请输入手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isNetworkAvailable(activity)){
                    Toast.makeText(activity,"网络异常～",Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestUtil.getInstance().httpObtaionCode(phone, new OkHttp.DataCallBack() {
                        @Override
                        public void requestFailure(Request request, IOException e) {
                        }
                        @Override
                        public void requestSuccess(String result) throws Exception {
                            Log.d("RegistActivity====",result);
                            JSONObject object = new JSONObject(result);
                            if(object.getString("error").equals("0")){
                                netcode=object.getString("code");
                            }else {
                                Toast.makeText(activity,object.getString("msg"),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Timer timer = new Timer(true);
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                                msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    };
                    timer.schedule(timerTask,0,1000);
                    huoquCode.setClickable(false);
                break;
            case R.id.go_register:     //去注册
                if (!isEdit(phone,code,pwd,pwdOk)) return;
                if (!Utils.sameStr(code,netcode)) return;
                if (!Utils.isNetworkAvailable(activity)){
                    Toast.makeText(activity,"网络异常～",Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestUtil.getInstance().httpRegister(phone, pwd, pwdOk, new OkHttp.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Log.d("RegistActivityRegist==",result);
                        JSONObject object = new JSONObject(result);
                        if (object.getString("error").equals("0")){
                            Toast.makeText(activity,"注册成功",Toast.LENGTH_SHORT).show();
                            finishActivity();
                        }else {
                            Toast.makeText(activity,"注册失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.go_login:   //去登陆
                DjhJumpUtil.getInstance().startBaseActivity(this,LoginActivity.class);
                break;
                default:
                    break;
        }
    }


    private boolean isEdit(String phone,String phoneCode,String pwd,String rePwd){
        boolean isfalse = true;
       if (GsonUtil.isEmpty(phone)){
            Toast.makeText(activity,"请输入手机号",Toast.LENGTH_SHORT).show();
            isfalse = false;
        }else if (!Utils.isMobileNO(phone)){
           Toast.makeText(activity,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
           isfalse = false;
       }else if (GsonUtil.isEmpty(phoneCode)){
            Toast.makeText(activity,"请填写验证码",Toast.LENGTH_SHORT).show();
            isfalse = false;
        }else if (Utils.hasChinese(phoneCode)){
           Toast.makeText(activity,"验证码不允许有中文",Toast.LENGTH_SHORT).show();
           isfalse = false;
       }else if (GsonUtil.isEmpty(pwd)){
            Toast.makeText(activity,"请输入密码",Toast.LENGTH_SHORT).show();
            isfalse = false;
        }else if (Utils.hasChinese(pwd)){
            Toast.makeText(activity,"密码不允许有中文",Toast.LENGTH_SHORT).show();
            isfalse = false;
       }else if (GsonUtil.isEmpty(rePwd)){
            Toast.makeText(activity,"请再次输入密码",Toast.LENGTH_SHORT).show();
            isfalse = false;
        }else if (Utils.hasChinese(pwdOk)){
           Toast.makeText(activity,"密码不允许有中文",Toast.LENGTH_SHORT).show();
           isfalse = false;
       }else if (!Utils.sameStr(pwd,pwdOk)){
            Toast.makeText(activity,"两次输入密码不一样",Toast.LENGTH_SHORT).show();
            isfalse = false;
       }
        return isfalse;
    }

    @Override
    protected void finishActivity() {
        View view = getWindow().peekDecorView();
        if (view!=null){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
        super.finishActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeMessages(1);
        }
    }
}
