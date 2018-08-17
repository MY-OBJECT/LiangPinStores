package com.example.asus.liangpinstore.ui.fragments.setfragment;

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

import butterknife.BindView;
import okhttp3.Request;

/**
 * Created by asus on 2018/1/25.
 */

public class FrSetSingnature extends BaseFragment {

    @BindView(R.id.set_signature)
    EditText setSignature;
    private String user_id;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_setsingnature, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user_id = (String) SPUtils.get(MyConstants.USER_INFO, activity, MyConstants.USER_INFO_ID, "");
    }

    @Override
    public void httpData() {
        super.httpData();
        if (!Utils.isNetworkAvailable(getActivity())) {
            Toast.makeText(activity, "网络不稳定，请检查网络～", Toast.LENGTH_SHORT).show();
            return;
        }
        if (setSignature.getText().toString()==null&&setSignature.getText().toString()==""){
            Toast.makeText(activity,"不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        RequestUtil.getInstance().httpSetSignature(user_id, setSignature.getText().toString(), new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject object = new JSONObject(result);
                if (object.getString("error").equals("0")){
                    Bundle bundle = new Bundle();
                    bundle.putString("code","0");
                    bundle.putString("value",setSignature.getText().toString());
                    EventBus.getDefault().post(bundle);
                    Toast.makeText(activity,"修改成功",Toast.LENGTH_SHORT).show();
                    finishActivity();
                }
            }
        });
    }

}
