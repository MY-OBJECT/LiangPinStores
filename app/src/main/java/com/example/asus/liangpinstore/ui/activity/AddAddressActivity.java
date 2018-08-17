package com.example.asus.liangpinstore.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.ui.base.BaseActivity;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.auto_choose_city)
    AutoRelativeLayout autoChooseCity;
    @BindView(R.id.address_choose)
    TextView addressChoose;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setLpTtitle("添加新地址");
        onRightBtn("完成");
        onBackImagButton();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.auto_choose_city)
    public void onViewClicked() {

        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(autoChooseCity.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            selectAddress();//调用CityPicker选取区域

        }

    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(this)
                .textSize(16)
                .titleBackgroundColor("#ffffff")
                .confirTextColor("#1db0dd")
                .cancelTextColor("#1db0dd")
                .province("北京市")
                .city("北京市")
                .district("昌平区")
                .textColor(Color.parseColor("#111010"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                addressChoose.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }

}
