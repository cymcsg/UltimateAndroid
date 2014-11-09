package com.marshalchen.common.uimodule.cardsSwiped.view;

import android.animation.*;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import com.marshalchen.common.uimodule.R;
import com.marshalchen.common.uimodule.cardsSwiped.model.CardModel;
import com.marshalchen.common.uimodule.cardsSwiped.model.Orientations;

import java.util.Random;

public class CardContainer extends AdapterView<ListAdapter> {
	public static final int INVALID_POINTER_ID = -1;
	private int mActivePointerId = INVALID_POINTER_ID;
	private static final double DISORDERED_MAX_ROTATION_RADIANS = Math.PI / 64;
    private int mNumberOfCards = -1;
	private final DataSetObserver mDataSetObserver = new DataSetObserver() {
		@Override
		public void onChanged() {
			super.onChanged();
			clearStack();
			ensureFull();
		}

		@Override
		public void onInvalidated() {
			super.onInvalidated();
			clearStack();
		}
	};
	private final Random mRandom = new Random();
	private final Rect boundsRect = new Rect();
	private final Rect childRect = new Rect();
	private final Matrix mMatrix = new Matrix();


	//TODO: determine max dynamically based on device speed
	private int mMaxVisible = 10;
	private GestureDetector mGestureDetector;
	private int mFlingSlop;
	private Orientations.Orientation mOrientation;
	private ListAdapter mListAdapter;
	private float mLastTouchX;
	private float mLastTouchY;
	private View mTopCard;
	private int mTouchSlop;
	private int mGravity;
	private int mNextAdapterPosition;
	private boolean mDragging;

	public CardContainer(Context context) {
		super(context);

        setOrientation(Orientations.Orientation.Disordered);
		setGravity(Gravity.CENTER);
		init();

	}

