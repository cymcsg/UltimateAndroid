/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marshalchen.common.uimodule.modifysys;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils.TruncateAt;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import com.marshalchen.common.uimodule.R;
import com.marshalchen.common.commonUtils.logUtils.Logs;

import java.lang.ref.WeakReference;

/**
 * PagerTitleStrip is a non-interactive indicator of the current, next,
 * and previous pages of a {@link android.support.v4.view.ViewPager}. It is intended to be used as a
 * child view of a ViewPager widget in your XML layout.
 * Add it as a child of a ViewPager in your layout file and set its
 * android:layout_gravity to TOP or BOTTOM to pin it to the top or bottom
 * of the ViewPager. The title from each page is supplied by the method
 * {@link android.support.v4.view.PagerAdapter#getPageTitle(int)} in the adapter supplied to
 * the ViewPager.
 * <p/>
 * <p>For an interactive indicator, see {@link android.support.v4.view.PagerTabStrip}.</p>
 */
public class PagerTitleStrip extends ViewGroup implements ViewPager.Decor {
    private static final String TAG = "PagerTitleStrip";

    ViewPager mPager;
    TextView mPrevText;
    TextView mCurrText;
    TextView mNextText;

    private int mLastKnownCurrentPage = -1;
    private float mLastKnownPositionOffset = -1;
    private int mScaledTextSpacing;
    private int mGravity;

    private boolean mUpdatingText;
    private boolean mUpdatingPositions;

    private final PageListener mPageListener = new PageListener();

    private WeakReference<PagerAdapter> mWatchingAdapter;

    private static final int[] ATTRS = new int[]{
            android.R.attr.textAppearance,
            android.R.attr.textSize,
            android.R.attr.textColor,
            android.R.attr.gravity
    };

    private static final int[] TEXT_ATTRS = new int[]{
            0x0101038c // android.R.attr.textAllCaps
    };

    private static final float SIDE_ALPHA = 0.6f;
    private static final int TEXT_SPACING = 16; // dip

    private int mNonPrimaryAlpha;
    int mTextColor;

    interface PagerTitleStripImpl {
        void setSingleLineAllCaps(TextView text);
    }

    static class PagerTitleStripImplBase implements PagerTitleStripImpl {
        public void setSingleLineAllCaps(TextView text) {
            text.setSingleLine();
        }
    }

    static class PagerTitleStripImplIcs implements PagerTitleStripImpl {
        public void setSingleLineAllCaps(TextView text) {
            PagerTitleStripIcs.setSingleLineAllCaps(text);
        }
    }

    private static final PagerTitleStripImpl IMPL;

