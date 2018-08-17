package com.example.asus.liangpinstore.ui.fragments.setfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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

import okhttp3.Request;

/**
 * Created by asus on 2018/1/25.
 */

public class FrSetName extends BaseFragment {

    private EditText up_name;
    private String user_id;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_setname,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user_id = (String) SPUtils.get(MyConstants.USER_INFO, activity, MyConstants.USER_INFO_ID, "");
        up_name = getView(R.id.update_name);
        up_name.setSelection(up_name.getText().length());
    }

    @Override
    public void httpData() {
        super.httpData();

        if (!Utils.isNetworkAvailable(getActivity())){
            Toast.makeText(activity,"网络异常～",Toast.LENGTH_SHORT).show();
            return;
        }
        if (up_name.getText().toString()==null&&up_name.getText().toString()==""){
            Toast.makeText(getActivity(),"不能为空～",Toast.LENGTH_SHORT).show();
            return;
        }
        RequestUtil.getInstance().httpSetName(user_id, up_name.getText().toString(), new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject object = new JSONObject(result);
                if (object.getString("error").equals("0")){
                    Bundle bundle = new Bundle();
                    bundle.putString("code","1");
                    bundle.putString("value",up_name.getText().toString());
                    EventBus.getDefault().post(bundle);
                    Toast.makeText(activity,"修改成功",Toast.LENGTH_SHORT).show();
                    finishActivity();
                }

            }
        });

        getActivity().setResult(Activity.RESULT_OK);
        finishActivity();

    }
}
