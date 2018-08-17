package com.example.asus.liangpinstore.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.bean.BeUser;
import com.example.asus.liangpinstore.constants.MyConstants;
import com.example.asus.liangpinstore.ui.base.BaseActivity;
import com.example.asus.liangpinstore.utils.DjhJumpUtil;
import com.example.asus.liangpinstore.utils.SPUtils;
import com.example.asus.liangpinstore.view.RoundImageView;
import com.framework.lplibs.utils.OperarPictureUtils;
import com.framework.lplibs.utils.TailorTukuUtils;
import com.framework.lplibs.utils.UnitOpearUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
public class PersonalCenterSetActivity extends BaseActivity {

    @BindView(R.id.tool_title)
    TextView toolTitle;
    @BindView(R.id.user_icon)
    RoundImageView userIcon;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_ID)
    TextView userID;
    @BindView(R.id.user_sex)
    TextView userSex;
    @BindView(R.id.user_signature)
    TextView userSignature;
    @BindView(R.id.user_exit)
    TextView getUser_exit;
    private Dialog dialog;
    private int type = 0;//0是选择图片  1是保存图片  2拍照
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 1;//请求码，自己定义
    private static final int MY_PERMISSIONS_REQUEST_CALL_SYSTEMCAMERA = 2;//请求码，自己定义
    private boolean isClickSelectPicture = false;//是否点击了选择照相或者图库
    private BeUser.ResultBean.UserInfoBean user;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_center_set;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        user = SPUtils.getUser(MyConstants.USER_INFO, activity);
        toolTitle.setText("设置");
        onBackImagButton();
    }

    @Override
    protected void initData() {
        userName.setText(user.getUsername());
        userSignature.setText(user.getSignature());
        if (user.getSex().equals("0")){
            userSex.setText("男");
        }else if (user.getSex().equals("1")){
            userSex.setText("女");
        }
        userID.setText(user.getPhone());
    }


    //定义处理接收的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Bundle userEvent){
        if (userEvent.getString("code").equals("0")){
            userSignature.setText(userEvent.getString("value"));
        }else if (userEvent.getString("code").equals("1")){
            userName.setText(userEvent.getString("value"));
        }else if (userEvent.getString("code").equals("2")){
            if (userEvent.getString("value").equals("0")){
                userSex.setText("男");
            }else if (userEvent.getString("value").equals("1")){
                userSex.setText("女");
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.update_head_icon, R.id.update_user_name, R.id.update_user_ID, R.id.update_user_sex, R.id.update_user_signature, R.id.update_user_tel, R.id.update_user_pwd,R.id.user_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.update_head_icon://更新头像
                initDialog();
                break;
            case R.id.update_user_name://更新名字
                DjhJumpUtil.getInstance().startUserSetActivity(activity,MyConstants.user_edit_name);
                break;
            case R.id.update_user_sex://更新性别
                DjhJumpUtil.getInstance().startUserSetActivity(activity,MyConstants.user_edit_sex);
                break;
            case R.id.update_user_signature://更新签名
                DjhJumpUtil.getInstance().startUserSetActivity(activity,MyConstants.user_edit_signature);
                break;
            case R.id.update_user_tel://更换手机号
                DjhJumpUtil.getInstance().startUserSetActivity(activity,MyConstants.user_edit_tel_phone);
                break;
            case R.id.update_user_pwd://更改密码
                DjhJumpUtil.getInstance().startUserSetActivity(activity,MyConstants.user_edit_pwd);
                break;
            case R.id.user_exit://退出
                boolean islogin = (boolean)SPUtils.get(MyConstants.USER_INFO,activity,MyConstants.ISLOGIN,false);
                if (islogin==true){
                    SPUtils.remove(MyConstants.USER_INFO,activity,MyConstants.USER_INFO_PHONE);
                    SPUtils.remove(MyConstants.USER_INFO,activity,MyConstants.USER_INFO_ID);
                    DjhJumpUtil.getInstance().startBaseActivity(activity,LoginActivity.class);
                }else {
                    Toast.makeText(activity,"未登录",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void initDialog() {
        dialog = new Dialog(this, R.style.dialog);
        dialog.setCanceledOnTouchOutside(true);
        RelativeLayout root = (RelativeLayout) LayoutInflater.from(this).inflate(
                R.layout.dialog_layout, null);
        root.findViewById(R.id.cancle_dialog).setOnClickListener(listener);
        root.findViewById(R.id.go_phone).setOnClickListener(listener);
        root.findViewById(R.id.go_take_phone).setOnClickListener(listener);
        dialog.setContentView(root);

        Window mWindow = dialog.getWindow();
        mWindow.setGravity(Gravity.BOTTOM);//设置dialog显示的位置
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.width = getResources().getDisplayMetrics().widthPixels;//宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 5f; // 透明度
        mWindow.setAttributes(lp);
        dialog.show();
    }

    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cancle_dialog:
                    dialog.cancel();//取消
                    break;
                case R.id.go_take_phone:
                    //拍照权限
                    checkCameraPermission();
                    if (dialog!=null&&dialog.isShowing()){
                        dialog.cancel();
                    }
                    break;
                case R.id.go_phone:
                    type = 0;
                    //动态权限
                    checkSysPicturePermission();
                    if (dialog!=null&&dialog.isShowing()){
                        dialog.cancel();
                    }
                    break;
                    default:
                        break;
            }
        }
    };

    private void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //判断是否需要 向用户解释，为什么要申请该权限
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CALL_CAMERA);
            } else {
                type = 2;
                checkSysPicturePermission();
            }
        } else {
            takePhoto();
        }
    }


    //存储权限
    private void checkSysPicturePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_CALL_SYSTEMCAMERA);
            } else {
                if (type == 0) {
                    choosePhoto();
                } else if (type == 1) {
                    savePhoto();
                } else if (type == 2) {
                    takePhoto();
                }

            }
        } else {
            if (type == 0) {
                choosePhoto();
            } else if (type == 1) {
                savePhoto();
            } else if (type == 2) {
                takePhoto();
            }
        }
    }

    //拍照
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(TailorTukuUtils.getPicFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, MyConstants.IMGREQUESTCODE);
    }

    /**
     * 选择照片
     */
    private void choosePhoto() {
        Intent i = new Intent(Intent.ACTION_PICK, null);// 调用android的图库
        i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(i, MyConstants.SDIMGREQUESTCODE);
    }

    /**
     * 拍照保存照片
     */
    private void savePhoto() {
        // 判断存储卡是否可以用，可用进行存储
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Toast.makeText(this,"SD卡不可用",Toast.LENGTH_SHORT).show();
            return;
        }

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = 2;
        Bitmap mBitmap = BitmapFactory.decodeFile(TailorTukuUtils.getPicFile()
                .getAbsolutePath(), bitmapOptions);
        if (mBitmap != null) {
            userIcon.setImageBitmap(mBitmap);
            OperarPictureUtils.saveBitmapToSdCard(mBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_CAMERA){
            //grantResults授权结果
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //成功开启摄像头
                type = 2;
                checkSysPicturePermission();
            }else {
                //授权失败
                Toast.makeText(this,"请再设置-应用权限开启照相权限",Toast.LENGTH_SHORT).show();
            }
            return;
        }else if (requestCode == MY_PERMISSIONS_REQUEST_CALL_SYSTEMCAMERA){
            //授权结果
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //成功
                if (type==0){
                    choosePhoto();
                }else if (type==1){
                    savePhoto();
                }else if (type==2){
                    takePhoto();
                }
            }
            else{
                //授权失败
                Toast.makeText(this,"请再设置-应用权限中打开照相权限设置",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //照相
        if (resultCode == Activity.RESULT_OK && requestCode == MyConstants.IMGREQUESTCODE){
            type = 1;
            checkSysPicturePermission();
        }else if (resultCode == Activity.RESULT_OK && requestCode == MyConstants.SDIMGREQUESTCODE){
            //选择照片
            TailorTukuUtils.tailorTuku(this, data.getData(),
                    UnitOpearUtils.dip2px(this, 70), UnitOpearUtils.dip2px(this, 70));

        }else if (resultCode == Activity.RESULT_OK && requestCode == TailorTukuUtils.RESULT_REQUEST_CODE){
            //图库的裁剪
            Bitmap bitmap = BitmapFactory.decodeFile(Uri.fromFile(TailorTukuUtils.getPicFile())
                    .getPath());
            if (bitmap!=null){
                isClickSelectPicture = true;
                userIcon.setImageBitmap(bitmap);// 将图片显示在ImageView里
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
