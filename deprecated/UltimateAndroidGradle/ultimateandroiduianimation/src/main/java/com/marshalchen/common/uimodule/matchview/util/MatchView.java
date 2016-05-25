package com.marshalchen.common.uimodule.matchview.util;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;

import java.util.ArrayList;

/**
 * Description:MatchBaseTextView
 * User: Lj
 * Date: 2014-12-03
 * Time: 07:48
 * FIXME
 */
public class MatchView extends View {
    //
    public ArrayList<MatchItem> mItemList = new ArrayList<MatchItem>();

    private int mLineWidth;
    private float mScale = 1;
    private int mDropHeight;
    private float internalAnimationFactor = 0.7f;
    private int horizontalRandomness;

    private float mProgress = 0;

    private int mDrawZoneWidth = 0;
    private int mDrawZoneHeight = 0;
    private int mOffsetX = 0;
    private int mOffsetY = 0;
    private float mBarDarkAlpha = 0.4f;
    private float mFromAlpha = 1.0f;
    private float mToAlpha = 0.4f;

    private int mLoadingAniDuration = 1000;
    private int mLoadingAniSegDuration = 1000;
    private int mLoadingAniItemDuration = 400;

    private Transformation mTransformation = new Transformation();
    private boolean mIsInLoading = false;
    private AniController mAniController = new AniController();
    private int mTextColor = Color.WHITE;
    private Handler mHandler;
    private float progress = 0;
    private float mInTime = 0.8f;//划入时间(单位秒)
    private float mOutTime = 0.8f;//划出时间(单位秒)
    private boolean isBeginLight = true;//结束后是否播放动画（默认为是）
    private int mPaddingTop = 15;//滑动的高度
    private float mTextSize = 25f;

    /**
     * 加载状态 1、划入 2、划出
     */
    private int STATE = 0;

    private MatchInListener mMatchInListener;
    private MatchOutListener mMatchOutListener;

    public void setMatchInListener(MatchInListener mMatchInListener) {
        this.mMatchInListener = mMatchInListener;
    }

    public void setMatchOutListener(MatchOutListener mMatchOutListener) {
        this.mMatchOutListener = mMatchOutListener;
    }

    public MatchView(Context context) {

        super(context);
        initView();
    }

    public MatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Utils.init(getContext());
        mLineWidth = Utils.dp2px(1);
        mDropHeight = Utils.dp2px(40);
        horizontalRandomness = Utils.SCREEN_WIDTH_PIXELS / 2;

        setPadding(0, Utils.dp2px(mPaddingTop), 0, Utils.dp2px(mPaddingTop));

