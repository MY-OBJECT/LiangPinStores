package com.example.asus.liangpinstore.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.asus.liangpinstore.R;

/**
 * Created by asus on 2018/1/18.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    public GridViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler, null, false);
        return null;
    }
}
