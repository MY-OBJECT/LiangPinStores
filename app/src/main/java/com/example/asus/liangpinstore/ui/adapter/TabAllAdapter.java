package com.example.asus.liangpinstore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.liangpinstore.R;

/**
 * Created by asus on 2018/1/20.
 */

public class TabAllAdapter extends RecyclerView.Adapter<TabAllAdapter.MyAllViewHolder>{
    private Context context;

    public TabAllAdapter(Context context) {
        this.context = context;
    }
    //定义一个接口
    public interface OnRecyclerViewItemListener {
        void onItemClick(View view, int position);
    }
    private TabRecBaseAdapter.OnRecyclerViewItemListener onItemListener = null;

    public void setOnItemClickListener(TabRecBaseAdapter.OnRecyclerViewItemListener itemListener) {
        this.onItemListener = itemListener;
    }
    @Override
    public TabAllAdapter.MyAllViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item2_layout,null,false);
        MyAllViewHolder viewHolder =new MyAllViewHolder(view);
        //设置监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemListener != null) {
                    onItemListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabAllAdapter.MyAllViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyAllViewHolder extends RecyclerView.ViewHolder {
        public MyAllViewHolder(View itemView) {
            super(itemView);
        }
    }
}
