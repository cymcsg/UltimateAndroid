package com.marshalchen.common.uimodule.tileView.layouts;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import com.marshalchen.common.uimodule.tileView.animation.Tween;
import com.marshalchen.common.uimodule.tileView.animation.TweenListener;
import com.marshalchen.common.uimodule.tileView.animation.easing.Strong;
import com.marshalchen.common.uimodule.tileView.widgets.Scroller;

import java.lang.ref.WeakReference;
import java.util.HashSet;


/**
 * ZoomPanLayout extends ViewGroup to provide support for scrolling and zooming.  Fling, drag, pinch and
 * double-tap events are supported natively.
 * 
 * ZoomPanLayout does not support direct insertion of child Views, and manages positioning through an intermediary View.
 * the addChild method provides an interface to add layouts to that intermediary view.  Each of these children are provided
 * with LayoutParams of MATCH_PARENT for both axes, and will always be positioned at 0,0, so should generally be ViewGroups
 * themselves (RelativeLayouts or FrameLayouts are generally appropriate).
 */

public class ZoomPanLayout extends ViewGroup {

	private static final int MINIMUM_VELOCITY = 50;
	private static final int ZOOM_ANIMATION_DURATION = 500;
	private static final int SLIDE_DURATION = 500;
	private static final int VELOCITY_UNITS = 1000;
	private static final int DOUBLE_TAP_TIME_THRESHOLD = 250;
	private static final int SINGLE_TAP_DISTANCE_THRESHOLD = 50;
	private static final double MINIMUM_PINCH_SCALE = 0.0;
	private static final float FRICTION = 0.99f;

	private int baseWidth;
	private int baseHeight;

	private int scaledWidth;
	private int scaledHeight;

	private double scale = 1;
	private double historicalScale = 1;

	private double minScale = 0;
	private double maxScale = 1;

	private boolean scaleToFit = true;

	private Point pinchStartScroll = new Point();
	private Point pinchStartOffset = new Point();
	private double pinchStartDistance;

	private Point doubleTapStartScroll = new Point();
	private Point doubleTapStartOffset = new Point();
	private double doubleTapDestinationScale;

	private Point firstFinger = new Point();
	private Point secondFinger = new Point();
	private Point lastFirstFinger = new Point();
	private Point lastSecondFinger = new Point();

	private Point scrollPosition = new Point();

	private Point singleTapHistory = new Point();
	private Point doubleTapHistory = new Point();

	private Point firstFingerLastDown = new Point();
	private Point secondFingerLastDown = new Point();
	
	private Point actualPoint = new Point();
	private Point destinationScroll = new Point();

	private boolean secondFingerIsDown = false;
	private boolean firstFingerIsDown = false;

	private boolean isTapInterrupted = false;
	private boolean isBeingFlung = false;
	private boolean isDragging = false;	
	private boolean isPinching = false;	

	private int dragStartThreshold = 30;
	private int pinchStartThreshold = 30;	

	private long lastTouchedAt;

	private ScrollActionHandler scrollActionHandler;

	private Scroller scroller;
	private VelocityTracker velocity;

	private HashSet<GestureListener> gestureListeners = new HashSet<GestureListener>();
	private HashSet<ZoomPanListener> zoomPanListeners = new HashSet<ZoomPanListener>();

	private StaticLayout clip;

	private TweenListener tweenListener = new TweenListener() {
		@Override
		public void onTweenComplete() {
			isTweening = false;
			for ( ZoomPanListener listener : zoomPanListeners ) {
				listener.onZoomComplete( scale );
				listener.onZoomPanEvent();
			}
		}

		@Override
		public void onTweenProgress( double progress, double eased ) {
			double originalChange = doubleTapDestinationScale - historicalScale;
			double updatedChange = originalChange * eased;
			double currentScale = historicalScale + updatedChange;
			setScale( currentScale );
			maintainScrollDuringScaleTween();
		}

		@Override
		public void onTweenStart() {
			saveHistoricalScale();
			isTweening = true;
			for ( ZoomPanListener listener : zoomPanListeners ) {
				listener.onZoomStart( scale );
				listener.onZoomPanEvent();
			}
		}
	};

