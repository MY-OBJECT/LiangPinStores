package com.example.asus.liangpinstore.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


/**
 * @author:mxy
 * @time:2017/09/11
 */

public class RoundImageView extends AppCompatImageView {

    private Paint paint;
    private Context context;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint();
        Drawable drawable = getDrawable();
        if (drawable!=null){
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            Bitmap roundBitmap = getRoundBitmap(bitmap, 5);
            Rect rectSrc = new Rect(0,0,roundBitmap.getWidth(),roundBitmap.getHeight());
            Rect rect = new Rect(0,0,getWidth(),getHeight());
            paint.reset();
            canvas.drawBitmap(roundBitmap,rectSrc,rect,paint);
        }
        else {
            super.onDraw(canvas);
        }
    }

    private Bitmap getRoundBitmap(Bitmap bitmap, int roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = bitmap.getWidth();

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;


    }
}
