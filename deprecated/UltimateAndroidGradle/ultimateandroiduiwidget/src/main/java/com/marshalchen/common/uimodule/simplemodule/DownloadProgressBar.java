package com.marshalchen.common.uimodule.simplemodule;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.marshalchen.common.uimodule.widgets.R;

public class DownloadProgressBar extends View {

    private static final String TAG = DownloadProgressBar.class.getSimpleName();
    private static final int DEFAULT_PROGRESS_DURATION = 1000;
    private static final int DEFAULT_RESULT_DURATION = 1000;
    private static final float DEFAULT_OVERSHOOT_VALUE = 2.5f;

    private Paint mCirclePaint;
    private Paint mDrawingPaint;
    private Paint mProgressPaint;
    private Paint mProgressBackgroundPaint;

    private float mRadius;
    private float mStrokeWidth;
    private float mLineWidth;
    private float mArrowLineToDotAnimatedValue;
    private float mArrowLineToHorizontalLineAnimatedValue;
    private float mDotToProgressAnimatedValue;
    private float mCurrentGlobalProgressValue;
    private float mSuccessValue;
    private float mExpandCollapseValue;
    private float mErrorValue;
    private float mOvershootValue;

    private float mCenterX;
    private float mCenterY;
    private float mPaddingX;
    private float mPaddingY;

    private int mCircleBackgroundColor;
    private int mDrawingColor;
    private int mProgressBackgroundColor;
    private int mProgressColor;
    private int mProgressDuration;
    private int mResultDuration;

    private AnimatorSet mArrowToLineAnimatorSet;
    private AnimatorSet mProgressAnimationSet;

    private OvershootInterpolator mOvershootInterpolator;

    private ValueAnimator mDotToProgressAnimation;
    private ValueAnimator mProgressAnimation;
    private ValueAnimator mSuccessAnimation;
    private ValueAnimator mExpandAnimation;
    private ValueAnimator mCollapseAnimation;
    private ValueAnimator mErrorAnimation;
    private ValueAnimator mArrowLineToDot;
    private ValueAnimator mArrowLineToHorizontalLine;
    private ValueAnimator mManualProgressAnimation;

    private RectF mCircleBounds;
    private RectF mProgressBackgroundBounds = new RectF();
    private RectF mProgressBounds = new RectF();

    private OnProgressUpdateListener mOnProgressUpdateListener;
    private AnimationSet mAbortAnimationSet;
    private AnimatorSet mManualProgressAnimationSet;
    private float mFromArc = 0;
    private float mToArc = 0;
    private float mCurrentGlobalManualProgressValue;

    private enum State {ANIMATING_LINE_TO_DOT, IDLE, ANIMATING_SUCCESS, ANIMATING_ERROR, ANIMATING_PROGRESS, ANIMATING_MANUAL_PROGRESS}

    private State mState;
    private State mResultState;
    private State mWhichProgress;

    public DownloadProgressBar(Context context) {
        super(context);
    }

