package com.example.asus.liangpinstore.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.bean.BeUser;
import com.example.asus.liangpinstore.constants.MyConstants;
import com.example.asus.liangpinstore.http.RequestUtil;
import com.example.asus.liangpinstore.ui.activity.AddressActivity;
import com.example.asus.liangpinstore.ui.activity.LoginActivity;
import com.example.asus.liangpinstore.ui.activity.MyOrderFormActivity;
import com.example.asus.liangpinstore.ui.activity.NotifyActivity;
import com.example.asus.liangpinstore.ui.activity.PersonalCenterSetActivity;
import com.example.asus.liangpinstore.ui.base.BaseFragment;
import com.example.asus.liangpinstore.utils.DjhJumpUtil;
import com.example.asus.liangpinstore.utils.SPUtils;
import com.example.asus.liangpinstore.utils.Utils;
import com.example.asus.liangpinstore.view.RoundImageView;
import com.framework.lplibs.json.ToObandList;
import com.framework.lplibs.utils.OkHttp;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by asus on 2018/1/16.
 */

public class MyCenterFr extends BaseFragment {

    @BindView(R.id.text_go_login)
    TextView textGoLogin;
    @BindView(R.id.user_icon)
    RoundImageView userIcon;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.signature)
    TextView signature;
    @BindView(R.id.auto_user)
    AutoLinearLayout autoUser;
    private String user_phone;
    private String user_id;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.centerfr, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @OnClick({R.id.imageButton_set, R.id.my_dingDan, R.id.mine_wait_pay, R.id.mine_wait_fa, R.id.mine_wait_shou, R.id.mine_wait_ping, R.id.mine_wait_service, R.id.text_go_login,R.id.mine_shouHuo_address,R.id.mine_open_store,R.id.mine_notify,R.id.mine_suggment,R.id.mine_aboutMe})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageButton_set://个人中心
                Log.d("onViewClicked",user_id);
                DjhJumpUtil.getInstance().setStartActivity(getActivity(),PersonalCenterSetActivity.class,user_id);
                break;
            case R.id.my_dingDan://我的订单
                DjhJumpUtil.getInstance().setStartActivity(getActivity(), MyOrderFormActivity.class,user_id);
                break;
            case R.id.mine_wait_pay://代付款
                DjhJumpUtil.getInstance().setStartActivityTwo(getActivity(),MyOrderFormActivity.class,user_id,1);
                break;
            case R.id.mine_wait_fa://代收款
                DjhJumpUtil.getInstance().setStartActivityTwo(getActivity(),MyOrderFormActivity.class,user_id,2);
                break;
            case R.id.mine_wait_shou://待收货
                DjhJumpUtil.getInstance().setStartActivityTwo(getActivity(),MyOrderFormActivity.class,user_id,3);
                break;
            case R.id.mine_wait_ping://待评价
                DjhJumpUtil.getInstance().setStartActivityTwo(getActivity(),MyOrderFormActivity.class,user_id,4);
                break;
            case R.id.mine_wait_service://退款/售后
                DjhJumpUtil.getInstance().setStartActivityTwo(getActivity(),MyOrderFormActivity.class,user_id,5);
                break;
            case R.id.text_go_login://无登陆去登陆
                DjhJumpUtil.getInstance().startBaseActivity(getActivity(), LoginActivity.class);
                break;
            case R.id.mine_shouHuo_address:
                DjhJumpUtil.getInstance().setStartActivity(getActivity(), AddressActivity.class,user_id);
                break;
            case R.id.mine_aboutMe:
                break;
            case R.id.mine_notify:
                DjhJumpUtil.getInstance().setStartActivity(getActivity(), NotifyActivity.class,user_id);
                break;
            case R.id.mine_open_store:
                break;
            case R.id.mine_suggment:
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        user_id = (String) SPUtils.get(MyConstants.USER_INFO,activity,MyConstants.USER_INFO_ID,"");
        user_phone = (String) SPUtils.get(MyConstants.USER_INFO, activity, MyConstants.USER_INFO_PHONE, "");
        Log.d("onResume",user_phone+"====");
        if (!Utils.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "请检查网络～", Toast.LENGTH_SHORT).show();
            return;
        }
        if (user_phone != null&& user_phone !="") {
            RequestUtil.getInstance().httpGetUserInfo(user_phone, new OkHttp.DataCallBack() {
                @Override
                public void requestFailure(Request request, IOException e) {

                }

                @Override
                public void requestSuccess(String result) throws Exception {
                    BeUser gsonObject = ToObandList.getGsonObject(result, BeUser.class);
                    BeUser.ResultBean result1 = gsonObject.getResult();
                    BeUser.ResultBean.UserInfoBean userInfo = result1.getUserInfo();
                    Log.d("MyCenter===",userInfo.toString());
                    if (userInfo!=null){
                        if (userInfo.getImg()!=null&&userInfo.getImg()!=""){
//                            Glide.with(activity).load(MyConstants.HTTPUSERIMGURL+userInfo.getImg()).into(userIcon);
                        }
                        SPUtils.setUser(MyConstants.USER_INFO,activity,userInfo);
                        userName.setText(userInfo.getUsername());
                        signature.setText(userInfo.getSignature());
                        autoUser.setVisibility(View.VISIBLE);
                        textGoLogin.setVisibility(View.INVISIBLE);
                    }

                }
            });
        }else {
            autoUser.setVisibility(View.GONE);
            textGoLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
