package com.example.asus.liangpinstore.ui.fragments.setfragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.constants.MyConstants;
import com.example.asus.liangpinstore.http.RequestUtil;
import com.example.asus.liangpinstore.ui.base.BaseFragment;
import com.example.asus.liangpinstore.utils.SPUtils;
import com.example.asus.liangpinstore.utils.Utils;
import com.framework.lplibs.json.GsonUtil;
import com.framework.lplibs.utils.OkHttp;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * Created by asus on 2018/1/25.
 */

public class FrSetPwd extends BaseFragment {
    @BindView(R.id.old_phone)
    EditText oldPhone;
    @BindView(R.id.obtain_code)
    Button obtainCode;
    @BindView(R.id.output_obtain_code)
    EditText outputObtainCode;
    @BindView(R.id.output_new_password)
    EditText outputNewPassword;
    @BindView(R.id.output_oknew_password)
    EditText outputOknewPassword;
    private String old_phone,user_id,code;
    private int time = 60;
    private MyHandler handler = new MyHandler();
    private String output_obtainCode;
    private String output_newPassword;
    private String output_okNewPas;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_setpwd, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user_id = (String) SPUtils.get(MyConstants.USER_INFO, activity, MyConstants.USER_INFO_ID, "");
    }

    @Override
    public void httpData() {
        super.httpData();
        output_obtainCode = outputObtainCode.getText().toString();
        output_newPassword = outputNewPassword.getText().toString();
        output_okNewPas = outputOknewPassword.getText().toString();
        if(isEdit(old_phone,output_obtainCode,output_newPassword,output_okNewPas)) return;
        if (!Utils.sameStr(code,output_obtainCode))return;
            else Toast.makeText(activity,"验证码不匹配！",Toast.LENGTH_SHORT).show();
        if (!Utils.sameStr(output_newPassword,output_okNewPas)) return;
            else Toast.makeText(activity,"两次输入密码不一样",Toast.LENGTH_SHORT).show();
        if (!Utils.isNetworkAvailable(getActivity())) {
            Toast.makeText(activity, "网络异常～", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestUtil.getInstance().httpSetPassword(user_id, output_newPassword, output_okNewPas, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject object = new JSONObject(result);
                if (object.getString("error").equals("0")){
                    Toast.makeText(activity,object.getString("msg"),Toast.LENGTH_SHORT).show();
                    SPUtils.remove(MyConstants.USER_INFO,activity,MyConstants.USER_INFO_PHONE);
                    SPUtils.remove(MyConstants.USER_INFO,activity,MyConstants.USER_INFO_ID);
                    finishActivity();
                }else {
                    Toast.makeText(activity,object.getString("msg"),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean isEdit(String phone,String phoneCode,String newPassword,String OknewPassword){
        boolean isfalse = false;
        if (GsonUtil.isEmpty(phone)){
            Toast.makeText(activity,"请输入手机号",Toast.LENGTH_SHORT).show();
            isfalse = true;
        }else if (!Utils.isMobileNO(phone)){
            Toast.makeText(activity,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
            isfalse = true;
        }else if (GsonUtil.isEmpty(phoneCode)){
            Toast.makeText(activity,"请填写验证码",Toast.LENGTH_SHORT).show();
            isfalse = true;
        }else if (Utils.hasChinese(phoneCode)){
            Toast.makeText(activity,"验证码不允许有中文",Toast.LENGTH_SHORT).show();
            isfalse = true;
        }else if (GsonUtil.isEmpty(newPassword)){
            Toast.makeText(activity,"请输入新的手机号",Toast.LENGTH_SHORT).show();
            isfalse = true;
        }else if (GsonUtil.isEmpty(OknewPassword)){
            Toast.makeText(activity,"请再次输入新的手机号",Toast.LENGTH_SHORT).show();
            isfalse = true;
        }
        return isfalse;
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if (time>0){
                        time--;
                        if (obtainCode!=null&&!obtainCode.equals("")){
                            obtainCode.setClickable(false);
                            obtainCode.setText("重新获取"+"("+String.valueOf(time)+")");
                        }else {
                            handler.removeCallbacksAndMessages(null);
                        }
                    }else{
                        if (obtainCode!=null&&!obtainCode.equals("")){
                            time = 60;
                            obtainCode.setText("重新获取");
                            obtainCode.setClickable(true);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick(R.id.obtain_code)
    public void onViewClicked() {
        old_phone = oldPhone.getText().toString();
        if (GsonUtil.isEmpty(old_phone)){
            Toast.makeText(activity,"请输入手机号",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Utils.isNetworkAvailable(activity)){
            Toast.makeText(activity,"网络异常～",Toast.LENGTH_SHORT).show();
            return;
        }
        obtainCode();
        RequestUtil.getInstance().httpObtainPhoneCode(old_phone, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(activity,"请求超时",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject object = new JSONObject(result);
                if (object.getString("error").equals("0")){
                    code = object.getString("code");
                    obtainCode();
                }else {
                    Toast.makeText(activity,object.getString("msg"),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void obtainCode() {
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
