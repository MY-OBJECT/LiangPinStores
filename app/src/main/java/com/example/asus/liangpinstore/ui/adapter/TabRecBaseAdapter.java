package com.example.asus.liangpinstore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.liangpinstore.R;

/**
 * Created by asus on 2018/1/19.
 */

public class TabRecBaseAdapter extends RecyclerView.Adapter<TabRecBaseAdapter.MyViewHolder> {

    private Context context;

    public TabRecBaseAdapter(Context context) {
        this.context = context;
    }

    //定义一个接口
    public interface OnRecyclerViewItemListener {
        void onItemClick(View view, int position);
    }
    private OnRecyclerViewItemListener onItemListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemListener itemListener) {
        this.onItemListener = itemListener;
    }
    @Override
    public TabRecBaseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item_layout,null,false);
        MyViewHolder holder = new MyViewHolder(view);
        //设置监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemListener != null) {
                    onItemListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(TabRecBaseAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