    static {
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            IMPL = new PagerTitleStripImplIcs();
        } else {
            IMPL = new PagerTitleStripImplBase();
        }
    }

    private static void setSingleLineAllCaps(TextView text) {
        IMPL.setSingleLineAllCaps(text);
    }

    public PagerTitleStrip(Context context) {
        this(context, null);
    }

    public PagerTitleStrip(Context context, AttributeSet attrs) {
        super(context, attrs);

        addView(mPrevText = new TextView(context));
        addView(mCurrText = new TextView(context));
        addView(mNextText = new TextView(context));

        final TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        final int textAppearance = a.getResourceId(0, 0);
        if (textAppearance != 0) {
            mPrevText.setTextAppearance(context, textAppearance);
            mCurrText.setTextAppearance(context, textAppearance);
            mNextText.setTextAppearance(context, textAppearance);
        }
        final int textSize = a.getDimensionPixelSize(1, 0);
        if (textSize != 0) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        if (a.hasValue(2)) {
            final int textColor = a.getColor(2, 0);
            mPrevText.setTextColor(textColor);
            mCurrText.setTextColor(textColor);
            mNextText.setTextColor(textColor);
        }
        mGravity = a.getInteger(3, Gravity.CENTER);
        a.recycle();

        mTextColor = mCurrText.getTextColors().getDefaultColor();
        setNonPrimaryAlpha(SIDE_ALPHA);

        mPrevText.setEllipsize(TruncateAt.END);
        mCurrText.setEllipsize(TruncateAt.END);
        mNextText.setEllipsize(TruncateAt.END);

        boolean allCaps = false;
        if (textAppearance != 0) {
            final TypedArray ta = context.obtainStyledAttributes(textAppearance, TEXT_ATTRS);
            allCaps = ta.getBoolean(0, false);
            ta.recycle();
        }

        if (allCaps) {
            setSingleLineAllCaps(mPrevText);
            setSingleLineAllCaps(mCurrText);
            setSingleLineAllCaps(mNextText);
        } else {
            mPrevText.setSingleLine();
            mCurrText.setSingleLine();
            mNextText.setSingleLine();
        }

        final float density = context.getResources().getDisplayMetrics().density;
        mScaledTextSpacing = (int) (TEXT_SPACING * density);
    }

    /**
     * Set the required spacing between title segments.
     *
     * @param spacingPixels Spacing between each title displayed in pixels
     */
    public void setTextSpacing(int spacingPixels) {
        mScaledTextSpacing = spacingPixels;
        requestLayout();
    }

    /**
     * @return The required spacing between title segments in pixels
     */
    public int getTextSpacing() {
        return mScaledTextSpacing;
    }

    /**
     * Set the alpha value used for non-primary page titles.
     *
     * @param alpha Opacity value in the range 0-1f
     */
    public void setNonPrimaryAlpha(float alpha) {
        mNonPrimaryAlpha = (int) (alpha * 255) & 0xFF;
        final int transparentColor = (mNonPrimaryAlpha << 24) | (mTextColor & 0xFFFFFF);
        mPrevText.setTextColor(transparentColor);
        mNextText.setTextColor(transparentColor);
    }

    /**
     * Set the color value used as the base color for all displayed page titles.
     * Alpha will be ignored for non-primary page titles. See {@link #setNonPrimaryAlpha(float)}.
     *
     * @param color Color hex code in 0xAARRGGBB format
     */
    public void setTextColor(int color) {
        mTextColor = color;
        mCurrText.setTextColor(color);
        final int transparentColor = (mNonPrimaryAlpha << 24) | (mTextColor & 0xFFFFFF);
        mPrevText.setTextColor(transparentColor);
        mNextText.setTextColor(transparentColor);
    }

    public void setTextColor(final int color, final int transColor) {
        mTextColor = color;
        mCurrText.setTextColor(color);
        final int transparentColor = (mNonPrimaryAlpha << 24) | (mTextColor & 0xFFFFFF);
        mPrevText.setTextColor(transColor);
        mNextText.setTextColor(transColor);
//        new Thread(){
//            /**
//             * Calls the <code>run()</code> method of the Runnable object the receiver
//             * holds. If no Runnable is set, does nothing.
//             *
//             * @see Thread#start
//             */
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    sleep(300);
//                    Bundle bundle=new Bundle();
//                    bundle.putInt("color",color);
//                    bundle.putInt("transColor",transColor);
//                    HandlerUtils.sendMessageHandler(textColorHandler,0,bundle);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    Logs.e(e, "");
//                }
//            }
//        }.start();;

    }
