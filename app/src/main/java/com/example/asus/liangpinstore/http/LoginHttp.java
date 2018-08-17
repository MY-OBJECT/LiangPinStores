package com.example.asus.liangpinstore.http;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.liangpinstore.MainActivity;
import com.example.asus.liangpinstore.constants.MyConstants;
import com.example.asus.liangpinstore.ui.base.BaseActivity;
import com.example.asus.liangpinstore.utils.DjhJumpUtil;
import com.example.asus.liangpinstore.utils.SPUtils;
import com.framework.lplibs.http.ErrorCode;
import com.framework.lplibs.utils.OkHttp;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;

/**
 * 登陆封装类
 * Created by asus on 2018/2/1.
 */

public class LoginHttp {

    private OnLogin onLogin;
    private BaseActivity activity;

    public LoginHttp(OnLogin onLogin, BaseActivity activity) {
        this.onLogin = onLogin;
        this.activity = activity;
    }

    public void httpData(final String phone, String pwd){
        RequestUtil.getInstance().httpLogin(phone, pwd, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                onLogin.error();
                Toast.makeText(activity, ErrorCode.error(e),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("LoginActivity===",result);
                JSONObject object = new JSONObject(result);
                String code = object.getString("error");
                String user_id = object.getString("user_id");
                if (code.equals("0")){
                    Toast.makeText(activity,object.get("msg")+"",Toast.LENGTH_SHORT).show();
                    SPUtils.put(MyConstants.USER_INFO,activity,MyConstants.USER_INFO_ID,user_id);
                    SPUtils.put(MyConstants.USER_INFO,activity,MyConstants.USER_INFO_PHONE,phone);
                    SPUtils.put(MyConstants.USER_INFO,activity,MyConstants.ISLOGIN,true);
                    DjhJumpUtil.getInstance().startBaseActivity(activity, MainActivity.class);
                    onLogin.successful();
                }else {
                    onLogin.error();
                }
            }
        });
    }

    public void startActivity(){

    }

    public interface OnLogin{
        void error();
        void successful();
    }

}
