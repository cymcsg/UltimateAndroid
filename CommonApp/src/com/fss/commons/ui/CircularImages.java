package com.fss.commons.ui;

/**
 * Created by cym on 14-3-28.
 */

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * ImageView圆形切割处理(感觉比较浪费内存,一般情况下使用CircleImageView足够了)
 * padding代表圆形边框线粗，background代表圆形边框的颜色
 * @author planet
 *
 */
public class CircularImages extends ImageView {
    public CircularImages(Context context) {
        super(context);
    }
    public CircularImages(Context context, AttributeSet attrs) {
        super(context, attrs);
        initColor(attrs);
    }
    public CircularImages(Context context, AttributeSet attrs,
                               int defStyle) {
        super(context, attrs, defStyle);
        initColor(attrs);
    }

    private int background = Color.WHITE;
    private Paint paint;
    private boolean set = false;
    private int padding = 0;

    private void initColor(final AttributeSet attrs){
        if(attrs != null){
            String v = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "background");
            if(v != null){
                if(v.startsWith("#")){
                    background = Color.parseColor(v);
                }else{
                    background = getResources().getColor(Integer.parseInt(v.replaceAll("@", "")));
                }
            }
        }
        setBackgroundResource(android.R.color.transparent);
        paint = new Paint();
        paint.setColor(background);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        padding = getPaddingLeft();
        setPadding(0, 0, 0, 0);
    }

    @Override
    public void setImageBitmap(final Bitmap bm) {
        post(new Runnable() {
            @Override
            public void run() {
                set = true;
                CircularImages.super.setImageBitmap(getCroppedBitmap(bm));
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!set){
            set = true;
            setImageBitmap(getCroppedBitmap(((BitmapDrawable) getDrawable()).getBitmap()));
        }
        super.onDraw(canvas);
        paint.setStrokeWidth(padding);
        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredWidth()/2, (float) (getMeasuredWidth()*.5-padding*.5), paint);
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        int width = getMeasuredWidth();
        //int height = getMeasuredHeight();
        Bitmap output = Bitmap.createBitmap(width,
                width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, width);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(width / 2, width / 2,
                width / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //bitmap = ImageTool.scale(width, bitmap);
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}