//    Handler textColorHandler=new Handler(){
//        /**
//         * Subclasses must implement this to receive messages.
//         *
//         * @param msg
//         */
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Bundle bundle=msg.getData();
//            mCurrText.setTextColor(bundle.getInt("color"));
//            final int transparentColor = (mNonPrimaryAlpha << 24) | (mTextColor & 0xFFFFFF);
//            mPrevText.setTextColor(bundle.getInt("transColor"));
//            mNextText.setTextColor(bundle.getInt("transColor"));
//        }
//    };

    /**
     * Set the default text size to a given unit and value.
     * See {@link android.util.TypedValue} for the possible dimension units.
     * <p/>
     * <p>Example: to set the text size to 14px, use
     * setTextSize(TypedValue.COMPLEX_UNIT_PX, 14);</p>
     *
     * @param unit The desired dimension unit
     * @param size The desired size in the given units
     */
    public void setTextSize(int unit, float size) {
        mPrevText.setTextSize(unit, size);
        mCurrText.setTextSize(unit, size);
        mNextText.setTextSize(unit, size);
    }

    /**
     * Set the {@link android.view.Gravity} used to position text within the title strip.
     * Only the vertical gravity component is used.
     *
     * @param gravity {@link android.view.Gravity} constant for positioning title text
     */
    public void setGravity(int gravity) {
        mGravity = gravity;
        requestLayout();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        final ViewParent parent = getParent();
        if (!(parent instanceof ViewPager)) {
            throw new IllegalStateException(
                    "PagerTitleStrip must be a direct child of a ViewPager.");
        }

        final ViewPager pager = (ViewPager) parent;
        final PagerAdapter adapter = pager.getAdapter();

        pager.setInternalPageChangeListener(mPageListener);
        pager.setOnAdapterChangeListener(mPageListener);
        mPager = pager;
        updateAdapter(mWatchingAdapter != null ? mWatchingAdapter.get() : null, adapter);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPager != null) {
            updateAdapter(mPager.getAdapter(), null);
            mPager.setInternalPageChangeListener(null);
            mPager.setOnAdapterChangeListener(null);
            mPager = null;
        }
    }

    void updateText(final int currentItem, PagerAdapter adapter) {
        final int itemCount = adapter != null ? adapter.getCount() : 0;
        mUpdatingText = true;

        CharSequence text = null;
        if (currentItem >= 1 && adapter != null) {
            text = adapter.getPageTitle(currentItem - 1);
        }
        Logs.d("text---" + text + "   " + currentItem);
        //if (text!=null){
        mPrevText.setText(text);
        if (currentItem == 1) {
            if (text != null)
                mPrevText.setText(changeSpanString(text.toString(), 0, 2));
        }
        // }
        mCurrText.setText(adapter != null && currentItem < itemCount ?
                changeSpanString(adapter.getPageTitle(currentItem).toString(), currentItem, 1) : null);

//        if (adapter != null && currentItem < itemCount){
//            final String tempString=adapter.getPageTitle(currentItem).toString();
//            final int tempInt=currentItem;
//            new Thread() {
//                /**
//                 * Calls the <code>run()</code> method of the Runnable object the receiver
//                 * holds. If no Runnable is set, does nothing.
//                 *
//                 * @see Thread#start
//                 */
//                @Override
//                public void run() {
//                    super.run();
//                    try {
//                        sleep(1000);
//                        Bundle bundle=new Bundle();
//                        bundle.putString("string",tempString);
//                        bundle.putInt("int",tempInt);
//                        HandlerUtils.sendMessageHandler(textHandler,1,bundle);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        Logs.e(e, "");
//                    }
//                }
//            }.start();
//    }else{
//            HandlerUtils.sendMessageHandler(textHandler,2);
//        }


        text = null;
        if (currentItem + 1 < itemCount && adapter != null) {
            text = adapter.getPageTitle(currentItem + 1);
        }
        Logs.d("text---" + text + "   " + currentItem);
        //if (text!=null){
        mNextText.setText(text);
        if (currentItem == 0) {
            if (text != null)
                mNextText.setText(changeSpanString(text.toString(), 1, 2));
        }
        // }


        // Measure everything
        final int width = getWidth() - getPaddingLeft() - getPaddingRight();
        final int childHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        final int childWidthSpec = MeasureSpec.makeMeasureSpec((int) (width * 0.8f),
                MeasureSpec.AT_MOST);
        final int childHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST);
        mPrevText.measure(childWidthSpec, childHeightSpec);
        mCurrText.measure(childWidthSpec, childHeightSpec);
        mNextText.measure(childWidthSpec, childHeightSpec);

        mLastKnownCurrentPage = currentItem;

        if (!mUpdatingPositions) {
            updateTextPositions(currentItem, mLastKnownPositionOffset, false);
        }

        mUpdatingText = false;
    }

