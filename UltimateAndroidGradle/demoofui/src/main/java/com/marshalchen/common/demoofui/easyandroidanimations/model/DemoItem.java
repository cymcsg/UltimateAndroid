package com.marshalchen.common.demoofui.easyandroidanimations.model;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

/**
 * This class is a helper class for providing sample content for user interfaces
 * created by Android template wizards.
 */
public class DemoItem {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<DemoAnimation> ITEMS = new ArrayList<DemoAnimation>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static SparseArray<DemoAnimation> ITEM_MAP = new SparseArray<DemoAnimation>();

	static {

		addItem(new DemoAnimation(1, "Blind", "new BlindAnimation(card).animate();"));
		addItem(new DemoAnimation(2, "Blink", "new BlinkAnimation(card).animate();"));
		addItem(new DemoAnimation(3, "Bounce", "new BounceAnimation(card).setNumOfBounces(3)\n.setDuration(Animation.DURATION_SHORT)\n.animate();"));
		addItem(new DemoAnimation(4, "Explode", "new ExplodeAnimation(card).animate();"));
		addItem(new DemoAnimation(5, "Fade In", "new FadeInAnimation(card).animate();"));
		addItem(new DemoAnimation(6, "Fade Out", "new FadeOutAnimation(card).animate();"));
		addItem(new DemoAnimation(7, "Flip Horizontal", "new FlipHorizontalAnimation(card).animate();"));
		addItem(new DemoAnimation(8, "Flip Horizontal To", "new FlipHorizontalToAnimation(card)\n.setFlipToView(card2)\n.setInterpolator(new LinearInterpolator())\n.animate();"));
		addItem(new DemoAnimation(9, "Flip Vertical", "new FlipVerticalAnimation(card).animate();"));
		addItem(new DemoAnimation(10, "Flip Vertical To", "new FlipVerticalToAnimation(card)\n.setFlipToView(card2)\n.setInterpolator(new LinearInterpolator())\n.animate();"));
		addItem(new DemoAnimation(11, "Fold In", "new FoldInAnimation(card).setFolds(10)\n.setOrientation(Orientation.HORIZONTAL)\n.animate();"));
		addItem(new DemoAnimation(12, "Fold Out", "new FoldOutAnimation(card).setFolds(10)\n.setOrientation(Orientation.HORIZONTAL)\n.animate();"));
		addItem(new DemoAnimation(13, "Highlight", "new HighlightAnimation(card).animate();"));
		addItem(new DemoAnimation(14, "Path", "ArrayList<Point> points = new ArrayList<>();\n\\\\TODO: Define points \n new PathAnimation(card).setPoints(points)\n.setDuration(2000).animate();"));
		addItem(new DemoAnimation(15, "Puff In", "new PuffInAnimation(card).animate();"));
		addItem(new DemoAnimation(16, "Puff Out", "new PuffOutAnimation(card).animate();"));
		addItem(new DemoAnimation(17, "Rotate", "new RotationAnimation(card)\n.setPivot(RotationAnimation.PIVOT_TOP_LEFT)\n.animate();"));
		addItem(new DemoAnimation(18, "Scale In", "new ScaleInAnimation(card).animate();"));
		addItem(new DemoAnimation(19, "Scale Out", "new ScaleOutAnimation(card).animate();"));
		addItem(new DemoAnimation(20, "Shake", "new ShakeAnimation(card).setNumOfShakes(3)\n.setDuration(Animation.DURATION_SHORT)\n.animate();"));
		addItem(new DemoAnimation(21, "Slide In", "new SlideInAnimation(card)\n.setDirection(Animation.DIRECTION_UP)\n.animate();"));
		addItem(new DemoAnimation(22, "Slide In Underneath", "new SlideInUnderneathAnimation(card)\n.setDirection(Animation.DIRECTION_DOWN)\n.animate();"));
		addItem(new DemoAnimation(23, "Slide Out", "new SlideOutAnimation(card)\n.setDirection(Animation.DIRECTION_LEFT)\n.animate();"));
		addItem(new DemoAnimation(24, "Slide Out Underneath", "new SlideOutUnderneathAnimation(card)\n.setDirection(Animation.DIRECTION_RIGHT)\n.animate();"));
		addItem(new DemoAnimation(25, "Transfer", "new TransferAnimation(card)\n.setDestinationView(target).animate();"));
		addItem(new DemoAnimation(26, "Parallel Animator", "See ParallelAnimator class and AnimationListener interface for reference"));

	}

	private static void addItem(DemoAnimation item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class DemoAnimation {
		public int id;
		public String title, code;

		public DemoAnimation(int id, String title, String code) {
			this.id = id;
			this.title = title;
			this.code = code;
		}

		@Override
		public String toString() {
			return title;
		}
	}
}
