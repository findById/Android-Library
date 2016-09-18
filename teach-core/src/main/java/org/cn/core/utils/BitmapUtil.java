package org.cn.core.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by chenning on 2016/9/7.
 */
public class BitmapUtil {
    /**
     * @param source
     * @param min
     * @return
     */
    public static Bitmap createCircleBitmap(Bitmap source, int min) {
        if (min <= 0) {
            min = source.getWidth() > source.getHeight() ? source.getHeight() : source.getWidth();
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}
