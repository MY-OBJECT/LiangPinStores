package com.framework.lplibs.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by z on 2016/1/18.
 */
public class JumpUtil {
    public void startBaseActivity(Context context, Class classs) {
        Intent intent = new Intent(context, classs);
        context.startActivity(intent);
    }

    public void startBaseActivityForResult(Activity activity, Class classs,
                                           Bundle extras, int requestCode) {
        Intent intent = new Intent(activity.getApplicationContext(), classs);
        if (extras != null) {
            intent.putExtras(extras);
        }
        activity.startActivityForResult(intent, requestCode);
    }


}
