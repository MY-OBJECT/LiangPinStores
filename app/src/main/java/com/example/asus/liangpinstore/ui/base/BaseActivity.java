 package com.example.asus.liangpinstore.ui.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.utils.ActivityUtil;
import com.example.asus.liangpinstore.widgets.StatusBarCompat;
import com.framework.lplibs.LpProgressDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

 public abstract class BaseActivity extends AppCompatActivity {

     public Activity activity;
     private Toolbar toolbar;
     private View mDecorView;
     private TextView toolTitle;
     private LpProgressDialog dialog;
     protected final int PROGRESS_BACK = -1;
     private SparseArray<View> mViews = new SparseArray<View>();

     /**
      * 加载子布局
      * @return
      */
     protected abstract
     @LayoutRes
     int getLayoutId();

     /**
      * 加载布局View
      * @param savedInstanceState
      */
     protected abstract void initView(Bundle savedInstanceState);

     /**
      * 加载数据
      */
     protected abstract void initData();

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(getLayoutId());
         ActivityUtil.getInstance().addActivity(this);
         ButterKnife.bind(this);
         mDecorView = getWindow().getDecorView();
         activity = this;
         toolbar = getView(R.id.toolbar);
         setStatusBar(toolbar);
         toolTitle = getView(R.id.tool_title);
         if (toolbar != null) {
             toolbar.setTitle("");
             setSupportActionBar(toolbar);
             toolbar.setOnMenuItemClickListener(onMenuItemClick);
         }
         initView(savedInstanceState);
         initData();
     }
     public <T extends View> T getView(int viewId) {
         View view = mViews.get(viewId);
         if (view == null) {
             view = findViewById(viewId);
             mViews.put(viewId, view);
         }
         return (T) view;
     }
     public void showLpDialog(){
         showLpDialog("加载中");
     }

     /**
      * ProgressBar
      */
     public void showLpDialog(Object title){
         if (this.isFinishing()){
             return;
         }
         if (dialog == null){
             dialog = new LpProgressDialog(this);
             dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                 @Override
                 public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
                         //点击返回
                         dismissfxDialog(PROGRESS_BACK);
                            return true;
                     }
                     return false;
                 }
             });
         }
         if (title!=null){
             if (title instanceof String){
                 dialog.setTextMsg((String) title);
             }else {
                 dialog.setTextMsg((Integer) title);
             }
         }
         dialog.show();
     }

     protected void setLpTtitle(String title) {
         if (toolTitle != null)
             toolTitle.setText(title);
     }


     public void onRightBtn(String textId) {
         if (toolbar != null) {
             TextView tv = getView(R.id.tool_right);
             tv.setText(textId);
             tv.setVisibility(View.VISIBLE);
             tv.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     onRightBtnClick(v);
                 }
             });
         }
     }
        public void onRightBtnClick(View view){
        }

     protected void dismissfxDialog(int flag) {
         if (this.isFinishing()) {
             return;
         }
         if (dialog != null && dialog.isShowing()){
             dialog.dismiss();
         }
     }

     public void dismissfxDialog() {
         dismissfxDialog(0);
     }


     public void setStatusBar(Toolbar statusBar) {
            StatusBarCompat.compat(activity,R.color.blue);
     }
     //ToolBar MENU点击事件
     private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
         @Override
         public boolean onMenuItemClick(MenuItem menuItem) {
             setFxOnMenuItemClick(menuItem);
             return true;
         }
     };

     protected void setFxOnMenuItemClick(MenuItem menuItem) {

     }

     public void onBackImagButton(){
            if (toolbar!=null){
                TextView return_Btn = getView(R.id.tool_left);
                return_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
     }

     /**
      * 商品详情页设置返回按钮
      */
     public void onBackDetailsImg(){
         if (toolbar!=null){
             TextView return_btn = getView(R.id.tool_left);
             Drawable drawable = getResources().getDrawable(R.mipmap.details_return);
             drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
             return_btn.setCompoundDrawables(drawable,null,null,null);
             return_btn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     onBackPressed();
                 }
             });

             TextView shop = getView(R.id.tool_right);
             Drawable drawable1 = getResources().getDrawable(R.mipmap.details_shop);
             drawable1.setBounds(0,0,drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
             shop.setCompoundDrawables(null,null,drawable1,null);
             shop.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Toast.makeText(activity,"该功能暂未开启",Toast.LENGTH_SHORT).show();
                 }
             });
         }
     }

     public void onRightButton(){

     }

     @Override
     protected void onDestroy() {
         super.onDestroy();
     }

     @Override
     public void onBackPressed() {
         finishActivity();
     }

     protected void finishActivity() {
         ActivityUtil.getInstance().finishThisActivity(this);
     }

//     @Override
//     public void onWindowFocusChanged(boolean hasFocus) {
//         super.onWindowFocusChanged(hasFocus);
//         if (hasFocus) {
////                hideSystemUI();
//             mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                     |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                     |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//                     |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                     |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//         }
//     }


 }
