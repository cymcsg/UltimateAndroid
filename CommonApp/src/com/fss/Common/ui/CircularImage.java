package com.fss.Common.ui;

/**
 * Created by cym on 14-3-28.
 */

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * ImageView圆形遮罩处理
 * background设置遮罩颜色
 * tag设置圆的边框色
 * padding设置圆形线宽
 * @author planet
 *
 */
public class CircularImage extends ImageView {
    public CircularImage(Context context) {
        super(context);
        initColor(null);
    }
    public CircularImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initColor(attrs);
    }
    public CircularImage(Context context, AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);
        initColor(attrs);
    }

    private Paint paint;
    private Path path;
    private boolean init = false;
    private int background = Color.TRANSPARENT;
    private int circleLineWidth = 6;
    private int circleColor = Color.BLACK;

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    private void initColor(AttributeSet attrs){
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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!init){
            initPaint();
        }
    }

    private void initPaint(){
        circleLineWidth = getPaddingLeft();
        setPadding(0, 0, 0, 0);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(background);
        //paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        //paint.setStrokeWidth(2);
        //paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        //Log.i(VIEW_LOG_TAG, "22>>>>>>>>>>>>>>>>>>>>3getPaddingBottom()="+getPaddingBottom());
        int width = getMeasuredWidth();
        float radius = width/(float)2;

        path = new Path();
        Log.i(VIEW_LOG_TAG, "moveto 0," + radius);
        path.moveTo(0, radius);
        Log.i(VIEW_LOG_TAG, "lineto 0,0");
        path.lineTo(0, 0);
        Log.i(VIEW_LOG_TAG, "lineto "+width+",0");
        path.lineTo(width, 0);
        Log.i(VIEW_LOG_TAG, "lineto "+width+","+width);
        path.lineTo(width, width);
        Log.i(VIEW_LOG_TAG, "lineto 0,"+width);
        path.lineTo(0, width);
        Log.i(VIEW_LOG_TAG, "lineto 0,"+radius);
        path.lineTo(0, radius);
        //圆弧左边中间起点是180,旋转360度
        //Log.i(VIEW_LOG_TAG, "arcto 0,0,"+width+","+width);
        path.arcTo(new RectF(0, 0, width, width), 180, -359, true);
        //path.addCircle(radius, radius, radius, Direction.CW);
        path.close();
        try {
            circleColor = Color.parseColor((String) getTag());
        } catch (Exception e) {
            e.printStackTrace();
        }
        init = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(background);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
        paint.setColor(circleColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(circleLineWidth);
        int width = getMeasuredHeight();
        canvas.drawCircle(width/2, width/2, (float) (width/2-circleLineWidth*.5), paint);
    }
}