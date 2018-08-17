package com.example.asus.liangpinstore.ui.fragments;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.bean.ListInfo;
import com.example.asus.liangpinstore.constants.MyConstants;
import com.example.asus.liangpinstore.http.RequestUtil;
import com.example.asus.liangpinstore.ui.activity.GoodsDetailsActivity;
import com.example.asus.liangpinstore.ui.activity.SearchActivity;
import com.example.asus.liangpinstore.ui.adapter.Center_recycler_gv;
import com.example.asus.liangpinstore.ui.adapter.CommonAdapter;
import com.example.asus.liangpinstore.ui.adapter.ViewHolder;
import com.example.asus.liangpinstore.ui.base.BaseFragment;
import com.example.asus.liangpinstore.utils.DjhJumpUtil;
import com.example.asus.liangpinstore.utils.Utils;
import com.framework.lplibs.json.ToObandList;
import com.framework.lplibs.utils.OkHttp;
import com.framework.lplibs.view.ReGridView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ezy.ui.view.BannerView;
import okhttp3.Request;

/**
 * Created by asus on 2018/1/16.
 */

public class HomeFr extends BaseFragment {

    @BindView(R.id.checkbox1)
    CheckedTextView checkbox1;
    @BindView(R.id.checkbox2)
    CheckedTextView checkbox2;
    @BindView(R.id.checkbox3)
    CheckedTextView checkbox3;
    @BindView(R.id.myScrollview)
    ObservableScrollView myScrollview;
    @BindView(R.id.banner1)
    BannerView banner1;
    @BindView(R.id.home_gridview)
    ReGridView homeGridview;
    Unbinder unbinder;
    private View view;
    private float headerHeight;
    private float minHeaderHeight;//顶部最低高度，即Bar的高度
    public ArrayList<CheckedTextView> checkedTextViews = new ArrayList<>();
    private final ArrayList<String> imgs = new ArrayList<>();
    private RelativeLayout relativeLayout;
    private Toolbar toolbar;
    private CommonAdapter commonAdapter;


    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.homefr, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initChecked();
        initData();
    }

    private void initData() {
        if (!Utils.isNetworkAvailable(getActivity())) {
            Toast.makeText(activity, "没网，请检查网络～", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestUtil.getInstance().httpShouye(new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("HomeFr===", result);
                ListInfo gsonObject = ToObandList.getGsonObject(result, ListInfo.class);
                ListInfo.ResultBeanX result1 = gsonObject.getResult();
                List<ListInfo.ResultBeanX.BannerBean> banner = result1.getBanner();
                for (int i = 0; i < banner.size(); i++) {
                    imgs.add(MyConstants.HTTPIAMURL + banner.get(i).getImg());
                    Log.e("HomeFr==========","imgs : " + imgs.toString());
                }
                imgs.addAll(imgs);
                banner1.setViewFactory(new BannerViewFactory());
                banner1.setDataList(imgs);
                banner1.start();
                //商品
                final List<ListInfo.ResultBeanX.GoodsListBean.ResultBean> result2 = result1.getGoodsList().getResult();
                commonAdapter = new CommonAdapter<ListInfo.ResultBeanX.GoodsListBean.ResultBean>(activity, result2, R.layout.item_recycler) {
                    @Override
                    public void convert(ViewHolder helper, ListInfo.ResultBeanX.GoodsListBean.ResultBean item, int position) {

                        helper.setImageByUrl(R.id.home_good_img, MyConstants.HTTPGOODSIMGURL + result2.get(position).getImg());
                        helper.setText(R.id.home_good_name, result2.get(position).getTitle());
                        helper.setText(R.id.home_good_price, "￥" + result2.get(position).getPrice() + "/斤");
                        helper.setText(R.id.home_good_num, "已售" + result2.get(position).getTotal_sale_num() + "单");

                    }
                };
                homeGridview.setAdapter(commonAdapter);
                homeGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DjhJumpUtil.getInstance().startGoodDetail(activity, GoodsDetailsActivity.class, MyConstants.GOOD_ID, result2.get(position).getId());
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class BannerViewFactory implements BannerView.ViewFactory {
        @Override
        public View create(Object item, int position, ViewGroup container) {
            ImageView iv = new ImageView(container.getContext());
            RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA);
            Glide.with(container.getContext().getApplicationContext()).load(item).apply(options).into(iv);
            return iv;
        }
    }

    private void initChecked() {
        checkedTextViews.add(checkbox1);
        checkedTextViews.add(checkbox2);
        checkedTextViews.add(checkbox3);
    }

    private void initView() {
        toolbar = getView(R.id.toolbar);
        myScrollview.setScrollViewCallbacks(callbacks);
        relativeLayout = getView(R.id.relative);
        relativeLayout.setOnClickListener(listener);
    }


    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = mSpace;
            }
        }

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }
    }

    private ObservableScrollViewCallbacks callbacks = new ObservableScrollViewCallbacks() {
        @Override
        public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
            float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
            float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);
            //Toolbar背景色透明度
            toolbar.setBackgroundColor(Color.argb((int) (offset * 255), 255, 255, 255));
        }

        @Override
        public void onDownMotionEvent() {

        }

        @Override
        public void onUpOrCancelMotionEvent(ScrollState scrollState) {
            if (scrollState == ScrollState.DOWN) {
                Log.d("onUpOrCancel", "向下滑动");
            } else if (scrollState == ScrollState.UP) {
                Log.d("onUpOrCancel", "向上滑动");
            } else if (scrollState == ScrollState.STOP) {
                Log.d("onUpOrCancel", "滑动停止");
            }
        }
    };


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DjhJumpUtil.getInstance().startBaseActivity(getActivity(), SearchActivity.class);
        }
    };


    @OnClick({R.id.checkbox1, R.id.checkbox2, R.id.checkbox3})
    public void onViewClicked(CheckedTextView view) {
        for (CheckedTextView checkedTextView : checkedTextViews) {
            if (checkedTextView.isChecked()) {
                checkedTextView.setChecked(false);
            }
        }
        switch (view.getId()) {
            case R.id.checkbox1:
                view.setChecked(true);
                break;
            case R.id.checkbox2:
                view.setChecked(true);
                break;
            case R.id.checkbox3:
                view.setChecked(true);
                break;
            default:
                break;
        }
    }

}
