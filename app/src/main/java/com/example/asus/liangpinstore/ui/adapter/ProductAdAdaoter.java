package com.example.asus.liangpinstore.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by asus on 2018/2/6.
 */

public class ProductAdAdaoter extends PagerAdapter {

    private Context context;
    private ArrayList<String> imgs;

    public ProductAdAdaoter(Context context, ArrayList<String> imgs) {
        this.context = context;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs==null ? 0 : imgs.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView adPicture = new ImageView(context);
        Glide.with(context).load(imgs.get(position)).into(adPicture);
        container.addView(adPicture);
        return adPicture;
    }

}
