package com.example.asus.liangpinstore.ui.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.ui.base.BaseActivity;
import com.example.asus.liangpinstore.utils.DjhJumpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity {

    @BindView(R.id.listview)
    ListView listview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setLpTtitle("添加地址");
        onRightBtn("完成");
        onBackImagButton();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.auto_add_address)
    public void onViewClicked() {
        DjhJumpUtil.getInstance().startBaseActivity(this,AddAddressActivity.class);
    }
}
