package com.example.asus.liangpinstore;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.asus.liangpinstore.runtimepermissions.PermissionsManager;
import com.example.asus.liangpinstore.runtimepermissions.PermissionsResultAction;
import com.example.asus.liangpinstore.ui.activity.ChatActivity;
import com.example.asus.liangpinstore.ui.base.BaseActivity;
import com.example.asus.liangpinstore.ui.fragments.ChatFr;
import com.example.asus.liangpinstore.ui.fragments.HomeFr;
import com.example.asus.liangpinstore.ui.fragments.MyCenterFr;
import com.example.asus.liangpinstore.ui.fragments.StoreFr;
import com.example.asus.liangpinstore.widgets.StatusBarCompat;
import com.hyphenate.EMClientListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rg)
    RadioGroup rg;
    private ArrayList<Fragment> fragments;
    private long END_TIME = 0;
    private ChatFr chatFr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        requestPermissions();
        viewData();
    }

    /**
     * 网络处理类
     */
    @Override
    protected void initData() {

    }

    private void viewData() {
        fragments = new ArrayList<>();
        chatFr = new ChatFr();
        addFrs();
        swichFragment(0);
        rg.setOnCheckedChangeListener(listener);
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        EMClient.getInstance().addClientListener(clientListener);
    }

    EMClientListener clientListener = new EMClientListener() {
        @Override
        public void onMigrate2x(boolean success) {
            Toast.makeText(MainActivity.this, "onUpgradeFrom 2.x to 3.x " + (success ? "success" : "fail"), Toast.LENGTH_LONG).show();
            if (success) {
                refreshUIWithMessage();
            }
        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                    // refresh conversation list
                    if (chatFr != null) {
//                        chatFr.refresh();
                    }
            }
        });
    }

    private void addFrs() {
        fragments.add(new HomeFr());
        fragments.add(chatFr);
        fragments.add(new StoreFr());
        fragments.add(new MyCenterFr());
    }

    protected RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb1:
                    swichFragment(0);
                    break;
                case R.id.rb2:
                    swichFragment(1);
                    break;
                case R.id.rb3:
                    swichFragment(2);
                    break;
                case R.id.rb4:
                    swichFragment(3);
                    break;
                default:
                    swichFragment(0);
                    break;
            }
        }
    };

    /**
     * 导航
     *
     * @param position
     */
    public void swichFragment(int position) {
        //添加事务管理
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        //遍历集合
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (i == position) {
                //显示fragment
                if (fragment.isAdded()) {
                    tran.show(fragment);
                } else {
                    //添加到事务所
                    tran.add(R.id.fl, fragment);
                }
            } else {
                //隐藏fragment
                if (fragment.isAdded()) {
                    tran.hide(fragment);
                }
            }
        }
        tran.commit();
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - END_TIME > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                END_TIME = secondTime;
                return true;
            } else {
                MainActivity.this.finish();
            }
        }

        return super.onKeyUp(keyCode, event);
    }
    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {}
        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
                        Toast.makeText(MainActivity.this, ChatActivity.activityInstance.getToChatUsername() + st10, Toast.LENGTH_LONG)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
        }
        @Override
        public void onContactInvited(String username, String reason) {}
        @Override
        public void onFriendRequestAccepted(String username) {}
        @Override
        public void onFriendRequestDeclined(String username) {}

    }
    @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
        }

}
