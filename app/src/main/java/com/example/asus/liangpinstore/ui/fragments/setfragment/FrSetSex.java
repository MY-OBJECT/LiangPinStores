package com.example.asus.liangpinstore.ui.fragments.setfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.example.asus.liangpinstore.R;
import com.example.asus.liangpinstore.constants.MyConstants;
import com.example.asus.liangpinstore.http.RequestUtil;
import com.example.asus.liangpinstore.ui.base.BaseFragment;
import com.example.asus.liangpinstore.utils.SPUtils;
import com.example.asus.liangpinstore.utils.Utils;
import com.framework.lplibs.utils.OkHttp;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * Created by asus on 2018/1/25.
 */

public class FrSetSex extends BaseFragment {

    @BindView(R.id.sex_nan)
    CheckedTextView sexNan;
    @BindView(R.id.sex_nv)
    CheckedTextView sexNv;
    public ArrayList<CheckedTextView> checkedTextViews = new ArrayList<>();
    private String user_id;
    private String sexCode;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_setsex, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user_id = (String) SPUtils.get(MyConstants.USER_INFO, activity, MyConstants.USER_INFO_ID, "");
        initCheck();
    }

    private void initCheck() {
        checkedTextViews.add(sexNan);
        checkedTextViews.add(sexNv);
    }

    @Override
    public void httpData() {
        super.httpData();
        if (!Utils.isNetworkAvailable(getActivity())){
            Toast.makeText(activity,"网络不稳定，请检查网络～",Toast.LENGTH_SHORT).show();
            return;
        }
        for (CheckedTextView textView:checkedTextViews){
            if (textView.isChecked()){
                String sex = textView.getText().toString();
                if (sex.equals("女")){
                    sexCode = "1";
                }else if (sex.equals("男")){
                    sexCode = "0";
                }
            }
        }
        RequestUtil.getInstance().httpSetSex(user_id, sexCode, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject object = new JSONObject(result);
                if (object.getString("error").equals("0")){
                    Bundle bundle = new Bundle();
                    bundle.putString("code","2");
                    bundle.putString("value",sexCode);
                    EventBus.getDefault().post(bundle);
                    Toast.makeText(activity,"修改成功",Toast.LENGTH_SHORT).show();
                    finishActivity();
                }

            }
        });

        getActivity().setResult(Activity.RESULT_OK);
        finishActivity();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.sex_nan, R.id.sex_nv})
    public void onViewClicked(CheckedTextView view) {
        for (CheckedTextView textView:checkedTextViews){
            if (textView.isChecked()){
                textView.setChecked(false);
            }
        }

        switch (view.getId()) {
            case R.id.sex_nan:
                view.setChecked(true);
                break;
            case R.id.sex_nv:
                view.setChecked(true);
                break;
                default:
                    break;
        }
    }
}
