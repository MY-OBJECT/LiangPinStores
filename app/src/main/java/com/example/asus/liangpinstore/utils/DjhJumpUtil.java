package com.example.asus.liangpinstore.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.asus.liangpinstore.constants.MyConstants;
import com.example.asus.liangpinstore.ui.activity.LoginActivity;
import com.example.asus.liangpinstore.ui.activity.UserSetActivity;
import com.framework.lplibs.utils.JumpUtil;


/**
 * Created by asus on 2018/1/23.
 */

public class DjhJumpUtil extends JumpUtil {

    private static DjhJumpUtil djhJumpUtil;
    public final int activtiy_UserSet = 5001;

    public DjhJumpUtil() {
    }

    public static DjhJumpUtil getInstance(){
        if (djhJumpUtil==null){
            synchronized (DjhJumpUtil.class){
                if (djhJumpUtil==null){
                    djhJumpUtil = new DjhJumpUtil();
                }
            }
        }
        return djhJumpUtil;
    }

    /**
     * 根据类型跳转fragment
     * @param activity
     * @param type
     */
    public void startUserSetActivity(Activity activity, int type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MyConstants.bundle_type, type);
        startBaseActivityForResult(activity, UserSetActivity.class, bundle, activtiy_UserSet);
    }

    /**
     * 带参跳转详情页
     * @param activity
     * @param nextActivity
     * @param key
     * @param value
     */
    public void startGoodDetail(Activity activity, Class nextActivity, String key, String value) {
        Intent intent = new Intent();
        intent.setClass(activity, nextActivity);
        intent.putExtra(key,value);
        activity.startActivity(intent);
    }

    /**
     * 判断是否有用户跳转
     * @param activity
     * @param nextActivity
     * @param user_id
     */
    public void setStartActivity(Activity activity,Class nextActivity,String user_id){
        Intent intent = new Intent();
        if (user_id != null && user_id != ""){
            intent.setClass(activity,nextActivity);
        }else {
            intent.setClass(activity, LoginActivity.class);
        }
        activity.startActivity(intent);

    }

    /**
     * 根据是否有用户ID跳转下一页或跳到登录页
     * @param activity
     * @param nextActivity
     * @param user_id
     * @param position
     */
    public void setStartActivityTwo(Activity activity,Class nextActivity,String user_id,int position){
        Intent intent = new Intent();
        if (user_id != null && user_id != ""){
            intent.putExtra("position",position);
            intent.setClass(activity,nextActivity);
        }else {
            intent.setClass(activity, LoginActivity.class);
        }
        activity.startActivity(intent);

    }

}
