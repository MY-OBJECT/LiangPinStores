package com.example.asus.liangpinstore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.bean.ListInfo;
import com.example.asus.liangpinstore.constants.MyConstants;

import java.util.List;

import butterknife.BindView;

/**
 * Created by asus on 2018/1/17.
 */

public class Center_recycler_gv extends RecyclerView.Adapter<Center_recycler_gv.MyHolder> {
    private Context context;
    private List<ListInfo.ResultBeanX.GoodsListBean.ResultBean> result2;

    public Center_recycler_gv(Context context, List<ListInfo.ResultBeanX.GoodsListBean.ResultBean> result2) {
        this.context = context;
        this.result2 = result2;
    }

    //定义一个接口
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view,int position);
    }
    private OnItemClickLitener onItemListener = null;

    public void setOnItemClickListener(OnItemClickLitener itemListener) {
        this.onItemListener = itemListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder viewHolder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler, null, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        Glide.with(context).load(MyConstants.HTTPGOODSIMGURL+result2.get(position).getImg()).into(holder.home_img);
        holder.home_name.setText(result2.get(position).getTitle());
        holder.home_prive.setText("￥"+result2.get(position).getPrice()+"/斤");
        holder.home_num.setText("已售"+result2.get(position).getTotal_sale_num()+"单");

        if (onItemListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemListener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemListener.onItemLongClick(holder.itemView,pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return result2 == null ? 0 : result2.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final ImageView home_img;
        private final TextView home_name;
        private final TextView home_num;
        private final TextView home_prive;

        public MyHolder(View itemView) {
            super(itemView);
            home_img = itemView.findViewById(R.id.home_good_img);
            home_name = itemView.findViewById(R.id.home_good_name);
            home_num = itemView.findViewById(R.id.home_good_num);
            home_prive = itemView.findViewById(R.id.home_good_price);
        }
    }
}
