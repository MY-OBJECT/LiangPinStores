package com.example.asus.liangpinstore.http;


import com.example.asus.liangpinstore.BuildConfig;
import com.example.asus.liangpinstore.constants.MyConstants;
import com.example.asus.liangpinstore.utils.MD5Utils;
import com.example.asus.liangpinstore.utils.SPUtils;
import com.framework.lplibs.utils.OkHttp;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求处理类
 * Created by asus on 2018/1/30.
 */

public class RequestUtil {
    private static RequestUtil requestUtil;
    private String nowTime = System.currentTimeMillis()+"";
    public static RequestUtil getInstance(){
        if (requestUtil==null){
            synchronized (RequestUtil.class){
                if (requestUtil==null){
                    requestUtil = new RequestUtil();
                }
            }

        }
        return requestUtil;
    }

    /**
     * 获取手机验证码
     */
    public void httpObtaionCode(String phone,OkHttp.DataCallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("c","user");
        params.put("a","regist");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phoneNumber",phone);
        params.put("param",jsonObject.toString());
        params.put("timesnamp",nowTime);
        String md5file = MD5Utils.getCartInfo("user","regist",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);
    }


    /**
     * 注册账户
     */
    public void httpRegister(String phone,String password, String repassword, OkHttp.DataCallBack callBack){

        Map<String,String> params = new HashMap<>();
        params.put("c","user");
        params.put("a","setPwd");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phoneNumber",phone);
        jsonObject.addProperty("password",password);
        jsonObject.addProperty("repassword",repassword);
        params.put("param",jsonObject.toString());
        String md5file = MD5Utils.getCartInfo("user","setPwd",nowTime);
        params.put("timesnamp",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);

    }
    /**
     * 登陆账户
     */
    public void httpLogin(String phone, String pwd, OkHttp.DataCallBack callBack){

        Map<String,String> params = new HashMap<>();
        params.put("c","user");
        params.put("a","login");
        JsonObject object = new JsonObject();
        object.addProperty("phoneNumber",phone);
        object.addProperty("password",pwd);
        params.put("param",object.toString());
        String md5file = MD5Utils.getCartInfo("user","login",nowTime);
        params.put("timesnamp",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);
    }

    public void httpShouye(OkHttp.DataCallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("c","shops");
        params.put("a","indexInfo");
        JsonObject object = new JsonObject();
        object.addProperty("cate2_id","");
        object.addProperty("page",1);
        object.addProperty("num",10);
        params.put("param",object.toString());
        String md5file = MD5Utils.getCartInfo("shops","indexInfo",nowTime);
        params.put("timesnamp",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);
    }

    /**
     * 或取用戶信息
     */

    public void httpGetUserInfo(String phone,OkHttp.DataCallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("c","user");
        params.put("a","getUserInfo");
        JsonObject object = new JsonObject();
        object.addProperty("phone",phone);
        params.put("param",object.toString());
        String md5file = MD5Utils.getCartInfo("user","getUserInfo",nowTime);
        params.put("timesnamp",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);
    }


    /**
     * 商品詳情
     */
    public void httpGoodsDetails(String goods_id, OkHttp.DataCallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("c","product");
        params.put("a","getProductDetial");
        JsonObject object = new JsonObject();
        object.addProperty("good_id",goods_id);
        params.put("param",object.toString());
        String md5file = MD5Utils.getCartInfo("product","getProductDetial",nowTime);
        params.put("timesnamp",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);
    }

    /**
     * 更改签名
     */
    public void httpSetSignature(String user_id, String outSignature, OkHttp.DataCallBack callBack){

        Map<String,String> params = new HashMap<>();
        params.put("c","user");
        params.put("a","personSignat");
        JsonObject object = new JsonObject();
        object.addProperty("id",user_id);
        object.addProperty("signature",outSignature);
        params.put("param",object.toString());
        String md5file = MD5Utils.getCartInfo("user","personSignat",nowTime);
        params.put("timesnamp",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);

    }

    /**
     * 修改账户姓名
     * @param user_id
     * @param outName
     * @param callBack
     */
    public void httpSetName(String user_id, String outName, OkHttp.DataCallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("c","user");
        params.put("a","changeName");
        JsonObject object = new JsonObject();
        object.addProperty("id",user_id);
        object.addProperty("name",outName);
        params.put("param",object.toString());
        String md5file = MD5Utils.getCartInfo("user","changeName",nowTime);
        params.put("timesnamp",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);
    }

    /**
     * 修改性别
     * @param user_id
     * @param callBack
     */
    public void httpSetSex(String user_id, String outSex, OkHttp.DataCallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("c","user");
        params.put("a","changeSex");
        JsonObject object = new JsonObject();
        object.addProperty("id",user_id);
        object.addProperty("sex",outSex);
        params.put("param",object.toString());
        String md5file = MD5Utils.getCartInfo("user","changeSex",nowTime);
        params.put("timesnamp",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);
    }

    /**
     * 获取手机验证码
     * @param phoneNumber
     * @param callBack
     */
    public void httpObtainPhoneCode(String phoneNumber, OkHttp.DataCallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("c","user");
        params.put("a","getcode");
        JsonObject object = new JsonObject();
        object.addProperty("phoneNumber",phoneNumber);
        params.put("param",object.toString());
        String md5file = MD5Utils.getCartInfo("user","getcode",nowTime);
        params.put("timesnamp",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);
    }

    /**
     * 更换手机号
     * @param phoneNumber
     * @param callBack
     */
    public void httpSetPhone(String user_id,String phoneNumber, OkHttp.DataCallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("c","user");
        params.put("a","changePhone");
        JsonObject object = new JsonObject();
        object.addProperty("id",user_id);
        object.addProperty("phone",phoneNumber);
        params.put("param",object.toString());
        String md5file = MD5Utils.getCartInfo("user","changePhone",nowTime);
        params.put("timesnamp",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);
    }

    /**
     * 更换密码
     * @param user_id
     * @param password
     * @param rePassword
     * @param callBack
     */
    public void httpSetPassword(String user_id,String password,String rePassword,OkHttp.DataCallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("c","user");
        params.put("a","changePwd");
        JsonObject object = new JsonObject();
        object.addProperty("id",user_id);
        object.addProperty("password",password);
        object.addProperty("repassword",rePassword);
        params.put("param",object.toString());
        String md5file = MD5Utils.getCartInfo("user","changePwd",nowTime);
        params.put("timesnamp",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);
    }

    /**
     * 设置用户头像
     * @param user_id
     * @param img
     * @param callBack
     */
    public void httpSetImg(String user_id,String img, OkHttp.DataCallBack callBack){
        Map<String,String> params = new HashMap<>();
        params.put("c","user");
        params.put("a","changeAvatar");
        JsonObject object = new JsonObject();
        object.addProperty("id",user_id);
        object.addProperty("img",img);
        params.put("param",object.toString());
        String md5file = MD5Utils.getCartInfo("user","changeAvatar",nowTime);
        params.put("timesnamp",nowTime);
        params.put("openid","1");
        params.put("sign",md5file);
        OkHttp.postAsync(BuildConfig.httpUrl,params,callBack);
    }

}
