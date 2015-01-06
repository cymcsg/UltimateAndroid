package com.marshalchen.common.commonUtils.uiUtils;

import android.graphics.*;
import android.view.View;

import java.io.*;

/**
 * Some effect of images
 */
public class ImageUtils {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 3;
    public static final int BOTTOM = 4;

    /**
     * Gray Image
     *
     * @param bmpOriginal
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }


    /**
     * toRoundCorner
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap
                .createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * Create watermark picture
     *
     * @param srcBitmap
     * @param watermark
     * @return
     */
    public static Bitmap createBitmapForWatermark(Bitmap srcBitmap, Bitmap watermark) {
        if (srcBitmap == null) {
            return null;
        }
        int w = srcBitmap.getWidth();
        int h = srcBitmap.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(srcBitmap, 0, 0, null);
        // draw watermark into
        cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);
        // store
        cv.restore();
        return newb;
    }

    /**
     * Picture compose.
     *
     * @param direction
     * @param bitmaps
     * @return
     */
    public static Bitmap potoMix(int direction, Bitmap... bitmaps) {
        if (bitmaps.length <= 0) {
            return null;
        }
        if (bitmaps.length == 1) {
            return bitmaps[0];
        }
        Bitmap newBitmap = bitmaps[0];
        for (int i = 1; i < bitmaps.length; i++) {
            newBitmap = createBitmapForFotoMix(newBitmap, bitmaps[i], direction);
        }
        return newBitmap;
    }


    private static Bitmap createBitmapForFotoMix(Bitmap first, Bitmap second, int direction) {
        if (first == null) {
            return null;
        }
        if (second == null) {
            return first;
        }
        int fw = first.getWidth();
        int fh = first.getHeight();
        int sw = second.getWidth();
        int sh = second.getHeight();
        Bitmap newBitmap = null;
        if (direction == LEFT) {
            newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(first, sw, 0, null);
            canvas.drawBitmap(second, 0, 0, null);
        } else if (direction == RIGHT) {
            newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(first, 0, 0, null);
            canvas.drawBitmap(second, fw, 0, null);
        } else if (direction == TOP) {
            newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(first, 0, sh, null);
            canvas.drawBitmap(second, 0, 0, null);
        } else if (direction == BOTTOM) {
            newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(first, 0, 0, null);
            canvas.drawBitmap(second, 0, fh, null);
        }
        return newBitmap;
    }

    /**
     * Zoom bitmap using Matrix
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, float width, float height) {

        int w = bitmap.getWidth();

        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();

        float scaleWidth = ((float) width / w);

        float scaleHeight = ((float) height / h);

        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

        return newbmp;

    }

    /**
     * Save bitmap to a .png file
     *
     * @param bitmap
     * @param name
     */
    public static void saveBitmapToPNG(Bitmap bitmap, String name) throws FileNotFoundException, IOException {
        File file = new File(name);
        FileOutputStream out = new FileOutputStream(file);
        if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
            out.flush();
            out.close();
        }
    }

    /**
     * Save bitmap to a .jpeg file
     *
     * @param bitmap
     * @param path
     */
    public static void saveJPGE_After(Bitmap bitmap, String path) throws FileNotFoundException, IOException {
        File file = new File(path);
        FileOutputStream out = new FileOutputStream(file);
        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
            out.flush();
            out.close();
        }
    }

    /**
     * Revert image file to a square with custom size
     *
     * @param path
     * @param width
     * @return
     * @throws IOException
     */
    public static Bitmap revertImageSize(String path, int width) throws IOException {

        return revertImageSize(path, width, width);
    }

    /**
     * Revert image file to a square with width and height equal 1000
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Bitmap revertImageSize(String path) throws IOException {

        return revertImageSize(path, 1000, 1000);
    }

    /**
     * Revert image file to custom size
     *
     * @param path
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static Bitmap revertImageSize(String path, int width, int height) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= width)
                    && (options.outHeight >> i <= height)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * Convert a View to Bitmap using view.getDrawingCache()
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * Convert a View to Bitmap using view.draw(new Canvas(bitmap))
     *
     * @param view
     * @param bitmapWidth
     * @param bitmapHeight
     * @return
     */
    public static Bitmap convertViewToBitmapWithCanvas(View view, int bitmapWidth, int bitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }
}
