package com.marshalchen.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import com.marshalchen.common.R;

public class ExpandedCircleProgressView extends View {

	private static final String TAG = "ExpandedCircleProgressView";

	private class Circle {
		float radius;
		float centerX;
		float centerY;

		Circle(float x, float y, float innerCircleSize) {
			this.radius = innerCircleSize;
			this.centerX = x;
			this.centerY = y;
		}
	}

	private Paint mProgressCirclePaint;
	private Circle mProgressCircle;

	private Paint mInnerCirclePaint;
	private Circle mInnerCircle;

	private Paint mTextPaint;
	private String mCurrentProgress = "0%";

	private Paint mOuterCirclePaint;
	private Circle mOuterCircle;

	private static int SLEEP_TIME = 8;
	private static float INCRESING_SIZE = 4;

	private ProgressUpdateTask mUpdateTask;

	private float mInnerCircleSize;
	private float mOuterCircleSize;

	private int mInnerCircleColor;
	private int mOuterCircleColor;
	private int mProgressCircleColor;
	private int mTextProgressColor;

	private float mOuterCircleLineWidth;

	private float mTextSize;

	private final static int MAX_PROGRESS = 100;

	public ExpandedCircleProgressView(final Context context) {
		super(context);
		init(context, null);
	}

	public ExpandedCircleProgressView(final Context context,
			final AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public ExpandedCircleProgressView(final Context context,
			final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public void init(final Context context, AttributeSet attrs) {

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.ExpandedCircleProgressView, 0, 0);
		try {
			// Retrieve the values from the TypedArray and store into
			// fields of this class.
			mInnerCircleSize = a.getDimension(
					R.styleable.ExpandedCircleProgressView_innerCircleSize,
					0.0f);

			mOuterCircleSize = a.getDimension(
					R.styleable.ExpandedCircleProgressView_outerCircleSize,
					0.0f);

			mInnerCircleColor = a.getColor(
					R.styleable.ExpandedCircleProgressView_innerCircleColor,
					0xff000000);
			mOuterCircleColor = a.getColor(
					R.styleable.ExpandedCircleProgressView_outerCircleColor,
					0xff000000);
			mProgressCircleColor = a.getColor(
					R.styleable.ExpandedCircleProgressView_progressCircleColor,
					0xff000000);

			mOuterCircleLineWidth = a
					.getDimension(
							R.styleable.ExpandedCircleProgressView_outerCircleLineWidth,
							0.5f);

			mTextSize = a.getDimension(
					R.styleable.ExpandedCircleProgressView_textProgressSize,
					0.0f);

			mTextProgressColor = a.getColor(
					R.styleable.ExpandedCircleProgressView_textProgressColor,
					0xff000000);
		} finally {
			// release the TypedArray so that it can be reused.
			a.recycle();
		}
		INCRESING_SIZE = (mOuterCircleSize - mInnerCircleSize) / 100;
		SLEEP_TIME = (int) (INCRESING_SIZE * 1500 / mOuterCircleSize);

		mProgressCirclePaint = new Paint();
		mProgressCirclePaint.setColor(mProgressCircleColor);
		mProgressCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mProgressCirclePaint.setStyle(Paint.Style.FILL);

		mInnerCirclePaint = new Paint();
		mInnerCirclePaint.setColor(mInnerCircleColor);
		mInnerCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mInnerCirclePaint.setStyle(Paint.Style.FILL);

		mOuterCirclePaint = new Paint();
		mOuterCirclePaint.setColor(mOuterCircleColor);
		mOuterCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mOuterCirclePaint.setStrokeWidth(mOuterCircleLineWidth);
		mOuterCirclePaint.setStyle(Paint.Style.STROKE);

		mTextPaint = new Paint();
		mTextPaint.setColor(mTextProgressColor);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		mTextPaint.setTextSize(mTextSize);

		cancelUpdateTask();
	}
	
	/**
	 * Set size of the inner circle
	 * @param size size of the inner circle
	 */
	public void setInnerCircleSize(int size){
		mInnerCircleSize = size;
		INCRESING_SIZE = (mOuterCircleSize - mInnerCircleSize) / 100;
	}
	/**
	 * Set size of the outer circle
	 * @param size size of the outer circle
	 */
	public void setOuterCircleSize(int size){
		mOuterCircleSize = size;
		INCRESING_SIZE = (mOuterCircleSize - mInnerCircleSize) / 100;
		SLEEP_TIME = (int) (INCRESING_SIZE * 1500 / mOuterCircleSize);
	}

	/**
	 * Set color of the inner circle
	 * @param color color of the inner circle
	 */
	public void setInnerCircleColor(int color) {
		mInnerCircleColor = color;
	}

	/**
	 * Set color of the outer circle
	 * @param color color of the outer circle
	 */
	public void setOuterCircleColor(int color) {
		mOuterCircleColor = color;
	}

	/**
	 * Set color of the progress text 
	 * @param color color of the progress text 
	 */
	public void setProgressTextColor(int color) {
		mTextProgressColor = color;
	}

	/**
	 * Set color of circle of expanding progress  
	 * @param color color of circle of expanding progress 
	 */
	public void setProgresColor(int color) {
		mProgressCircleColor = color;
	}
	
	/**
	 * Set width of the outer circle line
	 * @param width width of the outer circle line
	 */
	public void setOuterCircleLineWidth(int width) {
		mOuterCircleLineWidth = width;
	}
	
	/**
	 * Set size of the progress text
	 * @param size size of the progress text
	 */
	public void setTextProgressSize(int size) {
		mTextSize = size;
	}

	/**
	 * get maximum value of progress
	 * @return maximum value of progress
	 */
	public int getMaxProgress() {
		return MAX_PROGRESS;
	}

	/**
	 * set current progress 
	 * @param progress set progress from 0 to max progress(100)
	 */
	public void setProgress(int progress) {
		if (mUpdateTask != null)
			mUpdateTask.cancel(true);
		if (progress > MAX_PROGRESS)
			return;
		int size = (int) (progress * INCRESING_SIZE + mInnerCircleSize);

		mUpdateTask = new ProgressUpdateTask();
		mUpdateTask.execute(progress, size);
	}

	@Override
	public void onDraw(final Canvas canvas) {
		canvas.drawCircle(mProgressCircle.centerX, mProgressCircle.centerY,
				mProgressCircle.radius, mProgressCirclePaint);
		canvas.drawCircle(mInnerCircle.centerX, mInnerCircle.centerY,
				mInnerCircle.radius, mInnerCirclePaint);
		canvas.drawCircle(mOuterCircle.centerX, mOuterCircle.centerY,
				mOuterCircle.radius, mOuterCirclePaint);
		canvas.drawText(mCurrentProgress, mInnerCircle.centerX,
				mInnerCircle.centerY, mTextPaint);
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int x = w / 2;
		int y = h / 2;

		mProgressCircle = new Circle(x, y, mInnerCircleSize);
		mInnerCircle = new Circle(x, y, mInnerCircleSize);
		mOuterCircle = new Circle(x, y, mOuterCircleSize);
	}

	public class ProgressUpdateTask extends
			AsyncTask<Integer, Integer, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			if (mProgressCircle == null)
				return null;
			int progress = params[0];
			int size = params[1];

			if (progress >= 100)
				progress = 100;

			boolean isIncresing = mProgressCircle.radius <= size;
			while (continueLoop(isIncresing, size)) {
				// exit loop if asynctask is cancelled
				if (isCancelled())
					break;

				if (isIncresing)
					mProgressCircle.radius += INCRESING_SIZE;
				else
					mProgressCircle.radius -= INCRESING_SIZE;

				publishProgress(progress);
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return progress;
		}

		private boolean continueLoop(boolean isIncresing, int size) {
			if (isIncresing)
				return mProgressCircle.radius <= size;
			else
				return mProgressCircle.radius >= size;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			int value = values[0];
			mCurrentProgress = value + "%";
			invalidate();
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result != null)
				mCurrentProgress = result + "%";
		}
	}

	/**
	 * Cancel updating progress task  
	 */
	public void cancelUpdateTask() {
		if (mUpdateTask != null)
			mUpdateTask.cancel(true);
	}

}