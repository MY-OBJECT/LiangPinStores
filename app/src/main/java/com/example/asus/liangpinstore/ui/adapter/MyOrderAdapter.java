package com.example.asus.liangpinstore.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by asus on 2018/1/26.
 */

public class MyOrderAdapter extends FragmentPagerAdapter {
    private ArrayList<String> strings;
    private ArrayList<Fragment> fragments;

    public MyOrderAdapter(FragmentManager fm,ArrayList<String> strings,ArrayList<Fragment> fragments) {
        super(fm);
        this.strings=strings;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }
}
