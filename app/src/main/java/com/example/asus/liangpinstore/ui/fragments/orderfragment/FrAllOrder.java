package com.example.asus.liangpinstore.ui.fragments.orderfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.ui.adapter.CommonAdapter;
import com.example.asus.liangpinstore.ui.adapter.ViewHolder;
import com.example.asus.liangpinstore.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by asus on 2018/1/29.
 */

public class FrAllOrder extends BaseFragment {

    @BindView(R.id.listview)
    ListView listview;
    private String order;
    private String code;
    private CommonAdapter<String> commonAdapter;

    public FrAllOrder() {
    }

    public static final FrAllOrder newInstance(String order, String code) {
        FrAllOrder fragment = new FrAllOrder();
        Bundle bundle = new Bundle();
        bundle.putString("order", order);
        bundle.putString("code", code);
        fragment.setArguments(bundle);

        return fragment;
    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public FrAllOrder(String order, String code) {
        this.order = order;
        this.code = code;
    }

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frallorder_layout, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        listview.setAdapter(commonAdapter);

    }

    /**
     * 初始化控件
     */
    private void initView() {
        initAdapter();
    }

    private void initAdapter() {
        commonAdapter = new CommonAdapter<String>(activity,null, R.layout.list_item) {
            @Override
            public void convert(ViewHolder helper, String item, int position) {
                helper.setViewOnClick(R.id.head_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity,"点击商店",Toast.LENGTH_SHORT).show();
                    }
                });
                helper.setViewOnClick(R.id.image1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity,"点击按钮",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

    }

}
