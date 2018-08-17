package com.example.asus.liangpinstore.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.ui.adapter.TabPagerAdapter;
import com.example.asus.liangpinstore.ui.base.BaseFragment;
import com.example.asus.liangpinstore.ui.fragments.tabfragment.All_Store;
import com.example.asus.liangpinstore.ui.fragments.tabfragment.Recommend_Store;

import java.util.ArrayList;

import butterknife.BindView;
/**
 * Created by asus on 2018/1/16.
 */

public class StoreFr extends BaseFragment {


    @BindView(R.id.store_tabs)
    TabLayout storeTabs;
    @BindView(R.id.store_viewPager)
    ViewPager storeViewPager;
    private ArrayList<String> tab_title;
    private ArrayList<Fragment> tab_fr;
    private TabPagerAdapter adapter;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.storefr, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setTab();
    }

    private void setTab() {
        tab_title = new ArrayList<>();
        tab_title.add(getResources().getString(R.string.tab_title1_store));
        tab_title.add(getResources().getString(R.string.tab_title2_store));

        tab_fr = new ArrayList<>();
        tab_fr.add(new Recommend_Store());
        tab_fr.add(new All_Store());

        storeTabs.addTab(storeTabs.newTab().setText("推荐商店"));
        storeTabs.addTab(storeTabs.newTab().setText("全部商店"));

        adapter = new TabPagerAdapter(getActivity().getSupportFragmentManager(), tab_fr, tab_title);
        storeViewPager.setAdapter(adapter);
        storeTabs.setupWithViewPager(storeViewPager);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    public static float getPXfromDP(float value, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                context.getResources().getDisplayMetrics());
    }

}
