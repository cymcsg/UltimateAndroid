package com.marshalchen.common.uimodule.simplemodule;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.animation.AnimatorProxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class FilckerAnimationListView extends ListView {

	public static interface Manipulator<T extends ListAdapter> {
		void manipulate(T adapter);
	}

	private static class AdapterWrapper extends BaseAdapter {
		private final ListAdapter adapter;
		private boolean mayNotify = true;

		private final DataSetObserver observer = new DataSetObserver() {
			@Override
			public void onChanged() {
				if (mayNotify) {
					notifyDataSetChanged();
				}
			}

			@Override
			public void onInvalidated() {
				notifyDataSetInvalidated();
			};
		};

		public AdapterWrapper(final ListAdapter adapter) {
			this.adapter = adapter;

			adapter.registerDataSetObserver(observer);
		}

		public void setMayNotify(final boolean mayNotify) {
			this.mayNotify = mayNotify;
		}

		@Override
		public int getCount() {
			return adapter.getCount();
		}

		@Override
		public Object getItem(final int position) {
			return adapter.getItem(position);
		}

		@Override
		public long getItemId(final int position) {
			return adapter.getItemId(position);
		}

		@Override
		public boolean hasStableIds() {
			return adapter.hasStableIds();
		}

		@Override
		public View getView(final int position, final View convertView, final ViewGroup parent) {
			return adapter.getView(position, convertView, parent);
		}

	}

	protected static final int MAX_ANIM_DURATION = 900;
	protected static final int MIN_ANIM_DURATION = 500;
	protected static final int ALPHA_ANIM_DURATION = 300;

	protected final Map<Long, Float> yMap = new HashMap<Long, Float>();
	protected final Map<Long, Integer> positionMap = new HashMap<Long, Integer>();
	protected final Collection<Long> beforeVisible = new HashSet<>();
	protected final Collection<Long> afterVisible = new HashSet<>();

	private final List<Manipulator> pendingManipulations = new ArrayList<>();
	private final List<Manipulator> pendingManipulationsWithoutAnimation = new ArrayList<>();

	private boolean animating = false;
	private AdapterWrapper adapter;
	private float animationDurationFactor = 1f;
	private Interpolator translateInterpolater = new OvershootInterpolator(1.1f);

	public FilckerAnimationListView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public FilckerAnimationListView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FilckerAnimationListView(final Context context) {
		super(context);
		init();
	}

	private void init() {
	}

	public void setAnimationDurationFactor(final float animationDurationFactor) {
		this.animationDurationFactor = animationDurationFactor;
	}

	public float getAnimationDurationFactor() {
		return animationDurationFactor;
	}

	public void setInterpolater(final Interpolator translateInterpolater) {
		this.translateInterpolater = translateInterpolater;
	}

	public Interpolator getInterpolater() {
		return translateInterpolater;
	}

	@Override
	public void setAdapter(final ListAdapter adapter) {
		this.adapter = new AdapterWrapper(adapter);
		super.setAdapter(this.adapter);
	}

	public <T extends ListAdapter> void manipulate(final Manipulator<T> manipulator) {
		if (!animating) {
			prepareAnimation();

			manipulator.manipulate((T) adapter.adapter);

			doAnimation();
		} else {
			pendingManipulations.add(manipulator);
		}
	}

	public <T extends ListAdapter> void manipulateWithoutAnimation(final Manipulator<T> manipulator) {
		if (!animating) {
			manipulator.manipulate((T) adapter.adapter);
			adapter.notifyDataSetChanged();
		} else {
			pendingManipulationsWithoutAnimation.add(manipulator);
		}
	}

	private void manipulatePending() {

		if (!pendingManipulationsWithoutAnimation.isEmpty()) {
			animating = true;
			for (final Manipulator manipulator : pendingManipulationsWithoutAnimation) {
				manipulator.manipulate(adapter.adapter);
			}
			pendingManipulationsWithoutAnimation.clear();
			adapter.notifyDataSetChanged();

			post(new Runnable() {

				@Override
				public void run() {
					animating = false;
					manipulatePending();
				}
			});
		} else {

			if (pendingManipulations.isEmpty()) {
				return;
			}

			prepareAnimation();

			for (final Manipulator manipulator : pendingManipulations) {
				manipulator.manipulate(adapter.adapter);
			}
			pendingManipulations.clear();

			doAnimation();
		}
	}

	private void prepareAnimation() {
		yMap.clear();
		positionMap.clear();
		beforeVisible.clear();
		afterVisible.clear();

		adapter.setMayNotify(false);

		final int childCount = getChildCount();

		final int firstVisiblePosition = getFirstVisiblePosition();

		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			final long id = adapter.getItemId(firstVisiblePosition + i);

			yMap.put(id, ViewHelper.getY(child));
			positionMap.put(id, firstVisiblePosition + i);
		}

		for (int i = 0; i < firstVisiblePosition; i++) {
			final long id = adapter.getItemId(i);
			beforeVisible.add(id);
		}

		final int count = adapter.getCount();

		for (int i = firstVisiblePosition + childCount; i < count; i++) {
			final long id = adapter.getItemId(i);
			afterVisible.add(id);
		}

	}

	private void doAnimation() {
		setEnabled(false);
		animating = true;

		final float durationUnit = (float) MAX_ANIM_DURATION / getHeight();

		animatePreLayout(durationUnit, new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(final Animator animation) {
				adapter.notifyDataSetChanged();

				getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {

					@Override
					public boolean onPreDraw() {
						getViewTreeObserver().removeOnPreDrawListener(this);

						animatePostLayout(durationUnit);

						return true;
					}

				});
			}

		});

	}

	/**
	 * Animate items that are deleted entirely and items that move out of
	 * bounds.
	 */
	private void animatePreLayout(final float durationUnit, final Animator.AnimatorListener listener) {
		final AnimatorSet animatorSet = new AnimatorSet();

		final int firstVisiblePosition = getFirstVisiblePosition();
		final int childCount = getChildCount();

		for (final Iterator<Entry<Long, Float>> iter = yMap.entrySet().iterator(); iter.hasNext();) {
			final Entry<Long, Float> entry = iter.next();

			final long id = entry.getKey();
			final int oldPos = positionMap.get(id);
			final View child = getChildAt(oldPos - firstVisiblePosition);
			final int newPos = getPositionForId(id);

			// fade out items that disappear
			if (newPos == -1) {
				final ObjectAnimator anim = animateAlpha(child, false);
				animatorSet.play(anim);

				iter.remove();
				positionMap.remove(id);
				continue;
			}

			// translate items that move out of bounds
			if (newPos < firstVisiblePosition || newPos > firstVisiblePosition + childCount) {
				final float offset;

				if (newPos < firstVisiblePosition) {
					offset = -getHeight();
				} else {
					offset = getHeight();
				}

				final AnimatorProxy proxy = AnimatorProxy.wrap(child);
				final ObjectAnimator anim = ObjectAnimator.ofFloat(proxy, "translationY", 0f,
                        offset);

				final int finalDuration = getDuration(0, getHeight() / 2, durationUnit);

				anim.setInterpolator(new AccelerateInterpolator());
				anim.setDuration((long) (finalDuration * animationDurationFactor));

				animatorSet.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(final Animator animation) {
						child.post(new Runnable() {

							@Override
							public void run() {
								proxy.setTranslationY(0f);
							}
						});
					}
				});
				animatorSet.play(anim);

				iter.remove();
				positionMap.remove(id);
				continue;
			}
		}

		if (!animatorSet.getChildAnimations().isEmpty()) {
			animatorSet.addListener(listener);
			animatorSet.start();
		} else {
			listener.onAnimationEnd(animatorSet);
		}
	}

	/**
	 * Animate items that just appeared and items that move within the screen.
	 */
	private void animatePostLayout(final float durationUnit) {

		final AnimatorSet animatorSet = new AnimatorSet();

		for (int i = 0; i < getChildCount(); i++) {
			final View child = getChildAt(i);
			final long id = getItemIdAtPosition(getFirstVisiblePosition() + i);

			ObjectAnimator anim = null;

			ViewHelper.setAlpha(child, 1f);

			if (yMap.containsKey(id)) {
				// moved within visible area

				// log("Moved within visible area id: " + id);
				final float oldY = yMap.remove(id);
				final float newY = ViewHelper.getY(child);

				if (oldY != newY) {
					anim = animateY(child, oldY, newY, durationUnit);
				}

			} else {
				// moved into visible area or new

				if (beforeVisible.contains(id)) {
					// moved from top
					final float newY = ViewHelper.getY(child);
					final float oldY = -child.getHeight();

					anim = animateY(child, oldY, newY, durationUnit);
				} else if (afterVisible.contains(id)) {
					// moved from bottom
					final float newY = ViewHelper.getY(child);
					final float oldY = getHeight();

					anim = animateY(child, oldY, newY, durationUnit);
				} else {
					// entirely new
					ViewHelper.setAlpha(child, 0f);

					anim = animateAlpha(child, true);
					anim.setStartDelay(MIN_ANIM_DURATION);
				}

			}

			if (anim != null) {
				animatorSet.play(anim);
			}

		}

		animatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(final Animator animation) {
				finishAnimation();
			};
		});

		animatorSet.start();
	}

	private void finishAnimation() {
		adapter.setMayNotify(true);
		animating = false;

		setEnabled(true);

		manipulatePending();
	}

	protected int getPositionForId(final long id) {
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItemId(i) == id) {
				return i;
			}
		}

		return -1;
	}

	protected ObjectAnimator animateAlpha(final View view, final boolean fadeIn) {
		final ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", fadeIn ? new float[] {
				0f, 1f } : new float[] { 1f, 0f });

		anim.setDuration((long) (ALPHA_ANIM_DURATION * animationDurationFactor));

		return anim;

	}

	protected ObjectAnimator animateY(final View view, final float oldY, final float newY,
			final float durationUnit) {
		final int duration = getDuration(oldY, newY, durationUnit);

		final ObjectAnimator anim = ObjectAnimator.ofFloat(AnimatorProxy.wrap(view),
				"translationY", oldY - newY, 0);

		final int finalDuration = Math
				.min(Math.max(duration, MIN_ANIM_DURATION), MAX_ANIM_DURATION);

		anim.setDuration((long) (finalDuration * animationDurationFactor));
		anim.setInterpolator(translateInterpolater);

		return anim;
	}

	protected static int getDuration(final float oldY, final float newY, final float durationUnit) {
		final float distance = newY - oldY;
		final int duration = (int) (Math.abs(distance) * durationUnit);
		return duration;
	}

	protected boolean isPositionVisible(final int position) {
		final int firstVisiblePosition = getFirstVisiblePosition();

		if (position < firstVisiblePosition) {
			return false;
		}

		final int childCount = getChildCount();

		if (position > firstVisiblePosition + childCount) {
			return false;
		}

		return true;
	}

	protected void log(final String msg) {

			Logs.d(getClass().getSimpleName(), msg);

	}

}
