package com.example.asus.liangpinstore.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by asus on 2018/1/18.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tab_title;
    public TabPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments,ArrayList<String> tab_title) {
        super(fm);
        this.tab_title = tab_title;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tab_title.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_title.get(position%tab_title.size());
    }
}
