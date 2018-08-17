package com.example.asus.liangpinstore.ui.fragments.tabfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.ui.adapter.TabRecBaseAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by asus on 2018/1/18.
 */

public class Recommend_Store extends Fragment {
    @BindView(R.id.recommend_recycler)
    RecyclerView recommendRecycler;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommend_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recommendRecycler.setNestedScrollingEnabled(false);
        recommendRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        TabRecBaseAdapter adapter = new TabRecBaseAdapter(getContext());
        recommendRecycler.setAdapter(adapter);
    }

}
