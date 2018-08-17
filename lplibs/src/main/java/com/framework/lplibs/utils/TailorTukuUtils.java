package com.framework.lplibs.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;

/**
 * Created by asus on 2018/1/24.
 */

public class TailorTukuUtils {
    public static final int RESULT_REQUEST_CODE = 1000;

    public static void tailorTuku(Activity mActivity, Uri imgUriPath, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imgUriPath, "image/*");
        intent.putExtra("crop", "true");
        //裁剪框比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //图片输出大小
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        intent.putExtra("return_message-data", false);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(TailorTukuUtils.getPicFile()));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //不启用人脸识别
        intent.putExtra("noFaceDetection", false);
        mActivity.startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 得到图片的地址file
     */
    public static File getPicFile() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            //ToastUtils.showToastInUIThread(UIUtils.getContext(), "SD卡不可用");
            return null;
        }
        File fileDir = new File(Environment.getExternalStorageDirectory(), "storeImage");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File file = new File(fileDir, "head_icon.jpg");
        return file;
    }
}
