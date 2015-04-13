/**
 * 
 */
package com.marshalchen.common.uimodule.simplemodule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.marshalchen.ultimateandroiduicomponent.R;


/**
 * The Class HoloCircularProgressBar.
 * 
 * @author Pascal.Welsch
 * @since 05.03.2013
 * 
 * @version 1.1 (12.10.2013)
 */
public class HoloCircularProgressBar extends View {

	/**
	 * The Constant TAG.
	 */
	private static final String TAG = HoloCircularProgressBar.class.getSimpleName();

	/**
	 * used to save the super state on configuration change
	 */
	private static final String INSTANCE_STATE_SAVEDSTATE = "saved_state";

	/**
	 * used to save the progress on configuration changes
	 */
	private static final String INSTANCE_STATE_PROGRESS = "progress";

	/**
	 * used to save the marker progress on configuration changes
	 */
	private static final String INSTANCE_STATE_MARKER_PROGRESS = "marker_progress";

	/**
	 * used to save the background color of the progress
	 */
	private static final String INSTANCE_STATE_PROGRESS_BACKGROUND_COLOR = "progress_background_color";

	/**
	 * used to save the color of the progress
	 */
	private static final String INSTANCE_STATE_PROGRESS_COLOR = "progress_color";

	/**
	 * true if not all properties are set. then the view isn't drawn and there
	 * are no errors in the LayoutEditor
	 */
	private boolean mIsInitializing = true;

	/**
	 * the paint for the background.
	 */
	private Paint mBackgroundColorPaint = new Paint();

	/**
	 * The stroke width used to paint the circle.
	 */
	private int mCircleStrokeWidth = 10;

	/**
	 * The pointer width (in pixels).
	 */
	private int mThumbRadius = 20;

	/**
	 * The rectangle enclosing the circle.
	 */
	private final RectF mCircleBounds = new RectF();

	/**
	 * Radius of the circle
	 * 
	 * <p>
	 * Note: (Re)calculated in {@link #onMeasure(int, int)}.
	 * </p>
	 */
	private float mRadius;

	/**
	 * the color of the progress.
	 */
	private int mProgressColor;

	/**
	 * paint for the progress.
	 */
	private Paint mProgressColorPaint;

	/**
	 * The color of the progress background.
	 */
	private int mProgressBackgroundColor;

	/**
	 * The current progress.
	 */
	private float mProgress = 0.3f;

	/**
	 * The Thumb color paint.
	 */
	private Paint mThumbColorPaint = new Paint();

	/**
	 * The Marker progress.
	 */
	private float mMarkerProgress = 0.0f;

	/**
	 * The Marker color paint.
	 */
	private Paint mMarkerColorPaint;

	/**
	 * flag if the marker should be visible
	 */
	private boolean mIsMarkerEnabled = false;

	/**
	 * The gravity of the view. Where should the Circle be drawn within the
	 * given bounds
	 * 
	 * {@link #computeInsets(int, int)}
	 */
	private final int mGravity;

	/**
	 * The Horizontal inset calcualted in {@link #computeInsets(int, int)}
	 * depends on {@link #mGravity}.
	 */
	private int mHorizontalInset = 0;

	/**
	 * The Vertical inset calcualted in {@link #computeInsets(int, int)} depends
	 * on {@link #mGravity}..
	 */
	private int mVerticalInset = 0;

	/**
	 * The Translation offset x which gives us the ability to use our own
	 * coordinates system.
	 */
	private float mTranslationOffsetX;

	/**
	 * The Translation offset y which gives us the ability to use our own
	 * coordinates system.
	 */
	private float mTranslationOffsetY;

	/**
	 * The Thumb pos x.
	 * 
	 * Care. the position is not the position of the rotated thumb. The position
	 * is only calculated in {@link #onMeasure(int, int)}
	 */
	private float mThumbPosX;

	/**
	 * The Thumb pos y.
	 * 
	 * Care. the position is not the position of the rotated thumb. The position
	 * is only calculated in {@link #onMeasure(int, int)}
	 */
	private float mThumbPosY;

