package com.example.asus.liangpinstore.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by asus on 2018/1/16.
 */

public class ChatFr extends BaseFragment {

    @BindView(R.id.tool_title)
    TextView toolTitle;
    @BindView(R.id.tool_left)
    TextView toolLeft;
    Unbinder unbinder;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.chatfr, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolTitle.setText("聊天");
        toolLeft.setVisibility(View.GONE);

    }


    @OnClick(R.id.friends)
    public void onViewClicked() {
        showLpDialog();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissfxDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissfxDialog();
    }

}