	private boolean isTweening;
	private Tween tween = new Tween();
	{
		tween.setAnimationEase( Strong.EaseOut );
		tween.addTweenListener( tweenListener );
	}

	/**
	 * Constructor to use when creating a ZoomPanLayout from code.  Inflating from XML is not currently supported.
	 * @param context (Context) The Context the ZoomPanLayout is running in, through which it can access the current theme, resources, etc.
	 */
	public ZoomPanLayout( Context context ) {

		super( context );
		setWillNotDraw( false );

		scrollActionHandler = new ScrollActionHandler( this );

		scroller = new Scroller( context );
		scroller.setFriction( FRICTION );

		clip = new StaticLayout( context );
		super.addView( clip, -1, new LayoutParams( -1, -1 ) );

		updateClip();
	}

	//------------------------------------------------------------------------------------
	// PUBLIC API
	//------------------------------------------------------------------------------------

	/**
	 * Determines whether the ZoomPanLayout should limit it's minimum scale to no less than what would be required to fill it's container
	 * @param shouldScaleToFit (boolean) True to limit minimum scale, false to allow arbitrary minimum scale (see {@link setScaleLimits})
	 */
	public void setScaleToFit( boolean shouldScaleToFit ) {
		scaleToFit = shouldScaleToFit;
		calculateMinimumScaleToFit();
	}

	/**
	 * Set minimum and maximum scale values for this ZoomPanLayout. 
	 * Note that if {@link } is set to true, the minimum value set here will be ignored
	 * Default values are 0 and 1.
	 * @param min
	 * @param max
	 */
	public void setScaleLimits( double min, double max ) {
		// if scaleToFit is set, don't allow overwrite
		if ( !scaleToFit ) {
			minScale = min;
		}
		maxScale = max;
		setScale( scale );
	}

	/**
	 * Sets the size (width and height) of the ZoomPanLayout as it should be rendered at a scale of 1f (100%)
	 * @param wide width
	 * @param tall height
	 */
	public void setSize( int wide, int tall ) {
		baseWidth = wide;
		baseHeight = tall;
		scaledWidth = (int) ( baseWidth * scale );
		scaledHeight = (int) ( baseHeight * scale );
		updateClip();
	}

	/**
	 * Returns the base (un-scaled) width
	 * @return (int) base width
	 */
	public int getBaseWidth() {
		return baseWidth;
	}

	/**
	 * Returns the base (un-scaled) height
	 * @return (int) base height
	 */
	public int getBaseHeight() {
		return baseHeight;
	}

	/**
	 * Returns the scaled width
	 * @return (int) scaled width
	 */
	public int getScaledWidth() {
		return scaledWidth;
	}

	/**
	 * Returns the scaled height
	 * @return (int) scaled height
	 */
	public int getScaledHeight() {
		return scaledHeight;
	}

	/**
	 * Sets the scale (0-1) of the ZoomPanLayout
	 * @param scale (double) The new value of the ZoomPanLayout scale
	 */
	public void setScale( double d ) {
		d = Math.max( d, minScale );
		d = Math.min( d, maxScale );
		if ( scale != d ) {
			scale = d;
			scaledWidth = (int) ( baseWidth * scale );
			scaledHeight = (int) ( baseHeight * scale );
			updateClip();
			postInvalidate();
			for ( ZoomPanListener listener : zoomPanListeners ) {
				listener.onScaleChanged( scale );
				listener.onZoomPanEvent();
			}
		}
	}

	/**
	 * Requests a redraw
	 */
	public void redraw() {
		updateClip();
		postInvalidate();
	}

	/**
	 * Retrieves the current scale of the ZoomPanLayout
	 * @return (double) the current scale of the ZoomPanLayout
	 */
	public double getScale() {
		return scale;
	}

	/**
	 * Returns whether the ZoomPanLayout is currently being flung
	 * @return (boolean) true if the ZoomPanLayout is currently flinging, false otherwise
	 */
	public boolean isFlinging() {
		return isBeingFlung;
	}