	/**
	 * the overdraw is true if the progress is over 1.0.
	 * 
	 */
	private boolean mOverrdraw = false;

	/**
	 * the rect for the thumb square
	 */
	private final RectF mSquareRect = new RectF();

	/**
	 * indicates if the thumb is visible
	 */
	private boolean mIsThumbEnabled = true;

	/**
	 * Instantiates a new holo circular progress bar.
	 * 
	 * @param context
	 *            the context
	 */
	public HoloCircularProgressBar(final Context context) {
		this(context, null);
	}

	/**
	 * Instantiates a new holo circular progress bar.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public HoloCircularProgressBar(final Context context, final AttributeSet attrs) {
		this(context, attrs, R.attr.hcpCircularProgressBarStyle);
	}

	/**
	 * Instantiates a new holo circular progress bar.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public HoloCircularProgressBar(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);

		// load the styled attributes and set their properties
		final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.HoloCircularProgressBar,
				defStyle, 0);

		setProgressColor(attributes.getColor(R.styleable.HoloCircularProgressBar_hcp_progress_color, Color.CYAN));
		setProgressBackgroundColor(attributes.getColor(R.styleable.HoloCircularProgressBar_hcp_progress_background_color,
				Color.MAGENTA));
		setProgress(attributes.getFloat(R.styleable.HoloCircularProgressBar_hcp_hcp_progress, 0.0f));
		setMarkerProgress(attributes.getFloat(R.styleable.HoloCircularProgressBar_hcp_marker_progress, 0.0f));
		setWheelSize((int) attributes.getDimension(R.styleable.HoloCircularProgressBar_hcp_stroke_width, 10));
		mIsThumbEnabled = attributes.getBoolean(R.styleable.HoloCircularProgressBar_hcp_thumb_visible, true);
		mIsMarkerEnabled = attributes.getBoolean(R.styleable.HoloCircularProgressBar_hcp_marker_visible, true);

		mGravity = attributes.getInt(R.styleable.HoloCircularProgressBar_android_gravity, Gravity.CENTER);

		attributes.recycle();

		mThumbRadius = mCircleStrokeWidth * 2;

		updateBackgroundColor();

		updateMarkerColor();

		updateProgressColor();

		// the view has now all properties and can be drawn
		mIsInitializing = false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(final Canvas canvas) {

		// All of our positions are using our internal coordinate system.
		// Instead of translating
		// them we let Canvas do the work for us.
		canvas.translate(mTranslationOffsetX, mTranslationOffsetY);

		final float progressRotation = getCurrentRotation();

		// draw the background
		if (!mOverrdraw) {
			canvas.drawArc(mCircleBounds, 270, -(360 - progressRotation), false, mBackgroundColorPaint);
		}

		// draw the progress or a full circle if overdraw is true
		canvas.drawArc(mCircleBounds, 270, mOverrdraw ? 360 : progressRotation, false, mProgressColorPaint);

		// draw the marker at the correct rotated position
		if (mIsMarkerEnabled) {
			final float markerRotation = getMarkerRotation();

			canvas.save();
			canvas.rotate(markerRotation - 90);
			canvas.drawLine((float) (mThumbPosX + mThumbRadius / 2 * 1.4), mThumbPosY,
					(float) (mThumbPosX - mThumbRadius / 2 * 1.4), mThumbPosY, mMarkerColorPaint);
			canvas.restore();
		}

		if (isThumbEnabled()) {
			// draw the thumb square at the correct rotated position
			canvas.save();
			canvas.rotate(progressRotation - 90);
			// rotate the square by 45 degrees
			canvas.rotate(45, mThumbPosX, mThumbPosY);
			mSquareRect.left = mThumbPosX - mThumbRadius / 3;
			mSquareRect.right = mThumbPosX + mThumbRadius / 3;
			mSquareRect.top = mThumbPosY - mThumbRadius / 3;
			mSquareRect.bottom = mThumbPosY + mThumbRadius / 3;
			canvas.drawRect(mSquareRect, mThumbColorPaint);
			canvas.restore();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
		final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
		final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		final int min = Math.min(width, height);
		setMeasuredDimension(min, height);

		final float halfWidth = min * 0.5f;
		mRadius = halfWidth - mThumbRadius;

		mCircleBounds.set(-mRadius, -mRadius, mRadius, mRadius);

		mThumbPosX = (float) (mRadius * Math.cos(0));
		mThumbPosY = (float) (mRadius * Math.sin(0));
		computeInsets(width - min, height - min);

		mTranslationOffsetX = halfWidth + mHorizontalInset;
		mTranslationOffsetY = halfWidth + mVerticalInset;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onRestoreInstanceState(android.os.Parcelable)
	 */
	@Override
	protected void onRestoreInstanceState(final Parcelable state) {
		if (state instanceof Bundle) {
			final Bundle bundle = (Bundle) state;
			setProgress(bundle.getFloat(INSTANCE_STATE_PROGRESS));
			setMarkerProgress(bundle.getFloat(INSTANCE_STATE_MARKER_PROGRESS));

			final int progressColor = bundle.getInt(INSTANCE_STATE_PROGRESS_COLOR);
			if (progressColor != mProgressColor) {
				mProgressColor = progressColor;
				updateProgressColor();
			}

			final int progressBackgroundColor = bundle.getInt(INSTANCE_STATE_PROGRESS_BACKGROUND_COLOR);
			if (progressBackgroundColor != mProgressBackgroundColor) {
				mProgressBackgroundColor = progressBackgroundColor;
				updateBackgroundColor();
			}

			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE_SAVEDSTATE));
			return;
		}

		super.onRestoreInstanceState(state);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onSaveInstanceState()
	 */
	@Override
	protected Parcelable onSaveInstanceState() {
		final Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE_STATE_SAVEDSTATE, super.onSaveInstanceState());
		bundle.putFloat(INSTANCE_STATE_PROGRESS, mProgress);
		bundle.putFloat(INSTANCE_STATE_MARKER_PROGRESS, mMarkerProgress);
		bundle.putInt(INSTANCE_STATE_PROGRESS_COLOR, mProgressColor);
		bundle.putInt(INSTANCE_STATE_PROGRESS_BACKGROUND_COLOR, mProgressBackgroundColor);
		return bundle;
	}

	/**
	 * Compute insets.
	 * 
	 * <pre>
	 *  ______________________
	 * |_________dx/2_________|
	 * |......| /'''''\|......|
	 * |-dx/2-|| View ||-dx/2-|
	 * |______| \_____/|______|
	 * |________ dx/2_________|
	 * </pre>
	 * 
	 * @param dx
	 *            the dx the horizontal unfilled space
	 * @param dy
	 *            the dy the horizontal unfilled space
	 */
	@SuppressLint("NewApi")
	private void computeInsets(final int dx, final int dy) {
		final int layoutDirection;
		int absoluteGravity = mGravity;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			layoutDirection = getLayoutDirection();
			absoluteGravity = Gravity.getAbsoluteGravity(mGravity, layoutDirection);
		}

		switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
		case Gravity.LEFT:
			mHorizontalInset = 0;
			break;
		case Gravity.RIGHT:
			mHorizontalInset = dx;
			break;
		case Gravity.CENTER_HORIZONTAL:
		default:
			mHorizontalInset = dx / 2;
			break;
		}
		switch (absoluteGravity & Gravity.VERTICAL_GRAVITY_MASK) {
		case Gravity.TOP:
			mVerticalInset = 0;
			break;
		case Gravity.BOTTOM:
			mVerticalInset = dy;
			break;
		case Gravity.CENTER_VERTICAL:
		default:
			mVerticalInset = dy / 2;
			break;
		}
	}

	/**
	 * Gets the current rotation.
	 * 
	 * @return the current rotation
	 */
	private float getCurrentRotation() {
		return 360 * mProgress;
	}

	/**
	 * Gets the marker rotation.
	 * 
	 * @return the marker rotation
	 */
	private float getMarkerRotation() {

		return 360 * mMarkerProgress;
	}

	/**
	 * Sets the wheel size.
	 * 
	 * @param dimension
	 *            the new wheel size
	 */
	private void setWheelSize(final int dimension) {
		mCircleStrokeWidth = dimension;
	}

	/**
	 * updates the paint of the background
	 */
	private void updateBackgroundColor() {
		mBackgroundColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBackgroundColorPaint.setColor(mProgressBackgroundColor);
		mBackgroundColorPaint.setStyle(Paint.Style.STROKE);
		mBackgroundColorPaint.setStrokeWidth(mCircleStrokeWidth);

		invalidate();
	}

	/**
	 * updates the paint of the marker
	 */
	private void updateMarkerColor() {
		mMarkerColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mMarkerColorPaint.setColor(mProgressBackgroundColor);
		mMarkerColorPaint.setStyle(Paint.Style.STROKE);
		mMarkerColorPaint.setStrokeWidth(mCircleStrokeWidth / 2);

		invalidate();
	}

	/**
	 * updates the paint of the progress and the thumb to give them a new visual
	 * style
	 */
	private void updateProgressColor() {
		mProgressColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mProgressColorPaint.setColor(mProgressColor);
		mProgressColorPaint.setStyle(Paint.Style.STROKE);
		mProgressColorPaint.setStrokeWidth(mCircleStrokeWidth);

		mThumbColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mThumbColorPaint.setColor(mProgressColor);
		mThumbColorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mThumbColorPaint.setStrokeWidth(mCircleStrokeWidth);

		invalidate();
	}

	/**
	 * similar to {@link }
	 * 
	 * @return
	 */
	public float getMarkerProgress() {
		return mMarkerProgress;
	}

	/**
	 * gives the current progress of the ProgressBar. Value between 0..1 if you
	 * set the progress to >1 you'll get progress % 1 as return value
	 * 
	 * @return the progress
	 */
	public float getProgress() {
		return mProgress;
	}

	/**
	 * Gets the progress color.
	 * 
	 * @return the progress color
	 */
	public int getProgressColor() {
		return mProgressColor;
	}

	/**
	 * 
	 * @return true if the marker is visible
	 */
	public boolean isMarkerEnabled() {
		return mIsMarkerEnabled;
	}

	/**
	 * 
	 * @return true if the marker is visible
	 */
	public boolean isThumbEnabled() {
		return mIsThumbEnabled;
	}

	/**
	 * Sets the marker enabled.
	 * 
	 * @param enabled
	 *            the new marker enabled
	 */
	public void setMarkerEnabled(final boolean enabled) {
		mIsMarkerEnabled = enabled;
	}

	/**
	 * Sets the marker progress.
	 * 
	 * @param progress
	 *            the new marker progress
	 */
	public void setMarkerProgress(final float progress) {
		mIsMarkerEnabled = true;
		mMarkerProgress = progress;
	}

	/**
	 * Sets the progress.
	 * 
	 * @param progress
	 *            the new progress
	 */
	public void setProgress(final float progress) {
		if (progress == mProgress) {
			return;
		}

		if (progress == 1) {
			mOverrdraw = false;
			mProgress = 1;
		} else {

			if (progress >= 1) {
				mOverrdraw = true;
			} else {
				mOverrdraw = false;
			}

			mProgress = progress % 1.0f;
		}

		if (!mIsInitializing) {
			invalidate();
		}
	}

	/**
	 * Sets the progress background color.
	 * 
	 * @param color
	 *            the new progress background color
	 */
	public void setProgressBackgroundColor(final int color) {
		mProgressBackgroundColor = color;
        	
    	updateMarkerColor();
		updateBackgroundColor();
	}

	/**
	 * Sets the progress color.
	 * 
	 * @param color
	 *            the new progress color
	 */
	public void setProgressColor(final int color) {
		mProgressColor = color;

		updateProgressColor();
	}

	public void setThumbEnabled(final boolean enabled) {
		mIsThumbEnabled = enabled;
	}

}
