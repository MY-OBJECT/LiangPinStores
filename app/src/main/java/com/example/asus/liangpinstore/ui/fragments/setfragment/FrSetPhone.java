package com.example.asus.liangpinstore.ui.fragments.setfragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
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
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by asus on 2018/2/1.
 */

public class FrSetPhone extends BaseFragment {
    @BindView(R.id.old_phone)
    EditText oldPhone;
    @BindView(R.id.setPhone_huoqu_code)
    TextView setPhoneHuoquCode;
    @BindView(R.id.old_phone_code)
    EditText oldPhoneCode;
    @BindView(R.id.out_old_phone)
    EditText outOldPhone;
    @BindView(R.id.ok_out_old_phone)
    EditText okOutOldPhone;
    private String old_phone,oldPhone_code,out_oldPhone,okOut_oldPhone;
    private String code;
    private int time = 60;
    private MyHandler handler;
    private String user_id;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_setphone, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user_id = (String) SPUtils.get(MyConstants.USER_INFO, activity, MyConstants.USER_INFO_ID, "");
        handler = new MyHandler();
    }

    @Override
    public void httpData() {
        super.httpData();
        oldPhone_code = oldPhoneCode.getText().toString();
        out_oldPhone = outOldPhone.getText().toString();
        okOut_oldPhone = okOutOldPhone.getText().toString();
        if(isEdit(old_phone,oldPhone_code,out_oldPhone,okOut_oldPhone)) return;
        if (!Utils.sameStr(code,oldPhone_code)) return;
        else Toast.makeText(activity,"验证码不匹配！",Toast.LENGTH_SHORT).show();
        if (!Utils.sameStr(out_oldPhone,okOut_oldPhone)) return;
        else Toast.makeText(activity,"两次输入手机号不一样！",Toast.LENGTH_SHORT).show();
        if (!Utils.isNetworkAvailable(getActivity())) {
            Toast.makeText(activity, "网络异常～", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestUtil.getInstance().httpSetPhone(user_id, okOut_oldPhone, new OkHttp.DataCallBack() {
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
    private boolean isEdit(String phone,String phoneCode,String newPhone,String newPhone2){
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
        }else if (GsonUtil.isEmpty(newPhone)){
            Toast.makeText(activity,"请输入新的手机号",Toast.LENGTH_SHORT).show();
            isfalse = true;
        }else if (!Utils.isMobileNO(newPhone)){
            Toast.makeText(activity,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
            isfalse = true;
        }else if (GsonUtil.isEmpty(newPhone2)){
            Toast.makeText(activity,"请再次输入新的手机号",Toast.LENGTH_SHORT).show();
            isfalse = true;
        }else if (!Utils.isMobileNO(newPhone2)){
            Toast.makeText(activity,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
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
                        if (setPhoneHuoquCode!=null&&!setPhoneHuoquCode.equals("")){
                            setPhoneHuoquCode.setText("重新获取"+"("+String.valueOf(time)+")");
                        }else {
                            handler.removeCallbacksAndMessages(null);
                        }
                    }else{
                        if (setPhoneHuoquCode!=null&&!setPhoneHuoquCode.equals("")){
                            setPhoneHuoquCode.setText("重新获取");
                            setPhoneHuoquCode.setClickable(true);
                        }else {
                            handler.removeCallbacksAndMessages(null);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }


    @OnClick(R.id.setPhone_huoqu_code)
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

        RequestUtil.getInstance().httpObtainPhoneCode(old_phone, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject object = new JSONObject(result);
                if (object.getString("error").equals("0")){
                    code = object.getString("code");
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
        setPhoneHuoquCode.setClickable(false);
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