	/**
	 * Returns the single child of the ZoomPanLayout, a ViewGroup that serves as an intermediary container
	 * @return (View) The child view of the ZoomPanLayout that manages all contained views
	 */
	public View getClip() {
		return clip;
	}
	
	/**
	 * Returns the minimum distance required to start a drag operation, in pixels.
	 * @return (int) Pixel threshold required to start a drag.
	 */
	public int getDragStartThreshold(){
		return dragStartThreshold;
	}
	
	/**
	 * Returns the minimum distance required to start a drag operation, in pixels.
	 * @param threshold (int) Pixel threshold required to start a drag.
	 */
	public void setDragStartThreshold( int threshold ){
		dragStartThreshold = threshold;
	}
	
	/**
	 * Returns the minimum distance required to start a pinch operation, in pixels.
	 * @return (int) Pixel threshold required to start a pinch.
	 */
	public int getPinchStartThreshold(){
		return pinchStartThreshold;
	}
	
	/**
	 * Returns the minimum distance required to start a pinch operation, in pixels.
	 * @param threshold (int) Pixel threshold required to start a pinch.
	 */
	public void setPinchStartThreshold( int threshold ){
		pinchStartThreshold = threshold;
	}

	/**
	 * Adds a GestureListener to the ZoomPanLayout, which will receive gesture events
	 * @param listener (GestureListener) Listener to add
	 * @return (boolean) true when the listener set did not already contain the Listener, false otherwise 
	 */
	public boolean addGestureListener( GestureListener listener ) {
		return gestureListeners.add( listener );
	}

	/**
	 * Removes a GestureListener from the ZoomPanLayout
	 * @param listener (GestureListener) Listener to remove
	 * @return (boolean) if the Listener was removed, false otherwise
	 */
	public boolean removeGestureListener( GestureListener listener ) {
		return gestureListeners.remove( listener );
	}

	/**
	 * Adds a ZoomPanListener to the ZoomPanLayout, which will receive events relating to zoom and pan actions
	 * @param listener (ZoomPanListener) Listener to add
	 * @return (boolean) true when the listener set did not already contain the Listener, false otherwise 
	 */
	public boolean addZoomPanListener( ZoomPanListener listener ) {
		return zoomPanListeners.add( listener );
	}

	/**
	 * Removes a ZoomPanListener from the ZoomPanLayout
	 * @param listener (ZoomPanListener) Listener to remove
	 * @return (boolean) if the Listener was removed, false otherwise
	 */
	public boolean removeZoomPanListener( ZoomPanListener listener ) {
		return zoomPanListeners.remove( listener );
	}

	/**
	 * Scrolls the ZoomPanLayout to the x and y values specified by {@param point} Point
	 * @param point (Point) Point instance containing the destination x and y values
	 */
	public void scrollToPoint( Point point ) {
		constrainPoint( point );
		int ox = getScrollX();
		int oy = getScrollY();
		int nx = (int) point.x;
		int ny = (int) point.y;
		scrollTo( nx, ny );
		if ( ox != nx || oy != ny ) {
			for ( ZoomPanListener listener : zoomPanListeners ) {
				listener.onScrollChanged( nx, ny );
				listener.onZoomPanEvent();
			}
		}
	}

	/**
	 * Scrolls and centers the ZoomPanLayout to the x and y values specified by {@param point} Point
	 * @param point (Point) Point instance containing the destination x and y values
	 */
	public void scrollToAndCenter( Point point ) { // TODO:
		int x = (int) -( getWidth() * 0.5 );
		int y = (int) -( getHeight() * 0.5 );
		point.offset( x, y );
		scrollToPoint( point );
	}

	/**
	 * Scrolls the ZoomPanLayout to the x and y values specified by {@param point} Point using scrolling animation
	 * @param point (Point) Point instance containing the destination x and y values
	 */
	public void slideToPoint( Point point ) { // TODO:
		constrainPoint( point );
		int startX = getScrollX();
		int startY = getScrollY();
		int dx = point.x - startX;
		int dy = point.y - startY;
		scroller.startScroll( startX, startY, dx, dy, SLIDE_DURATION );
		invalidate(); // we're posting invalidate in computeScroll, yet both are required
	}