        mHandler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
                if (STATE == 1) {//划入
                    if (progress < 100) {
                        progress++;
                        setProgress((progress * 1f / (100)));
                        mHandler.sendEmptyMessageDelayed(0, (long) (mInTime * 10));
                    } else {
                        STATE = 2;
                        if (mMatchInListener != null) {
                            mMatchInListener.onFinish();
                        }
                    }
                } else if (STATE == 2) {//划出
                    if (mIsInLoading) {
                        lightFinish();
                    }
                    if (progress > 0) {
                        progress--;
                        setProgress((progress * 1f / (100)));
                        mHandler.sendEmptyMessageDelayed(0, (long) (mOutTime * 10));
                    } else {
                        progress = 0;
                        if (mMatchOutListener != null) {
                            mMatchOutListener.onFinish();
                        }
                        STATE = 1;
                    }
                }
            }
        };
    }

    /**
     * 设置划入动画时长
     *
     * @param mTime (单位秒)
     */
    public void setInTime(float mTime) {
        mInTime = mTime;
    }

    /**
     * 设置划出动画时长
     *
     * @param mTime (单位秒)
     */
    public void setOutTime(float mTime) {
        mOutTime = mTime;
    }

    /**
     * 划入后是否开始闪光
     *
     * @param isLight
     */
    public void setLight(boolean isLight) {
        isBeginLight = isLight;
    }

    /**
     * 设置划入动画的高度
     *
     * @param dp
     */
    public void setPaddingTop(int dp) {
        mPaddingTop = dp;
    }

    /**
     * 设置字体大小
     *
     * @param mTextSize
     */
    public void setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    protected void show() {
        if (mItemList.size() == 0) {
            return;
        }
        STATE = 1;
        mHandler.sendEmptyMessage(0);
        if (mMatchInListener != null) {
            mMatchInListener.onBegin();
        }
    }

    public void hide() {
        if (mMatchOutListener != null) {
            mMatchOutListener.onBegin();
        }
        mHandler.sendEmptyMessage(0);
    }

    public void setProgress(float progress) {
        if (mMatchInListener != null && STATE == 1) {
            mMatchInListener.onProgressUpdate(progress);
        } else if (mMatchOutListener != null && STATE == 2) {
            mMatchOutListener.onProgressUpdate(progress);
        }

        if (progress == 1) {
            if (isBeginLight) {
                beginLight();
            }
        } else if (mIsInLoading) {
            lightFinish();
        }
        mProgress = progress;
        postInvalidate();
    }

    public int getLoadingAniDuration() {
        return mLoadingAniDuration;
    }

    public void setLoadingAniDuration(int duration) {
        mLoadingAniDuration = duration;
        mLoadingAniSegDuration = duration;
    }

    public MatchView setLineWidth(int width) {
        mLineWidth = width;
        for (int i = 0; i < mItemList.size(); i++) {
            mItemList.get(i).setLineWidth(width);
        }
        return this;
    }

    public MatchView setTextColor(int color) {
        mTextColor = color;
        for (int i = 0; i < mItemList.size(); i++) {
            mItemList.get(i).setColor(color);
        }
        return this;
    }

    public MatchView setDropHeight(int height) {
        mDropHeight = height;
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getTopOffset() + mDrawZoneHeight + getBottomOffset();
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mOffsetX = (getMeasuredWidth() - mDrawZoneWidth) / 2;
        mOffsetY = getTopOffset();
        mDropHeight = getTopOffset();
    }

    private int getTopOffset() {
        return getPaddingTop() + Utils.dp2px(10);
    }

    private int getBottomOffset() {
        return getPaddingBottom() + Utils.dp2px(10);
    }

    public void initWithString(String str) {
        initWithString(str, mTextSize);
    }

    public void initWithString(String str, float fontSize) {
        ArrayList<float[]> pointList = MatchPath.getPath(str, fontSize * 0.01f, 14);
        initWithPointList(pointList);
    }

    public void initWithStringArray(int id) {
        String[] points = getResources().getStringArray(id);
        ArrayList<float[]> pointList = new ArrayList<float[]>();
        for (int i = 0; i < points.length; i++) {
            String[] x = points[i].split(",");
            float[] f = new float[4];
            for (int j = 0; j < 4; j++) {
                f[j] = Float.parseFloat(x[j]);
            }
            pointList.add(f);
        }
        initWithPointList(pointList);
    }

    public float getScale() {
        return mScale;
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    public void initWithPointList(ArrayList<float[]> pointList) {

        float drawWidth = 0;
        float drawHeight = 0;
        boolean shouldLayout = mItemList.size() > 0;
        mItemList.clear();
        for (int i = 0; i < pointList.size(); i++) {
            float[] line = pointList.get(i);
            PointF startPoint = new PointF(Utils.dp2px(line[0]) * mScale, Utils.dp2px(line[1]) * mScale);
            PointF endPoint = new PointF(Utils.dp2px(line[2]) * mScale, Utils.dp2px(line[3]) * mScale);

            drawWidth = Math.max(drawWidth, startPoint.x);
            drawWidth = Math.max(drawWidth, endPoint.x);

            drawHeight = Math.max(drawHeight, startPoint.y);
            drawHeight = Math.max(drawHeight, endPoint.y);

            MatchItem item = new MatchItem(i, startPoint, endPoint, mTextColor, mLineWidth);
            item.resetPosition(horizontalRandomness);
            mItemList.add(item);
        }
        mDrawZoneWidth = (int) Math.ceil(drawWidth);
        mDrawZoneHeight = (int) Math.ceil(drawHeight);
        if (shouldLayout) {
            requestLayout();
        }
    }

    public void beginLight() {
        mIsInLoading = true;
        mAniController.start();
        invalidate();
    }

    public void lightFinish() {
        mIsInLoading = false;
        mAniController.stop();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float progress = mProgress;
        int c1 = canvas.save();
        int len = mItemList.size();
        for (int i = 0; i < mItemList.size(); i++) {
            canvas.save();
            MatchItem LoadingViewItem = mItemList.get(i);
            float offsetX = mOffsetX + LoadingViewItem.midPoint.x;
            float offsetY = mOffsetY + LoadingViewItem.midPoint.y;

            if (mIsInLoading) {
                LoadingViewItem.getTransformation(getDrawingTime(), mTransformation);
                canvas.translate(offsetX, offsetY);
            } else {

                if (progress == 0) {
                    LoadingViewItem.resetPosition(horizontalRandomness);
                    continue;
                }

                float startPadding = (1 - internalAnimationFactor) * i / len;
                float endPadding = 1 - internalAnimationFactor - startPadding;

                // done
                if (progress == 1 || progress >= 1 - endPadding) {
                    canvas.translate(offsetX, offsetY);
                    LoadingViewItem.setAlpha(mBarDarkAlpha);
                } else {
                    float realProgress;
                    if (progress <= startPadding) {
                        realProgress = 0;
                    } else {
                        realProgress = Math.min(1, (progress - startPadding) / internalAnimationFactor);
                    }
                    offsetX += LoadingViewItem.translationX * (1 - realProgress);
                    offsetY += -mDropHeight * (1 - realProgress);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(360 * realProgress);
                    matrix.postScale(realProgress, realProgress);
                    matrix.postTranslate(offsetX, offsetY);
                    LoadingViewItem.setAlpha(mBarDarkAlpha * realProgress);
                    canvas.concat(matrix);
                }
            }
            LoadingViewItem.draw(canvas);
            canvas.restore();
        }
        if (mIsInLoading) {
            invalidate();
        }
        canvas.restoreToCount(c1);
    }

    private class AniController implements Runnable {

        private int mTick = 0;
        private int mCountPerSeg = 0;
        private int mSegCount = 0;
        private int mInterval = 0;
        private boolean mRunning = true;

        private void start() {
            mRunning = true;
            mTick = 0;

            mInterval = mLoadingAniDuration / mItemList.size();
            mCountPerSeg = mLoadingAniSegDuration / mInterval;
            mSegCount = mItemList.size() / mCountPerSeg + 1;
            run();
        }

        @Override
        public void run() {

            int pos = mTick % mCountPerSeg;
            for (int i = 0; i < mSegCount; i++) {

                int index = i * mCountPerSeg + pos;
                if (index > mTick) {
                    continue;
                }

                index = index % mItemList.size();
                MatchItem item = mItemList.get(index);

                item.setFillAfter(false);
                item.setFillEnabled(true);
                item.setFillBefore(false);
                item.setDuration(mLoadingAniItemDuration);
                item.start(mFromAlpha, mToAlpha);
            }

            mTick++;
            if (mRunning) {
                postDelayed(this, mInterval);
            }
        }

        private void stop() {
            mRunning = false;
            removeCallbacks(this);
        }
    }

    public interface MatchInListener {
        public void onBegin();

        public void onProgressUpdate(float progress);

        public void onFinish();
    }

    public interface MatchOutListener {
        public void onBegin();

        public void onProgressUpdate(float progress);

        public void onFinish();
    }
}