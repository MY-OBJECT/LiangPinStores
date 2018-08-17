package com.framework.lplibs.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by asus on 2018/1/24.
 */

public class OperarPictureUtils {

    /**
     * 保存图片到sd卡片
     */
    public static void saveBitmapToSdCard(Bitmap mBitmap) {
        FileOutputStream b = null;
        try {
            b = new FileOutputStream(TailorTukuUtils.getPicFile().getAbsolutePath());
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 30, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (b != null) {
                    b.flush();
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 保存图片到sd卡片
     */
    public static void saveBitmapToSdCard(Bitmap mBitmap, String filePath, String picName)  {
        FileOutputStream b = null;
        File file = new File(Environment.getExternalStorageDirectory(), filePath);
        if (!file.exists()) {
            file.mkdirs();// 创建文件夹
        }
        String fileName = file.getAbsolutePath() + File.separator + picName;

        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (b != null) {
                    b.flush();
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 先按宽高比例大小缩小，再压缩为指定大小以下
     * 针对的是File
     * @param srcPath  图片路径
     * @param targetWidth  目标宽度
     * @param targetHeight  目标高度
     * @param targetKbSize  多少kb以下
     * @return
     */
    public static Bitmap decodeAndCompress(String srcPath,float targetWidth, float targetHeight,int targetKbSize) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设为true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int startW= newOpts.outWidth;
        int startH = newOpts.outHeight;

        float targetH = targetHeight;//这里设置目标高度
        float targetW = targetWidth;//这里设置目标宽度
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int squareRate = 1;//be=1表示不缩放,squareRate只能是整数大于1的整数，如果是2表示四分之一
        if (startW > startH && startW > targetW) {//如果宽度大的话根据宽度固定大小缩放
            squareRate = (int) (newOpts.outWidth / targetW);
        } else if (startW < startH && startH > targetH) {//如果高度高的话根据宽度固定大小缩放
            squareRate = (int) (newOpts.outHeight / targetH);
        }
        if (squareRate <= 0){
            squareRate = 1;
        }
        newOpts.inSampleSize = squareRate;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);


        return compressQuality(bitmap,targetKbSize);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 先让图片缩放到多少内存以下，接着缩放，最后压缩
     * 针对的是bitmap
     * 此方法对比decodeAndCompress最大的好处就是针对特别大的图片，一开始就先把大小限制在指定范围以下
     * @param image  bitmap
     * @param maxSize  界限，多少kb以下
     * @param targetWidth  目标宽
     * @param targetHeight 目标高
     * @param targetKbSize 最终压缩到多少kb以下
     * @return
     */

    public static Bitmap compressLimitDecodeCompress(Bitmap image,int maxSize,float targetWidth, float targetHeight,int targetKbSize) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( (baos.toByteArray().length / 1024)>maxSize) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设为了true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int startW = newOpts.outWidth;
        int startH = newOpts.outHeight;

        float targetW = targetWidth;
        float targetH = targetHeight;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (startW > startH && startW > targetH) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / targetH);
        } else if (startW < startH && startH > targetW) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / targetW);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressQuality(bitmap,targetKbSize);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 压缩图片质量到指定大小以下
     * @param bitmap
     * @param targetKbSize
     * @return
     */
    private static Bitmap compressQuality(Bitmap bitmap, int targetKbSize) {
        // 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        // 将字节换成KB
        double mid = b.length / 1024;
        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (mid > targetKbSize) {
            // 获取bitmap大小 是允许最大大小的多少倍
            double i = mid / targetKbSize;
            // 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            bitmap = zoomImage(bitmap, bitmap.getWidth() / Math.sqrt(i),
                    bitmap.getHeight() / Math.sqrt(i));
        }
        return bitmap;

    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

}