	/**
	 * Scrolls and centers the ZoomPanLayout to the x and y values specified by {@param point} Point using scrolling animation
	 * @param point (Point) Point instance containing the destination x and y values
	 */
	public void slideToAndCenter( Point point ) { // TODO:
		int x = (int) -( getWidth() * 0.5 );
		int y = (int) -( getHeight() * 0.5 );
		point.offset( x, y );
		slideToPoint( point );
	}

	/**
	 * <i>This method is experimental</i>
	 * Scroll and scale to match passed Rect as closely as possible.
	 * The widget will attempt to frame the Rectangle, so that it's contained
	 * within the viewport, if possible.
	 * @param rect (Rect) rectangle to frame
	 */
	public void frameViewport( Rect rect ) {
		// position it
		scrollToPoint( new Point( rect.left, rect.top ) ); // TODO: center the axis that's smaller?
		// scale it
		double scaleX = getWidth() / (double) rect.width();
		double scaleY = getHeight() / (double) rect.height();
		double minimumScale = Math.min( scaleX, scaleY );
		smoothScaleTo( minimumScale, SLIDE_DURATION );

	}
	
	/**
	 * Set the scale of the ZoomPanLayout while maintaining the current center point
	 * @param scale (double) The new value of the ZoomPanLayout scale
	 */
	public void setScaleFromCenter( double s ) {

		int centerOffsetX = (int) ( getWidth() * 0.5f );
		int centerOffsetY = (int) ( getHeight() * 0.5f );

		Point offset = new Point( centerOffsetX, centerOffsetY );
		Point scroll = new Point( getScrollX(), getScrollY() );
		scroll.offset( offset.x, offset.y );

		double deltaScale = s / getScale();

		int x = (int) ( scroll.x * deltaScale ) - offset.x;
		int y = (int) ( scroll.y * deltaScale ) - offset.y;
		Point destination = new Point( x, y );

		setScale( s );
		scrollToPoint( destination );

	}

	/**
	 * Adds a View to the intermediary ViewGroup that manages layout for the ZoomPanLayout.
	 * This View will be laid out at the width and height specified by {@setSize} at 0, 0
	 * All ViewGroup.addView signatures are routed through this signature, so the only parameters
	 * considered are child and index.
	 * @param child (View) The View to be added to the ZoomPanLayout view tree
	 * @param index (int) The position at which to add the child View
	 */
	@Override
	public void addView( View child, int index, LayoutParams params ) {
		LayoutParams lp = new LayoutParams( scaledWidth, scaledHeight );
		clip.addView( child, index, lp );
	}

	@Override
	public void removeAllViews() {
		clip.removeAllViews();
	}

	@Override
	public void removeViewAt( int index ) {
		clip.removeViewAt( index );
	}

	@Override
	public void removeViews( int start, int count ) {
		clip.removeViews( start, count );
	}

	/**
	 * Scales the ZoomPanLayout with animated progress
	 * @param destination (double) The final scale to animate to
	 * @param duration (int) The duration (in milliseconds) of the animation
	 */
	public void smoothScaleTo( double destination, int duration ) {
		if ( isTweening ) {
			return;
		}
		saveHistoricalScale();
		int x = (int) ( ( getWidth() * 0.5 ) + 0.5 );
		int y = (int) ( ( getHeight() * 0.5 ) + 0.5 );
		doubleTapStartOffset.set( x, y );
		doubleTapStartScroll.set( getScrollX(), getScrollY() );
		doubleTapStartScroll.offset( x, y );
		startSmoothScaleTo( destination, duration );
	}

	//------------------------------------------------------------------------------------
	// PRIVATE/PROTECTED
	//------------------------------------------------------------------------------------