    public DownloadProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DownloadProgressView, 0, 0);
        try {
            mRadius = array.getDimension(R.styleable.DownloadProgressView_dpv_circleRadius, 0);
            mStrokeWidth = array.getDimension(R.styleable.DownloadProgressView_dpv_strokeWidth, 0);
            mLineWidth = array.getDimension(R.styleable.DownloadProgressView_dpv_lineWidth, 0);
            mProgressDuration = array.getInteger(R.styleable.DownloadProgressView_dpv_progressDuration, DEFAULT_PROGRESS_DURATION);
            mResultDuration = array.getInteger(R.styleable.DownloadProgressView_dpv_resultDuration, DEFAULT_RESULT_DURATION);
            mProgressBackgroundColor = array.getColor(R.styleable.DownloadProgressView_dpv_progressBackgroundColor, 0);
            mDrawingColor = array.getColor(R.styleable.DownloadProgressView_dpv_drawingColor, 0);
            mProgressColor = array.getColor(R.styleable.DownloadProgressView_dpv_progressColor, 0);
            mCircleBackgroundColor = array.getColor(R.styleable.DownloadProgressView_dpv_circleBackgroundColor, 0);
            mOvershootValue = array.getFloat(R.styleable.DownloadProgressView_dpv_overshootValue, DEFAULT_OVERSHOOT_VALUE);
        } finally {
            array.recycle();
        }
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(mCircleBackgroundColor);
        mCirclePaint.setStrokeWidth(mStrokeWidth);

        mDrawingPaint = new Paint();
        mDrawingPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mDrawingPaint.setStyle(Paint.Style.STROKE);
        mDrawingPaint.setColor(mDrawingColor);
        mDrawingPaint.setStrokeWidth(mLineWidth);

        mProgressPaint = new Paint();
        mProgressPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStyle(Paint.Style.FILL);

        mProgressBackgroundPaint = new Paint();
        mProgressBackgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mProgressBackgroundPaint.setColor(mProgressBackgroundColor);
        mProgressBackgroundPaint.setStyle(Paint.Style.FILL);

        mState = State.IDLE;
        setupAnimations();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w / 2f;
        mCenterY = h / 2f;
        mPaddingX = w / 2f - mRadius;
        mPaddingY = h / 2f - mRadius;

        mCircleBounds = new RectF();
        mCircleBounds.top = mPaddingY;
        mCircleBounds.left = mPaddingX;
        mCircleBounds.bottom = h / 2f + mRadius;
        mCircleBounds.right = w / 2f + mRadius;
    }

    private void setupAnimations() {
        mOvershootInterpolator = new OvershootInterpolator(mOvershootValue);
        mArrowLineToDot = ValueAnimator.ofFloat(0, mRadius / 4);
        mArrowLineToDot.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mArrowLineToDotAnimatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mArrowLineToDot.setDuration(200);
        mArrowLineToDot.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mState = State.ANIMATING_LINE_TO_DOT;
                if (mOnProgressUpdateListener != null) {
                    mOnProgressUpdateListener.onAnimationStarted();
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mArrowLineToDot.setInterpolator(new AccelerateInterpolator());

        mArrowLineToHorizontalLine = ValueAnimator.ofFloat(0, mRadius / 2);
        mArrowLineToHorizontalLine.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mArrowLineToHorizontalLineAnimatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mArrowLineToHorizontalLine.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mArrowLineToHorizontalLine.setDuration(600);
        mArrowLineToHorizontalLine.setStartDelay(400);
        mArrowLineToHorizontalLine.setInterpolator(mOvershootInterpolator);

        mDotToProgressAnimation = ValueAnimator.ofFloat(0, mRadius);
        mDotToProgressAnimation.setDuration(600);
        mDotToProgressAnimation.setStartDelay(600);
        mDotToProgressAnimation.setInterpolator(mOvershootInterpolator);
        mDotToProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDotToProgressAnimatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mDotToProgressAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(mWhichProgress == State.ANIMATING_PROGRESS)
                    mProgressAnimationSet.start();
                else if(mWhichProgress == State.ANIMATING_MANUAL_PROGRESS)
                    mManualProgressAnimationSet.start();

                mState = mWhichProgress;

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mArrowToLineAnimatorSet = new AnimatorSet();
        mArrowToLineAnimatorSet.playTogether(mArrowLineToDot, mArrowLineToHorizontalLine, mDotToProgressAnimation);

        mProgressAnimation = ValueAnimator.ofFloat(0, 360f);
        mProgressAnimation.setStartDelay(500);
        mProgressAnimation.setInterpolator(new LinearInterpolator());
        mProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentGlobalProgressValue = (float) valueAnimator.getAnimatedValue();
                if (mOnProgressUpdateListener != null) {
                    mOnProgressUpdateListener.onProgressUpdate(mCurrentGlobalProgressValue);
                }
                invalidate();
            }
        });
        mProgressAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mDotToProgressAnimatedValue = 0;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mProgressAnimation.setDuration(mProgressDuration);

        mManualProgressAnimation = ValueAnimator.ofFloat(mFromArc, mToArc);
        mManualProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentGlobalManualProgressValue = (float)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mManualProgressAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if(mOnProgressUpdateListener != null) {
                    mOnProgressUpdateListener.onManualProgressStarted();
                }
                mDotToProgressAnimatedValue = 0;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(mOnProgressUpdateListener != null) {
                    mOnProgressUpdateListener.onManualProgressEnded();
                }
                if(mToArc > 359) {
                    mCollapseAnimation.start();
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });



        mExpandAnimation = ValueAnimator.ofFloat(0, mRadius / 6);
        mExpandAnimation.setDuration(300);
        mExpandAnimation.setInterpolator(new DecelerateInterpolator());
        mExpandAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mExpandCollapseValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        mCollapseAnimation = ValueAnimator.ofFloat(mRadius / 6, mStrokeWidth / 2);
        mCollapseAnimation.setDuration(300);
        mCollapseAnimation.setStartDelay(300);
        mCollapseAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(mState == State.ANIMATING_MANUAL_PROGRESS) {
                    if (mResultState == State.ANIMATING_ERROR) {
                        mErrorAnimation.start();
                    } else if (mResultState == State.ANIMATING_SUCCESS) {
                        mSuccessAnimation.start();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mCollapseAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mCollapseAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mExpandCollapseValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mManualProgressAnimationSet = new AnimatorSet();
        mManualProgressAnimationSet.playSequentially(mExpandAnimation, mManualProgressAnimation);

        mProgressAnimationSet = new AnimatorSet();
        mProgressAnimationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mResultState == State.ANIMATING_ERROR) {
                    mErrorAnimation.start();
                } else if (mResultState == State.ANIMATING_SUCCESS) {
                    mSuccessAnimation.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mProgressAnimationSet.playSequentially(mExpandAnimation, mProgressAnimation, mCollapseAnimation);

        mErrorAnimation = ValueAnimator.ofFloat(0, mRadius / 4);
        mErrorAnimation.setDuration(600);
        mErrorAnimation.setStartDelay(500);
        mErrorAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mErrorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mErrorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mErrorAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mState = State.ANIMATING_ERROR;
                if (mOnProgressUpdateListener != null) {
                    mOnProgressUpdateListener.onAnimationError();
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {

                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mOnProgressUpdateListener != null) {
                            mOnProgressUpdateListener.onAnimationEnded();
                        }
                        mState = State.IDLE;
                        resetValues();
                        invalidate();
                    }
                }, mResultDuration);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mSuccessAnimation = ValueAnimator.ofFloat(0, mRadius / 4);
        mSuccessAnimation.setDuration(600);
        mSuccessAnimation.setStartDelay(500);
        mSuccessAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mSuccessAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mSuccessValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mSuccessAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mState = State.ANIMATING_SUCCESS;
                if (mOnProgressUpdateListener != null) {
                    mOnProgressUpdateListener.onAnimationSuccess();
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mOnProgressUpdateListener != null) {
                            mOnProgressUpdateListener.onAnimationEnded();
                        }
                        mState = State.IDLE;
                        resetValues();
                        invalidate();
                    }
                }, mResultDuration);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    private void resetValues() {
        mArrowLineToDotAnimatedValue = 0;
        mArrowLineToHorizontalLineAnimatedValue = 0;
        mCurrentGlobalProgressValue = 0;
        mCurrentGlobalManualProgressValue = 0;
        mManualProgressAnimation.setFloatValues(0,0);
        mToArc = 0;
        mFromArc = 0;
    }

    private void drawing(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mCirclePaint);
        switch (mState) {
            case IDLE:
                canvas.drawLine(mCenterX, mCenterY - mRadius / 2, mCenterX, mCenterY + mRadius / 2, mDrawingPaint);
                canvas.drawLine(mCenterX - mRadius / 2, mCenterY, mCenterX, mCenterY + mRadius / 2, mDrawingPaint);
                canvas.drawLine(mCenterX, mCenterY + mRadius / 2, mCenterX + mRadius / 2, mCenterY, mDrawingPaint);
                break;
            case ANIMATING_LINE_TO_DOT:
                if (!mDotToProgressAnimation.isRunning()) {
                    canvas.drawLine(
                            mCenterX,
                            mCenterY - mRadius / 2 + mArrowLineToDotAnimatedValue * 2 - mStrokeWidth / 2,
                            mCenterX,
                            mCenterY + mRadius / 2 - mArrowLineToDotAnimatedValue * 2 + mStrokeWidth / 2,
                            mDrawingPaint
                    );
                }
                canvas.drawLine(
                        mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2,
                        mCenterY,
                        mCenterX,
                        mCenterY + mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue,
                        mDrawingPaint
                );
                canvas.drawLine(
                        mCenterX,
                        mCenterY + mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue,
                        mCenterX + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2,
                        mCenterY,
                        mDrawingPaint
                );
                break;
            case ANIMATING_PROGRESS:
                float progress = ((mCenterX + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2) - (mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2)) / 360f;

                mDrawingPaint.setStrokeWidth(mStrokeWidth);
                canvas.drawArc(mCircleBounds, -90, mCurrentGlobalProgressValue, false, mDrawingPaint);

                mProgressBackgroundBounds.left = mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2;
                mProgressBackgroundBounds.top = mCenterY - mExpandCollapseValue;
                mProgressBackgroundBounds.right =  mCenterX + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2;
                mProgressBackgroundBounds.bottom = mCenterY + mExpandCollapseValue;
                canvas.drawRoundRect(mProgressBackgroundBounds, 45, 45, mProgressBackgroundPaint);

                mProgressBounds.left = mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2;
                mProgressBounds.top = mCenterY - mExpandCollapseValue;
                mProgressBounds.right = mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2 + progress * mCurrentGlobalProgressValue;
                mProgressBounds.bottom = mCenterY + mExpandCollapseValue;
                canvas.drawRoundRect(mProgressBounds, 45, 45, mProgressPaint);
                break;
            case ANIMATING_MANUAL_PROGRESS:
                float manualProgress = ((mCenterX + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2) - (mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2)) / 360f;

                mDrawingPaint.setStrokeWidth(mStrokeWidth);
                canvas.drawArc(mCircleBounds, -90, mCurrentGlobalManualProgressValue, false, mDrawingPaint);

                mProgressBackgroundBounds.left = mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2;
                mProgressBackgroundBounds.top = mCenterY - mExpandCollapseValue;
                mProgressBackgroundBounds.right =  mCenterX + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2;
                mProgressBackgroundBounds.bottom = mCenterY + mExpandCollapseValue;
                canvas.drawRoundRect(mProgressBackgroundBounds, 45, 45, mProgressBackgroundPaint);

                mProgressBounds.left = mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2;
                mProgressBounds.top = mCenterY - mExpandCollapseValue;
                mProgressBounds.right = mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2 + manualProgress * mCurrentGlobalManualProgressValue;
                mProgressBounds.bottom = mCenterY + mExpandCollapseValue;
                canvas.drawRoundRect(mProgressBounds, 45, 45, mProgressPaint);
                break;
            case ANIMATING_SUCCESS:
                mDrawingPaint.setStrokeWidth(mLineWidth);
                canvas.drawArc(mCircleBounds, 0, 360, false, mDrawingPaint);
                canvas.drawLine(
                        mCenterX - mRadius / 2 + mSuccessValue * 2 - mSuccessValue / (float) Math.sqrt(2f) / 2,
                        mCenterY + mSuccessValue,
                        mCenterX + mSuccessValue * 2 - mSuccessValue / (float) Math.sqrt(2f) / 2,
                        mCenterY - mSuccessValue,
                        mDrawingPaint
                );
                canvas.drawLine(
                        mCenterX - mSuccessValue - 2 * mSuccessValue / (float) Math.sqrt(2f) / 2,
                        mCenterY,
                        mCenterX + mRadius / 2 - mSuccessValue * 2 - mSuccessValue / (float) Math.sqrt(2f) / 2,
                        mCenterY + mSuccessValue,
                        mDrawingPaint
                );
                break;
            case ANIMATING_ERROR:
                mDrawingPaint.setStrokeWidth(mLineWidth);
                canvas.drawArc(mCircleBounds, 0, 360, false, mDrawingPaint);

                canvas.drawLine(
                        mCenterX - mRadius / 2 - mRadius / 4 + mErrorValue * 2,
                        mCenterY + mErrorValue,
                        mCenterX + mErrorValue,
                        mCenterY - mErrorValue,
                        mDrawingPaint
                );
                canvas.drawLine(
                        mCenterX - mErrorValue,
                        mCenterY - mErrorValue,
                        mCenterX + mRadius / 2 + mRadius / 4 - mErrorValue * 2,
                        mCenterY + mErrorValue,
                        mDrawingPaint
                );
                break;
        }
        if (mDotToProgressAnimatedValue > 0) {
            canvas.drawCircle(
                    mCenterX,
                    mCenterY - mDotToProgressAnimatedValue,
                    mStrokeWidth / 2,
                    mDrawingPaint
            );
        }

        if (mDotToProgressAnimation.isRunning() && !mArrowLineToHorizontalLine.isRunning()) {
            canvas.drawLine(
                    mCenterX - mRadius / 2 - mArrowLineToHorizontalLineAnimatedValue / 2,
                    mCenterY,
                    mCenterX + mRadius / 2 + mArrowLineToHorizontalLineAnimatedValue / 2,
                    mCenterY,
                    mDrawingPaint
            );
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawing(canvas);
    }

    public void playToSuccess() {
        mResultState = State.ANIMATING_SUCCESS;
        mWhichProgress = State.ANIMATING_PROGRESS;
        mArrowToLineAnimatorSet.start();
        invalidate();
    }

    public void playToError() {
        mWhichProgress = State.ANIMATING_PROGRESS;
        mResultState = State.ANIMATING_ERROR;
        mArrowToLineAnimatorSet.start();
        invalidate();
    }

    public void playManualProgressAnimation() {
        mWhichProgress = State.ANIMATING_MANUAL_PROGRESS;
        mResultState = State.ANIMATING_SUCCESS;
        mArrowToLineAnimatorSet.start();
        invalidate();
    }

    public void abortDownload() {
        if(mExpandAnimation.isRunning() || mProgressAnimation.isRunning()) {
            mProgressAnimationSet.cancel();
            mCollapseAnimation.start();
            invalidate();
        }
    }

    public void setErrorResultState() {
        if(mSuccessAnimation.isRunning() || mErrorAnimation.isRunning())
            return;
        mResultState = State.ANIMATING_ERROR;
    }

    public void setSuccessResultState() {
        if(mSuccessAnimation.isRunning() || mErrorAnimation.isRunning())
            return;
        mResultState = State.ANIMATING_SUCCESS;
    }

    public void setProgress(int value) {
        if(value < 1 || value > 100)
            return;
        mToArc = value * 3.6f;
        mManualProgressAnimation.setFloatValues(mFromArc, mToArc);
        mManualProgressAnimation.start();
        mFromArc = mToArc;
        invalidate();
    }

    public interface OnProgressUpdateListener {
        void onProgressUpdate(float currentPlayTime);

        void onAnimationStarted();

        void onAnimationEnded();

        void onAnimationSuccess();

        void onAnimationError();

        void onManualProgressStarted();

        void onManualProgressEnded();
    }

    public void setOnProgressUpdateListener(OnProgressUpdateListener listener) {
        mOnProgressUpdateListener = listener;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.mState = mState;
        savedState.mmCurrentPlayTime = getCurrentPlayTimeByState(mState);
        return savedState;
    }

    private long[] getCurrentPlayTimeByState(State mState) {
        long[] tab = new long[3];
        switch (mState) {
            case ANIMATING_LINE_TO_DOT:
                for (int i = 0; i < mArrowToLineAnimatorSet.getChildAnimations().size(); i++) {
                    tab[i] = ((ValueAnimator) mArrowToLineAnimatorSet.getChildAnimations().get(i)).getCurrentPlayTime();
                }
                mArrowToLineAnimatorSet.cancel();
                break;
            case ANIMATING_PROGRESS:
                for (int i = 0; i < mProgressAnimationSet.getChildAnimations().size(); i++) {
                    tab[i] = ((ValueAnimator) mProgressAnimationSet.getChildAnimations().get(i)).getCurrentPlayTime();
                }
                mProgressAnimationSet.cancel();
                break;
            case ANIMATING_ERROR:
                tab[0] = mErrorAnimation.getCurrentPlayTime();
                mErrorAnimation.cancel();
                break;
            case ANIMATING_SUCCESS:
                tab[0] = mSuccessAnimation.getCurrentPlayTime();
                mSuccessAnimation.cancel();
                break;
        }
        return tab;
    }

    private void setCurrentPlayTimeByStateAndPlay(long[] tab, State mState) {
        switch (mState) {
            case ANIMATING_LINE_TO_DOT:
                mArrowToLineAnimatorSet.start();
                for (int i = 0; i < mArrowToLineAnimatorSet.getChildAnimations().size(); i++) {
                    ((ValueAnimator) mArrowToLineAnimatorSet.getChildAnimations().get(i)).setCurrentPlayTime(tab[i]);
                }
                break;
            case ANIMATING_PROGRESS:
                mProgressAnimationSet.start();
                for (int i = 0; i < mProgressAnimationSet.getChildAnimations().size(); i++) {
                    ((ValueAnimator) mProgressAnimationSet.getChildAnimations().get(i)).setCurrentPlayTime(tab[i]);
                }
                break;
            case ANIMATING_ERROR:
                mErrorAnimation.start();
                mErrorAnimation.setCurrentPlayTime(tab[0]);
                break;
            case ANIMATING_SUCCESS:
                mSuccessAnimation.start();
                mSuccessAnimation.setCurrentPlayTime(tab[0]);
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            mState = savedState.mState;
            super.onRestoreInstanceState(savedState.getSuperState());
            if (mState != State.IDLE) {
                continueAnimation(mState, savedState.mmCurrentPlayTime);
            }
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    private void continueAnimation(State mState, long[] mmCurrentPlayTime) {
        setCurrentPlayTimeByStateAndPlay(mmCurrentPlayTime, mState);
    }

    static class SavedState extends BaseSavedState {

        private boolean isFlashing;
        private boolean isConfigurationChanged;
        private long[] mmCurrentPlayTime;
        private State mState;

        public SavedState(Parcel source) {
            super(source);
            isFlashing = source.readInt() == 1;
            isConfigurationChanged = source.readInt() == 1;
            mmCurrentPlayTime = source.createLongArray();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(isFlashing ? 1 : 0);
            dest.writeInt(isConfigurationChanged ? 1 : 0);
            dest.writeLongArray(mmCurrentPlayTime);

        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
