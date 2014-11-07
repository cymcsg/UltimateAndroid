package com.marshalchen.common.ui;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cym on 14-6-6.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

    public boolean onTouch(final View v, final MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            onTouch(e);
            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    // onTouch(e);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }
    public void onTouch(MotionEvent e) {
    }
    public void onSwipeRight() {

//call this only if your condition was set
    }

    public void onSwipeLeft() {
//nothing, this means,swipes to left will be ignored
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}