	@Override
	protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
		measureChildren( widthMeasureSpec, heightMeasureSpec );
		int w = clip.getMeasuredWidth();
		int h = clip.getMeasuredHeight();
		w = Math.max( w, getSuggestedMinimumWidth() );
		h = Math.max( h, getSuggestedMinimumHeight() );
		w = resolveSize( w, widthMeasureSpec );
		h = resolveSize( h, heightMeasureSpec );
		setMeasuredDimension( w, h );
	}

	@Override
	protected void onLayout( boolean changed, int l, int t, int r, int b ) {
		clip.layout( 0, 0, clip.getMeasuredWidth(), clip.getMeasuredHeight() );
		constrainScroll();
		if ( changed ) {
			calculateMinimumScaleToFit();
		}
	}

	private void calculateMinimumScaleToFit() {
		if ( scaleToFit ) {
			double minimumScaleX = getWidth() / (double) baseWidth;
			double minimumScaleY = getHeight() / (double) baseHeight;
			double recalculatedMinScale = Math.max( minimumScaleX, minimumScaleY );
			if ( recalculatedMinScale != minScale ) {
				minScale = recalculatedMinScale;
				setScale( scale );
			}
		}
	}

	private void updateClip() {
		updateViewClip( clip );
		for ( int i = 0; i < clip.getChildCount(); i++ ) {
			View child = clip.getChildAt( i );
			updateViewClip( child );
		}
		constrainScroll();
	}

	private void updateViewClip( View v ) {
		LayoutParams lp = v.getLayoutParams();
		lp.width = scaledWidth;
		lp.height = scaledHeight;
		v.setLayoutParams( lp );
	}

	@Override
	public void computeScroll() {
		if ( scroller.computeScrollOffset() ) {
			Point destination = new Point( scroller.getCurrX(), scroller.getCurrY() );
			scrollToPoint( destination );
			dispatchScrollActionNotification();
			postInvalidate(); // should not be necessary but is...
		}
	}

	private void dispatchScrollActionNotification() {
		if ( scrollActionHandler.hasMessages( 0 ) ) {
			scrollActionHandler.removeMessages( 0 );
		}
		scrollActionHandler.sendEmptyMessageDelayed( 0, 100 );
	}

	private void handleScrollerAction() {
		Point point = new Point();
		point.x = getScrollX();
		point.y = getScrollY();
		for ( GestureListener listener : gestureListeners ) {
			listener.onScrollComplete( point );
		}
		if ( isBeingFlung ) {
			isBeingFlung = false;
			for ( GestureListener listener : gestureListeners ) {
				listener.onFlingComplete( point );
			}
		}
	}

	private void constrainPoint( Point point ) {
		int x = point.x;
		int y = point.y;
		int mx = Math.max( 0, Math.min( x, getLimitX() ) );
		int my = Math.max( 0, Math.min( y, getLimitY() ) );
		if ( x != mx || y != my ) {
			point.set( mx, my );
		}
	}

	private void constrainScroll() { // TODO:
		Point currentScroll = new Point( getScrollX(), getScrollY() );
		Point limitScroll = new Point( currentScroll );
		constrainPoint( limitScroll );
		if ( !currentScroll.equals( limitScroll ) ) {
			scrollToPoint( limitScroll );
		}
	}

	private int getLimitX() {
		return scaledWidth - getWidth();
	}

	private int getLimitY() {
		return scaledHeight - getHeight();
	}

	private void saveHistoricalScale() {
		historicalScale = scale;
	}

	private void savePinchHistory() {
		int x = (int) ( ( firstFinger.x + secondFinger.x ) * 0.5 );
		int y = (int) ( ( firstFinger.y + secondFinger.y ) * 0.5 );
		pinchStartOffset.set( x, y );
		pinchStartScroll.set( getScrollX(), getScrollY() );
		pinchStartScroll.offset( x, y );
	}

	private void maintainScrollDuringPinchOperation() {
		double deltaScale = scale / historicalScale;
		int x = (int) ( pinchStartScroll.x * deltaScale ) - pinchStartOffset.x;
		int y = (int) ( pinchStartScroll.y * deltaScale ) - pinchStartOffset.y;
		destinationScroll.set( x, y );
		scrollToPoint( destinationScroll );
	}

	private void saveDoubleTapHistory() {
		doubleTapStartOffset.set( firstFinger.x, firstFinger.y );
		doubleTapStartScroll.set( getScrollX(), getScrollY() );
		doubleTapStartScroll.offset( doubleTapStartOffset.x, doubleTapStartOffset.y );
	}

	private void maintainScrollDuringScaleTween() {
		double deltaScale = scale / historicalScale;
		int x = (int) ( doubleTapStartScroll.x * deltaScale ) - doubleTapStartOffset.x;
		int y = (int) ( doubleTapStartScroll.y * deltaScale ) - doubleTapStartOffset.y;
		destinationScroll.set( x, y );
		scrollToPoint( destinationScroll );
	}

	private void saveHistoricalPinchDistance() {
		int dx = firstFinger.x - secondFinger.x;
		int dy = firstFinger.y - secondFinger.y;
		pinchStartDistance = Math.sqrt( dx * dx + dy * dy );
	}

	private void setScaleFromPinch() {
		int dx = firstFinger.x - secondFinger.x;
		int dy = firstFinger.y - secondFinger.y;
		double pinchCurrentDistance = Math.sqrt( dx * dx + dy * dy );
		double currentScale = pinchCurrentDistance / pinchStartDistance;
		currentScale = Math.max( currentScale, MINIMUM_PINCH_SCALE );
		currentScale = historicalScale * currentScale;
		setScale( currentScale );
	}

	private void performDrag() {
		Point delta = new Point();
		if ( secondFingerIsDown && !firstFingerIsDown ) {
			delta.set( lastSecondFinger.x, lastSecondFinger.y );
			delta.offset( -secondFinger.x, -secondFinger.y );
		} else {
			delta.set( lastFirstFinger.x, lastFirstFinger.y );
			delta.offset( -firstFinger.x, -firstFinger.y );
		}
		scrollPosition.offset( delta.x, delta.y );
		scrollToPoint( scrollPosition );
	}

	private boolean performFling() {
		if ( secondFingerIsDown ) {
			return false;
		}
		velocity.computeCurrentVelocity( VELOCITY_UNITS );
		double xv = velocity.getXVelocity();
		double yv = velocity.getYVelocity();
		double totalVelocity = Math.abs( xv ) + Math.abs( yv );
		if ( totalVelocity > MINIMUM_VELOCITY ) {
			scroller.fling( getScrollX(), getScrollY(), (int) -xv, (int) -yv, 0, getLimitX(), 0, getLimitY() );
			postInvalidate();
			return true;
		}
		return false;
	}

	// if the taps occurred within threshold, it's a double tap
	private boolean determineIfQualifiedDoubleTap() {
		long now = System.currentTimeMillis();
		long ellapsed = now - lastTouchedAt;
		lastTouchedAt = now;
		return ( ellapsed <= DOUBLE_TAP_TIME_THRESHOLD ) && ( Math.abs( firstFinger.x - doubleTapHistory.x ) <= SINGLE_TAP_DISTANCE_THRESHOLD )
				&& ( Math.abs( firstFinger.y - doubleTapHistory.y ) <= SINGLE_TAP_DISTANCE_THRESHOLD );

	}

	private void saveTapActionOrigination() {
		singleTapHistory.set( firstFinger.x, firstFinger.y );
	}

	private void saveDoubleTapOrigination() {
		doubleTapHistory.set( firstFinger.x, firstFinger.y );
	}
	
	private void saveFirstFingerDown() {
		firstFingerLastDown.set ( firstFinger.x, firstFinger.y );
	}
	
	private void saveSecondFingerDown() {
		secondFingerLastDown.set ( secondFinger.x, secondFinger.y );
	}

	private void setTapInterrupted( boolean v ) {
		isTapInterrupted = v;
	}

	// if the touch event has traveled past threshold since the finger first when down, it's not a tap
	private boolean determineIfQualifiedSingleTap() {
		return !isTapInterrupted && ( Math.abs( firstFinger.x - singleTapHistory.x ) <= SINGLE_TAP_DISTANCE_THRESHOLD )
				&& ( Math.abs( firstFinger.y - singleTapHistory.y ) <= SINGLE_TAP_DISTANCE_THRESHOLD );
	}
	
	private void startSmoothScaleTo( double destination, int duration ){
		if ( isTweening ) {
			return;
		}
		doubleTapDestinationScale = destination;
		tween.setDuration( duration );
		tween.start();
	}

	private void processEvent( MotionEvent event ) {

		// copy for history
		lastFirstFinger.set( firstFinger.x, firstFinger.y );
		lastSecondFinger.set( secondFinger.x, secondFinger.y );

		// set false for now
		firstFingerIsDown = false;
		secondFingerIsDown = false;

		// determine which finger is down and populate the appropriate points
		for ( int i = 0; i < event.getPointerCount(); i++ ) {
			int id = event.getPointerId( i );
			int x = (int) event.getX( i );
			int y = (int) event.getY( i );
			switch ( id ) {
			case 0:
				firstFingerIsDown = true;
				firstFinger.set( x, y );
				actualPoint.set( x, y );
				break;
			case 1:
				secondFingerIsDown = true;
				secondFinger.set( x, y );
				actualPoint.set( x, y );
				break;
			}
		}
		// record scroll position and adjust finger point to account for scroll offset
		scrollPosition.set( getScrollX(), getScrollY() );
		actualPoint.offset( scrollPosition.x, scrollPosition.y );

		// update velocity for flinging
		// TODO: this can probably be moved to the ACTION_MOVE switch
		if ( velocity == null ) {
			velocity = VelocityTracker.obtain();
		}
		velocity.addMovement( event );
	}

	@Override
	public boolean onTouchEvent( MotionEvent event ) {
		// update positions
		processEvent( event );
		// get the type of action
		final int action = event.getAction() & MotionEvent.ACTION_MASK;
		// react based on nature of touch event
		switch ( action ) {
		// first finger goes down
		case MotionEvent.ACTION_DOWN:
			if ( !scroller.isFinished() ) {
				scroller.abortAnimation();
			}
			isBeingFlung = false;
			isDragging = false;
			setTapInterrupted( false );
			saveFirstFingerDown();
			saveTapActionOrigination();
			for ( GestureListener listener : gestureListeners ) {
				listener.onFingerDown( actualPoint );
			}
			break;
		// second finger goes down
		case MotionEvent.ACTION_POINTER_DOWN:
			isPinching = false;
			saveSecondFingerDown();
			setTapInterrupted( true );
			for ( GestureListener listener : gestureListeners ) {
				listener.onFingerDown( actualPoint );
			}
			break;
		// either finger moves
		case MotionEvent.ACTION_MOVE:
			// if both fingers are down, that means it's a pinch
			if ( firstFingerIsDown && secondFingerIsDown ) {
				if ( !isPinching ) {
					double firstFingerDistance = getDistance( firstFinger, firstFingerLastDown );
					double secondFingerDistance = getDistance( secondFinger, secondFingerLastDown );
					double distance = ( firstFingerDistance + secondFingerDistance ) * 0.5;
	                isPinching = distance >= pinchStartThreshold;
	                // are we starting a pinch action?
					if ( isPinching ) {
						saveHistoricalPinchDistance();
						saveHistoricalScale();
						savePinchHistory();
						for ( GestureListener listener : gestureListeners ) {
							listener.onPinchStart( pinchStartOffset );
						}
						for ( ZoomPanListener listener : zoomPanListeners ) {
							listener.onZoomStart( scale );
							listener.onZoomPanEvent();
						}
					}
				}
				if ( isPinching ) {
					setScaleFromPinch();
					maintainScrollDuringPinchOperation();
					for ( GestureListener listener : gestureListeners ) {
						listener.onPinch( pinchStartOffset );
					}
				}				
				// otherwise it's a drag
			} else {
				if ( !isDragging ) {
	                double distance = getDistance( firstFinger, firstFingerLastDown );
					isDragging = distance >= dragStartThreshold;
				}
				if ( isDragging ) {
					performDrag();
					for ( GestureListener listener : gestureListeners ) {
						listener.onDrag( actualPoint );
					}
				}
			}
			break;
		// first finger goes up
		case MotionEvent.ACTION_UP:
			if ( performFling() ) {
				isBeingFlung = true;
				Point startPoint = new Point( getScrollX(), getScrollY() );
				Point finalPoint = new Point( scroller.getFinalX(), scroller.getFinalY() );
				for ( GestureListener listener : gestureListeners ) {
					listener.onFling( startPoint, finalPoint );
				}
			}
			if ( velocity != null ) {
				velocity.recycle();
				velocity = null;
			}
			// could be a single tap...
			if ( determineIfQualifiedSingleTap() ) {
				for ( GestureListener listener : gestureListeners ) {
					listener.onTap( actualPoint );
				}
			}
			// or a double tap
			if ( determineIfQualifiedDoubleTap() ) {
				scroller.forceFinished( true );
				saveHistoricalScale();
				saveDoubleTapHistory();
				double destination;
				if ( scale >= maxScale ) {
					destination = minScale;
				} else {
					destination = Math.min( maxScale, scale * 2 );
				}
				startSmoothScaleTo( destination, ZOOM_ANIMATION_DURATION );
				for ( GestureListener listener : gestureListeners ) {
					listener.onDoubleTap( actualPoint );
				}
			}
			// either way it's a finger up event
			for ( GestureListener listener : gestureListeners ) {
				listener.onFingerUp( actualPoint );
			}
			// save coordinates to measure against the next double tap
			saveDoubleTapOrigination();
			isDragging = false;
			isPinching = false;
			break;
		// second finger goes up
		case MotionEvent.ACTION_POINTER_UP:
			isPinching = false;
			setTapInterrupted( true );
			for ( GestureListener listener : gestureListeners ) {
				listener.onFingerUp( actualPoint );
			}
			for ( GestureListener listener : gestureListeners ) {
				listener.onPinchComplete( pinchStartOffset );
			}
			for ( ZoomPanListener listener : zoomPanListeners ) {
				listener.onZoomComplete( scale );
				listener.onZoomPanEvent();
			}
			break;

		}

		return true;

	}
	
	// sugar to calculate distance between 2 Points, because android.graphics.Point is horrible
	private static double getDistance( Point p1, Point p2 ) {
		int x = p1.x - p2.x;
        int y = p1.y - p2.y;
        return Math.sqrt( x * x + y * y );
	}

	private static class ScrollActionHandler extends Handler {
		private final WeakReference<ZoomPanLayout> reference;

		public ScrollActionHandler( ZoomPanLayout zoomPanLayout ) {
			super();
			reference = new WeakReference<ZoomPanLayout>( zoomPanLayout );
		}

		@Override
		public void handleMessage( Message msg ) {
			ZoomPanLayout zoomPanLayout = reference.get();
			if ( zoomPanLayout != null ) {
				zoomPanLayout.handleScrollerAction();
			}
		}
	}

	//------------------------------------------------------------------------------------
	// Public static interfaces and classes
	//------------------------------------------------------------------------------------

	public static interface ZoomPanListener {
		public void onScaleChanged(double scale);
		public void onScrollChanged(int x, int y);
		public void onZoomStart(double scale);
		public void onZoomComplete(double scale);
		public void onZoomPanEvent();
	}

	public static interface GestureListener {
		public void onFingerDown(Point point);
		public void onScrollComplete(Point point);
		public void onFingerUp(Point point);
		public void onDrag(Point point);
		public void onDoubleTap(Point point);
		public void onTap(Point point);
		public void onPinch(Point point);
		public void onPinchStart(Point point);
		public void onPinchComplete(Point point);
		public void onFling(Point startPoint, Point finalPoint);
		public void onFlingComplete(Point point);
	}

}
