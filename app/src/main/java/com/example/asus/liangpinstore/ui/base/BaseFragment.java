package com.example.asus.liangpinstore.ui.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.liangpinstore.utils.ActivityUtil;
import com.framework.lplibs.LpProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by asus on 2018/1/16.
 */

public abstract class BaseFragment extends Fragment implements View.OnTouchListener {

    private View rootview;
    private Unbinder unbinder;
    protected Bundle bundle;
    protected BaseActivity activity;
    private LpProgressDialog dialog;
    protected final int PROGRESS_BACK = -1;
    private SparseArray<View> mViews = new SparseArray<View>();
    private String order;
    private String code;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        activity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = initinitLayout(inflater);
        rootview.setOnTouchListener(this);
        unbinder = ButterKnife.bind(this, rootview);
        return rootview;
    }

    public void httpData(){

    }

    protected abstract View initinitLayout(LayoutInflater inflater);

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = rootview.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    /**
     * 显示Dialog
     */
    protected void showLpDialog() {
        showLpDialog("加载中");
    }
    protected void showLpDialog(Object title) {
        if (getActivity() == null || getActivity().isFinishing()){
            return;
        }
        if (dialog == null) {
            dialog = new LpProgressDialog(getActivity());
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                        //点击返回
                        dismissfxDialog(PROGRESS_BACK);
                        return true;
                    }
                    return false;
                }
            });
        }
        if (title != null) {
            if (title instanceof String) {
                dialog.setTextMsg((String) title);
            } else {
                dialog.setTextMsg((Integer) title);
            }
        }
        dialog.show();
    }



    /**取消弹出的Dialog
     * @param flag
     */
    protected void dismissfxDialog(int flag) {
        if (getActivity() == null || getActivity().isFinishing()){
            return;
        }
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
    protected void dismissfxDialog() {
        dismissfxDialog(0);
    }

    protected void finishActivity() {
        ActivityUtil.getInstance().finishThisActivity(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