//    Handler textHandler=new Handler(){
//        /**
//         * Subclasses must implement this to receive messages.
//         *
//         * @param msg
//         */
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what==1){
//                Bundle bundle=msg.getData();
//                mCurrText.setText(
//                        changeSpanString(bundle.getString("string"), bundle.getInt("int"), 1) );
//            }else{
//                mCurrText.setText(null);
//            }
//        }
//    };

    SpannableString changeSpanString(String temp, int position, int color) {

        try {
            temp = temp.replaceAll("-", " - ");


            SpannableString ss = new SpannableString(temp);
            Matrix matrix = new Matrix();

            matrix.postRotate(180);

            Bitmap bitmaporg = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ico_arrow2x);
            if (color == 2) {
                bitmaporg = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ico_arrow_grey2x);
            }
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmaporg, 0, 0,
                    bitmaporg.getWidth(), bitmaporg.getHeight(), matrix, true);
            BitmapDrawable bmd = new BitmapDrawable(getResources(), resizedBitmap);
            BitmapDrawable bm = new BitmapDrawable(getResources(), bitmaporg);
            Drawable drawable = position == 0 ? bm :
                    bmd;
            drawable.setBounds(0, 0,
                    drawable.getIntrinsicWidth() * 12 / 10,
                    drawable.getIntrinsicHeight() * 12 / 10);
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);


            ss.setSpan(imageSpan, temp.indexOf("-"), temp.indexOf("-") + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            return ss;
        } catch (Exception e) {
            e.printStackTrace();
            Logs.e(e, "");
            return null;
        }
    }

    @Override
    public void requestLayout() {
        if (!mUpdatingText) {
            super.requestLayout();
        }
    }

    void updateAdapter(PagerAdapter oldAdapter, PagerAdapter newAdapter) {
        if (oldAdapter != null) {
            oldAdapter.unregisterDataSetObserver(mPageListener);
            mWatchingAdapter = null;
        }
        if (newAdapter != null) {
            newAdapter.registerDataSetObserver(mPageListener);
            mWatchingAdapter = new WeakReference<PagerAdapter>(newAdapter);
        }
        if (mPager != null) {
            mLastKnownCurrentPage = -1;
            mLastKnownPositionOffset = -1;
            updateText(mPager.getCurrentItem(), newAdapter);
            requestLayout();
        }
    }

    void updateTextPositions(int position, float positionOffset, boolean force) {
        if (position != mLastKnownCurrentPage) {
            updateText(position, mPager.getAdapter());
        } else if (!force && positionOffset == mLastKnownPositionOffset) {
            return;
        }

        mUpdatingPositions = true;

        final int prevWidth = mPrevText.getMeasuredWidth();
        final int currWidth = mCurrText.getMeasuredWidth();
        final int nextWidth = mNextText.getMeasuredWidth();
        final int halfCurrWidth = currWidth / 2;

        final int stripWidth = getWidth();
        final int stripHeight = getHeight();
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        final int textPaddedLeft = paddingLeft + halfCurrWidth;
        final int textPaddedRight = paddingRight + halfCurrWidth;
        final int contentWidth = stripWidth - textPaddedLeft - textPaddedRight;

        float currOffset = positionOffset + 0.5f;
        if (currOffset > 1.f) {
            currOffset -= 1.f;
        }
        final int currCenter = stripWidth - textPaddedRight - (int) (contentWidth * currOffset);
        final int currLeft = currCenter - currWidth / 2;
        final int currRight = currLeft + currWidth;

        final int prevBaseline = mPrevText.getBaseline();
        final int currBaseline = mCurrText.getBaseline();
        final int nextBaseline = mNextText.getBaseline();
        final int maxBaseline = Math.max(Math.max(prevBaseline, currBaseline), nextBaseline);
        final int prevTopOffset = maxBaseline - prevBaseline;
        final int currTopOffset = maxBaseline - currBaseline;
        final int nextTopOffset = maxBaseline - nextBaseline;
        final int alignedPrevHeight = prevTopOffset + mPrevText.getMeasuredHeight();
        final int alignedCurrHeight = currTopOffset + mCurrText.getMeasuredHeight();
        final int alignedNextHeight = nextTopOffset + mNextText.getMeasuredHeight();
        final int maxTextHeight = Math.max(Math.max(alignedPrevHeight, alignedCurrHeight),
                alignedNextHeight);

        final int vgrav = mGravity & Gravity.VERTICAL_GRAVITY_MASK;

        int prevTop;
        int currTop;
        int nextTop;
        switch (vgrav) {
            default:
            case Gravity.TOP:
                prevTop = paddingTop + prevTopOffset;
                currTop = paddingTop + currTopOffset;
                nextTop = paddingTop + nextTopOffset;
                break;
            case Gravity.CENTER_VERTICAL:
                final int paddedHeight = stripHeight - paddingTop - paddingBottom;
                final int centeredTop = (paddedHeight - maxTextHeight) / 2;
                prevTop = centeredTop + prevTopOffset;
                currTop = centeredTop + currTopOffset;
                nextTop = centeredTop + nextTopOffset;
                break;
            case Gravity.BOTTOM:
                final int bottomGravTop = stripHeight - paddingBottom - maxTextHeight;
                prevTop = bottomGravTop + prevTopOffset;
                currTop = bottomGravTop + currTopOffset;
                nextTop = bottomGravTop + nextTopOffset;
                break;
        }

        mCurrText.layout(currLeft, currTop, currRight,
                currTop + mCurrText.getMeasuredHeight());

        final int prevLeft = Math.min(paddingLeft, currLeft - mScaledTextSpacing - prevWidth);
        mPrevText.layout(prevLeft, prevTop, prevLeft + prevWidth,
                prevTop + mPrevText.getMeasuredHeight());

        final int nextLeft = Math.max(stripWidth - paddingRight - nextWidth,
                currRight + mScaledTextSpacing);
        mNextText.layout(nextLeft, nextTop, nextLeft + nextWidth,
                nextTop + mNextText.getMeasuredHeight());

        mLastKnownPositionOffset = positionOffset;
        mUpdatingPositions = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("Must measure with an exact width");
        }

        int childHeight = heightSize;
        int minHeight = getMinHeight();
        int padding = 0;
        padding = getPaddingTop() + getPaddingBottom();
        childHeight -= padding;

        final int childWidthSpec = MeasureSpec.makeMeasureSpec((int) (widthSize * 0.8f),
                MeasureSpec.AT_MOST);
        final int childHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST);

        mPrevText.measure(childWidthSpec, childHeightSpec);
        mCurrText.measure(childWidthSpec, childHeightSpec);
        mNextText.measure(childWidthSpec, childHeightSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize * 7 / 5);
        } else {
            int textHeight = mCurrText.getMeasuredHeight();
            setMeasuredDimension(widthSize, Math.max(minHeight, textHeight + padding) * 7 / 5);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mPager != null) {
            final float offset = mLastKnownPositionOffset >= 0 ? mLastKnownPositionOffset : 0;
            updateTextPositions(mLastKnownCurrentPage, offset, true);
        }
    }

    int getMinHeight() {
        int minHeight = 0;
        final Drawable bg = getBackground();
        if (bg != null) {
            minHeight = bg.getIntrinsicHeight();
        }
        return minHeight;
    }

    Handler pageHandler = new Handler() {
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updateTextPositions(msg.getData().getInt("position"), msg.getData().getFloat("positionOffset"), false);
        }
    };

    private class PageListener extends DataSetObserver implements ViewPager.OnPageChangeListener,
            ViewPager.OnAdapterChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset > 0.5f) {
                // Consider ourselves to be on the next page when we're 50% of the way there.
                position++;
            }

            updateTextPositions(position, positionOffset, true);

        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                // Only update the text here if we're not dragging or settling.
                Logs.d("select------");
                updateText(mPager.getCurrentItem(), mPager.getAdapter());

                final float offset = mLastKnownPositionOffset >= 0 ? mLastKnownPositionOffset : 0;
                updateTextPositions(mPager.getCurrentItem(), offset, true);
//                Message message = new Message();
//                message.what = 0;
//                Bundle bundle=new Bundle();
//                bundle.putInt("position",mPager.getCurrentItem());
//                bundle.putFloat("positionOffset", offset);
//                pageHandler.sendMessageDelayed(message,1000);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;
        }

        @Override
        public void onAdapterChanged(PagerAdapter oldAdapter, PagerAdapter newAdapter) {
            updateAdapter(oldAdapter, newAdapter);
        }

        @Override
        public void onChanged() {
            updateText(mPager.getCurrentItem(), mPager.getAdapter());
            Logs.d("select------");
            final float offset = mLastKnownPositionOffset >= 0 ? mLastKnownPositionOffset : 0;
            updateTextPositions(mPager.getCurrentItem(), offset, true);
        }
    }
}