	public CardContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		initFromXml(attrs);
		init();
	}


	public CardContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initFromXml(attrs);
		init();
	}

	private void init() {
		ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
		mFlingSlop = viewConfiguration.getScaledMinimumFlingVelocity();
		mTouchSlop = viewConfiguration.getScaledTouchSlop();
		mGestureDetector = new GestureDetector(getContext(), new GestureListener());
	}

	private void initFromXml(AttributeSet attr) {
		TypedArray a = getContext().obtainStyledAttributes(attr,
				R.styleable.CardContainer);

		setGravity(a.getInteger(R.styleable.CardContainer_android_gravity, Gravity.CENTER));
		int orientation = a.getInteger(R.styleable.CardContainer_orientation, 1);
		setOrientation(Orientations.Orientation.fromIndex(orientation));

		a.recycle();
	}

	@Override
	public ListAdapter getAdapter() {
		return mListAdapter;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		if (mListAdapter != null)
			mListAdapter.unregisterDataSetObserver(mDataSetObserver);

		clearStack();
		mTopCard = null;
		mListAdapter = adapter;
		mNextAdapterPosition = 0;
		adapter.registerDataSetObserver(mDataSetObserver);

		ensureFull();

		if (getChildCount() != 0) {
			mTopCard = getChildAt(getChildCount() - 1);
			mTopCard.setLayerType(LAYER_TYPE_HARDWARE, null);
		}
        mNumberOfCards = getAdapter().getCount();
		requestLayout();
	}

	private void ensureFull() {
		while (mNextAdapterPosition < mListAdapter.getCount() && getChildCount() < mMaxVisible) {
			View view = mListAdapter.getView(mNextAdapterPosition, null, this);
			view.setLayerType(LAYER_TYPE_SOFTWARE, null);
			if(mOrientation == Orientations.Orientation.Disordered) {
				view.setRotation(getDisorderedRotation());
			}
			addViewInLayout(view, 0, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					mListAdapter.getItemViewType(mNextAdapterPosition)), false);

			requestLayout();

			mNextAdapterPosition += 1;
		}
	}

	private void clearStack() {
		removeAllViewsInLayout();
		mNextAdapterPosition = 0;
		mTopCard = null;
	}

	public Orientations.Orientation getOrientation() {
		return mOrientation;
	}

	public void setOrientation(Orientations.Orientation orientation) {
		if (orientation == null)
			throw new NullPointerException("Orientation may not be null");
		if(mOrientation != orientation) {
			this.mOrientation = orientation;
			if(orientation == Orientations.Orientation.Disordered) {
				for (int i = 0; i < getChildCount(); i++) {
					View child = getChildAt(i);
					child.setRotation(getDisorderedRotation());
				}
			}
			else {
				for (int i = 0; i < getChildCount(); i++) {
					View child = getChildAt(i);
					child.setRotation(0);
				}
			}
			requestLayout();
		}

	}

	private float getDisorderedRotation() {
		return (float) Math.toDegrees(mRandom.nextGaussian() * DISORDERED_MAX_ROTATION_RADIANS);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int requestedWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
		int requestedHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
		int childWidth, childHeight;

		if (mOrientation == Orientations.Orientation.Disordered) {
			int R1, R2;
			if (requestedWidth >= requestedHeight) {
				R1 = requestedHeight;
				R2 = requestedWidth;
			} else {
				R1 = requestedWidth;
				R2 = requestedHeight;
			}
			childWidth = (int) ((R1 * Math.cos(DISORDERED_MAX_ROTATION_RADIANS) - R2 * Math.sin(DISORDERED_MAX_ROTATION_RADIANS)) / Math.cos(2 * DISORDERED_MAX_ROTATION_RADIANS));
			childHeight = (int) ((R2 * Math.cos(DISORDERED_MAX_ROTATION_RADIANS) - R1 * Math.sin(DISORDERED_MAX_ROTATION_RADIANS)) / Math.cos(2 * DISORDERED_MAX_ROTATION_RADIANS));
		} else {
			childWidth = requestedWidth;
			childHeight = requestedHeight;
		}

		int childWidthMeasureSpec, childHeightMeasureSpec;
		childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST);
		childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST);

		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			assert child != null;
			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		for (int i = 0; i < getChildCount(); i++) {
			boundsRect.set(0, 0, getWidth(), getHeight());

			View view = getChildAt(i);
			int w, h;
			w = view.getMeasuredWidth();
			h = view.getMeasuredHeight();

			Gravity.apply(mGravity, w, h, boundsRect, childRect);
			view.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mTopCard == null) {
			return false;
		}
		if (mGestureDetector.onTouchEvent(event)) {
			return true;
		}
		Log.d("Touch Event", MotionEvent.actionToString(event.getActionMasked()) + " ");
		final int pointerIndex;
		final float x, y;
		final float dx, dy;
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				mTopCard.getHitRect(childRect);

				pointerIndex = event.getActionIndex();
				x = event.getX(pointerIndex);
				y = event.getY(pointerIndex);

				if (!childRect.contains((int) x, (int) y)) {
					return false;
				}
				mLastTouchX = x;
				mLastTouchY = y;
				mActivePointerId = event.getPointerId(pointerIndex);


				float[] points = new float[]{x - mTopCard.getLeft(), y - mTopCard.getTop()};
				mTopCard.getMatrix().invert(mMatrix);
				mMatrix.mapPoints(points);
				mTopCard.setPivotX(points[0]);
				mTopCard.setPivotY(points[1]);

				break;
			case MotionEvent.ACTION_MOVE:

				pointerIndex = event.findPointerIndex(mActivePointerId);
				x = event.getX(pointerIndex);
				y = event.getY(pointerIndex);

				dx = x - mLastTouchX;
				dy = y - mLastTouchY;

				if (Math.abs(dx) > mTouchSlop || Math.abs(dy) > mTouchSlop) {
					mDragging = true;
				}

				if(!mDragging) {
					return true;
				}

				mTopCard.setTranslationX(mTopCard.getTranslationX() + dx);
				mTopCard.setTranslationY(mTopCard.getTranslationY() + dy);

				mTopCard.setRotation(40 * mTopCard.getTranslationX() / (getWidth() / 2.f));

				mLastTouchX = x;
				mLastTouchY = y;
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if (!mDragging) {
					return true;
				}
				mDragging = false;
				mActivePointerId = INVALID_POINTER_ID;
				ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mTopCard,
						PropertyValuesHolder.ofFloat("translationX", 0),
						PropertyValuesHolder.ofFloat("translationY", 0),
						PropertyValuesHolder.ofFloat("rotation", (float) Math.toDegrees(mRandom.nextGaussian() * DISORDERED_MAX_ROTATION_RADIANS)),
						PropertyValuesHolder.ofFloat("pivotX", mTopCard.getWidth() / 2.f),
						PropertyValuesHolder.ofFloat("pivotY", mTopCard.getHeight() / 2.f)
				).setDuration(250);
				animator.setInterpolator(new AccelerateInterpolator());
				animator.start();
				break;
			case MotionEvent.ACTION_POINTER_UP:
				pointerIndex = event.getActionIndex();
				final int pointerId = event.getPointerId(pointerIndex);

				if (pointerId == mActivePointerId) {
					final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
					mLastTouchX = event.getX(newPointerIndex);
					mLastTouchY = event.getY(newPointerIndex);

					mActivePointerId = event.getPointerId(newPointerIndex);
				}
				break;
		}

		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (mTopCard == null) {
			return false;
		}
		if (mGestureDetector.onTouchEvent(event)) {
			return true;
		}
		final int pointerIndex;
		final float x, y;
		final float dx, dy;
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				mTopCard.getHitRect(childRect);

                CardModel cardModel = (CardModel)getAdapter().getItem(0);

                if (cardModel.getOnClickListener() != null) {
                   cardModel.getOnClickListener().OnClickListener();
                }
				pointerIndex = event.getActionIndex();
				x = event.getX(pointerIndex);
				y = event.getY(pointerIndex);

				if (!childRect.contains((int) x, (int) y)) {
					return false;
				}

				mLastTouchX = x;
				mLastTouchY = y;
				mActivePointerId = event.getPointerId(pointerIndex);
				break;
			case MotionEvent.ACTION_MOVE:
				pointerIndex = event.findPointerIndex(mActivePointerId);
				x = event.getX(pointerIndex);
				y = event.getY(pointerIndex);
				if (Math.abs(x - mLastTouchX) > mTouchSlop || Math.abs(y - mLastTouchY) > mTouchSlop) {
					float[] points = new float[]{x - mTopCard.getLeft(), y - mTopCard.getTop()};
					mTopCard.getMatrix().invert(mMatrix);
					mMatrix.mapPoints(points);
					mTopCard.setPivotX(points[0]);
					mTopCard.setPivotY(points[1]);
					return true;
				}
		}

		return false;
	}

	@Override
	public View getSelectedView() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSelection(int position) {
		throw new UnsupportedOperationException();
	}

	public int getGravity() {
		return mGravity;
	}

	public void setGravity(int gravity) {
		mGravity = gravity;
	}

	public static class LayoutParams extends ViewGroup.LayoutParams {

		int viewType;

		public LayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);
		}

		public LayoutParams(int width, int height) {
			super(width, height);
		}

		public LayoutParams(ViewGroup.LayoutParams source) {
			super(source);
		}

		public LayoutParams(int w, int h, int viewType) {
			super(w, h);
			this.viewType = viewType;
		}
	}

	private class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.d("Fling", "Fling with " + velocityX + ", " + velocityY);
			final View topCard = mTopCard;
			float dx = e2.getX() - e1.getX();
			if (Math.abs(dx) > mTouchSlop &&
					Math.abs(velocityX) > Math.abs(velocityY) &&
					Math.abs(velocityX) > mFlingSlop * 3) {
				float targetX = topCard.getX();
				float targetY = topCard.getY();
				long duration = 0;

				boundsRect.set(0 - topCard.getWidth() - 100, 0 - topCard.getHeight() - 100, getWidth() + 100, getHeight() + 100);

				while (boundsRect.contains((int) targetX, (int) targetY)) {
					targetX += velocityX / 10;
					targetY += velocityY / 10;
					duration += 100;
				}

				duration = Math.min(500, duration);

				mTopCard = getChildAt(getChildCount() - 2);
                CardModel cardModel = (CardModel)getAdapter().getItem(0);

				if(mTopCard != null)
					mTopCard.setLayerType(LAYER_TYPE_HARDWARE, null);

                if (cardModel.getOnCardDimissedListener() != null) {
                    if ( targetX > 0 ) {
                        cardModel.getOnCardDimissedListener().onDislike();
                    } else {
                        cardModel.getOnCardDimissedListener().onLike();
                    }
                }

				topCard.animate()
						.setDuration(duration)
						.alpha(.75f)
						.setInterpolator(new LinearInterpolator())
						.x(targetX)
						.y(targetY)
						.rotation(Math.copySign(45, velocityX))
						.setListener(new AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(Animator animation) {
								removeViewInLayout(topCard);
								ensureFull();
							}

							@Override
							public void onAnimationCancel(Animator animation) {
								onAnimationEnd(animation);
							}
						});
				return true;
			} else
				return false;
		}
	}
}
