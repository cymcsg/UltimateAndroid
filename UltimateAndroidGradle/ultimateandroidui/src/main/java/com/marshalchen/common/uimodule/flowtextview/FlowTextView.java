package com.marshalchen.common.uimodule.flowtextview;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.marshalchen.common.uimodule.flowtextview.helpers.ClickHandler;
import com.marshalchen.common.uimodule.flowtextview.helpers.CollisionHelper;
import com.marshalchen.common.uimodule.flowtextview.helpers.PaintHelper;
import com.marshalchen.common.uimodule.flowtextview.helpers.SpanParser;
import com.marshalchen.common.uimodule.flowtextview.listeners.OnLinkClickListener;
import com.marshalchen.common.uimodule.flowtextview.models.HtmlLink;
import com.marshalchen.common.uimodule.flowtextview.models.HtmlObject;
import com.marshalchen.common.uimodule.flowtextview.models.Line;
import com.marshalchen.common.uimodule.flowtextview.models.Obstacle;

import java.util.ArrayList;


public class FlowTextView extends RelativeLayout {

    // FIELDS
    private final PaintHelper mPaintHelper = new PaintHelper();
    private final SpanParser mSpanParser = new SpanParser(this, mPaintHelper);
    private final ClickHandler mClickHandler = new ClickHandler(mSpanParser);
    private int mColor = Color.BLACK;
    private int pageHeight = 0;
    private TextPaint mTextPaint;
    private TextPaint mLinkPaint;
    private float mTextsize = 20.0f;
    private Typeface typeFace;
    private int mDesiredHeight = 100; // height of the whole view
    private boolean needsMeasure = true;
    private final ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    private CharSequence mText = "";
    private boolean mIsHtml = false;

	public FlowTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public FlowTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public FlowTextView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context){
		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.density = getResources().getDisplayMetrics().density;		
		mTextPaint.setTextSize(mTextsize);
		mTextPaint.setColor(Color.BLACK);
		mLinkPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		mLinkPaint.density = getResources().getDisplayMetrics().density;		
		mLinkPaint.setTextSize(mTextsize);
		mLinkPaint.setColor(Color.BLUE);
		mLinkPaint.setUnderlineText(true);
		this.setBackgroundColor(Color.TRANSPARENT);
		this.setOnTouchListener(mClickHandler);
	}

    // INTERESTING DRAWING STUFF
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float mViewWidth = this.getWidth();
        obstacles.clear(); // clear old data, boxes stores an array of "obstacles" that we need to paint the text around
        int lowestYCoord = findBoxesAndReturnLowestObstacleYCoord(); // find the "obstacles" within the view and get the lowest obstacle coordinate at the same time
        String[] blocks = mText.toString().split("\n"); // split the text into its natural blocks


        // set up some counter and helper variables we will us to traverse through the string to be rendered
        int charOffsetStart = 0; // tells us where we are in the original string
        int charOffsetEnd = 0; // tells us where we are in the original string
        int lineIndex = 0;
        float xOffset; // left margin off a given line
        float maxWidth; // how far to the right it can strectch
        float yOffset = 0;
        String thisLineStr; // the current line we are trying to render
        int chunkSize;
        int lineHeight = getLineHeight(); // get the height in pixels of a line for our current TextPaint

        ArrayList<HtmlObject> lineObjects = new ArrayList<HtmlObject>(); // this will get populated with special html objects we need to render
        Object[] spans;

        HtmlObject htmlLine;// reuse for single plain lines

        mSpanParser.reset();

        for(int block_no = 0; block_no <= blocks.length-1; block_no++) // at the highest level we iterate through each 'block' of text
        {
            String thisBlock = blocks[block_no];
            if(thisBlock.length()<=0){ //is a line break
                lineIndex++; // we need a new line
                charOffsetEnd += 2;
                charOffsetStart = charOffsetEnd;
            }else{ // is some actual text

                while(thisBlock.length()>0){ // churn through the block spitting it out onto seperate lines until there is nothing left to render
                    lineIndex++; // we need a new line
                    yOffset = lineIndex * lineHeight; // calculate our new y position based on number of lines * line height
                    Line thisLine = CollisionHelper.calculateLineSpaceForGivenYOffset(yOffset, lineHeight, mViewWidth, obstacles); // calculate a theoretical "line" space that we have to paint into based on the "obstacles" that exist at this yOffset and this line height - collision detection essentially
                    xOffset = thisLine.leftBound;
                    maxWidth = thisLine.rightBound - thisLine.leftBound;
                    float actualWidth;



                    // now we have a line of known maximum width that we can render to, figure out how many characters we can use to get that width taking into account html funkyness
                    do {
                        chunkSize = getChunk(thisBlock, maxWidth);
                        int thisCharOffset = charOffsetEnd+chunkSize;

                        if(chunkSize>1){
                            thisLineStr = thisBlock.substring(0, chunkSize);
                        }
                        else{
                            thisLineStr = "";
                        }

                        lineObjects.clear();

                        if(mIsHtml){
                            spans = ((Spanned) mText).getSpans(charOffsetStart,  thisCharOffset, Object.class);
                            if(spans.length > 0){
                                actualWidth = mSpanParser.parseSpans(lineObjects, spans, charOffsetStart, thisCharOffset, xOffset);
                            }else{
                                actualWidth = maxWidth; // if no spans then the actual width will be <= maxwidth anyway
                            }
                        }else{
                            actualWidth = maxWidth;// if not html then the actual width will be <= maxwidth anyway
                        }

                        if(actualWidth>maxWidth){
                            maxWidth-=5; // if we end up looping - start slicing chars off till we get a suitable size
                        }

                    } while (actualWidth > maxWidth);

                    // chunk is ok
                    charOffsetEnd += chunkSize;


                    if(lineObjects.size() <= 0 ){ // no funky objects found, add the whole chunk as one object
                        htmlLine = new HtmlObject(thisLineStr, 0, 0, xOffset, mTextPaint);
                        lineObjects.add(htmlLine);
                    }

                    for (HtmlObject thisHtmlObject : lineObjects) {

                        if(thisHtmlObject instanceof HtmlLink){
                            HtmlLink thisLink = (HtmlLink) thisHtmlObject;
                            float thisLinkWidth = thisLink.paint.measureText(thisHtmlObject.content);
                            mSpanParser.addLink(thisLink, yOffset, thisLinkWidth, lineHeight);
                        }

                        paintObject(canvas, thisHtmlObject.content, thisHtmlObject.xOffset, yOffset, thisHtmlObject.paint);

                        if(thisHtmlObject.recycle){
                            mPaintHelper.recyclePaint(thisHtmlObject.paint);
                        }
                    }


                    if(chunkSize>=1) thisBlock = thisBlock.substring(chunkSize, thisBlock.length());

                    charOffsetStart = charOffsetEnd;
                }
            }
        }

        yOffset += (lineHeight/2);

        View child = getChildAt(getChildCount()-1);
        if (child.getTag() != null)
        {
            if (child.getTag().toString().equalsIgnoreCase("hideable"))
            {
                if (yOffset > pageHeight)
                {
                    if (yOffset < obstacles.get(obstacles.size()-1).topLefty - getLineHeight())
                    {
                        child.setVisibility(View.GONE);
                    }
                    else
                    {
                        child.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    child.setVisibility(View.GONE);
                }
            }
        }

        mDesiredHeight = Math.max(lowestYCoord, (int) yOffset);
        if(needsMeasure){
            needsMeasure = false;
            requestLayout();
        }
    }
    private int findBoxesAndReturnLowestObstacleYCoord(){
        int lowestYCoord = 0;
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE)
            {
                Obstacle obstacle = new Obstacle();
                obstacle.topLeftx = child.getLeft();
                obstacle.topLefty = child.getTop();
                obstacle.bottomRightx = obstacle.topLeftx + child.getWidth();
                obstacle.bottomRighty = obstacle.topLefty + child.getHeight();
                obstacles.add(obstacle);
                if(obstacle.bottomRighty > lowestYCoord) lowestYCoord = obstacle.bottomRighty;
            }
        }

        return lowestYCoord;
    }
	private int getChunk(String text, float maxWidth){		
		int length = mTextPaint.breakText(text, true, maxWidth, null);
		if(length<=0) return length; // if its 0 or less, return it, can't fit any chars on this line
		else if(length>=text.length()) return length; // we can fit the whole string in
		else if(text.charAt(length-1) == ' ') return length; // if break char is a space  -- return
		else{
			if(text.length() > length)	if(text.charAt(length) == ' ') return length + 1; // or if the following char is a space then return this length - it is fine
		}		 

		// otherwise, count back until we hit a space and return that as the break length
		int tempLength = length-1;
		while(text.charAt(tempLength)!= ' '){
			tempLength--;
			if(tempLength <=0) return length; // if we count all the way back to 0 then this line cannot be broken, just return the original break length
		}		

		return tempLength+1; // return the nicer break length which doesn't split a word up

	}
	private void paintObject(Canvas canvas, String thisLineStr, float xOffset, float yOffset, Paint paint){
		canvas.drawText(thisLineStr, xOffset, yOffset, paint);
	}

    // MINOR VIEW EVENTS
    @Override
    protected void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        this.invalidate();
    }
    @Override
    public void invalidate() {
        this.needsMeasure = true;
        super.invalidate();
    }
    @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);	

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width;
		int height;

		if (widthMode == MeasureSpec.EXACTLY) {
			// Parent has told us how big to be. So be it.
			width = widthSize;
		} else {	
			width = this.getWidth();		
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			// Parent has told us how big to be. So be it.
			height = heightSize;           
		} else {
			height = mDesiredHeight;
		}

		setMeasuredDimension(width, height + getLineHeight());
	}

    // GETTERS AND SETTERS
    // text size
    public float getTextsize() {
        return mTextsize;
    }
    public void setTextSize(float textSize){
        this.mTextsize = textSize;
        mTextPaint.setTextSize(mTextsize);
        mLinkPaint.setTextSize(mTextsize);
        invalidate();
    }

    // typeface
    public Typeface getTypeFace() {
        return typeFace;
    }
    public void setTypeface(Typeface type){
        this.typeFace = type;
        mTextPaint.setTypeface(typeFace);
        mLinkPaint.setTypeface(typeFace);
        invalidate();
    }

    // text paint
    public TextPaint getTextPaint() {
        return mTextPaint;
    }
    public void setTextPaint(TextPaint mTextPaint) {
        this.mTextPaint = mTextPaint;
        invalidate();
    }

    // link paint
    public TextPaint getLinkPaint() {
        return mLinkPaint;
    }
    public void setLinkPaint(TextPaint mLinkPaint) {
        this.mLinkPaint = mLinkPaint;
        invalidate();
    }

    // text content
    public CharSequence getText() {
        return mText;
    }
    public void setText(CharSequence text){
        mText = text;
        if(text instanceof Spannable){
            mIsHtml = true;
            mSpanParser.setSpannable((Spannable) text);
        }else{
            mIsHtml = false;
        }
        this.invalidate();
    }

    // text colour
    public int getColor() {
        return mColor;
    }
    public void setColor(int color){
        this.mColor = color;

        if(mTextPaint!=null){
            mTextPaint.setColor(mColor);
        }

        mPaintHelper.setColor(mColor);

        this.invalidate();
    }

    // link click listener
    public OnLinkClickListener getOnLinkClickListener() {
        return mClickHandler.getOnLinkClickListener();
    }
    public void setOnLinkClickListener(OnLinkClickListener onLinkClickListener){
        mClickHandler.setOnLinkClickListener(onLinkClickListener);
    }

    // line height
    public int getLineHeight() {
        float mSpacingMult = 1.0f;
        float mSpacingAdd = 0.0f;
        return Math.round(mTextPaint.getFontMetricsInt(null) * mSpacingMult
                + mSpacingAdd);
    }

    // page height
    public void setPageHeight(int pageHeight)
    {
        this.pageHeight = pageHeight;
        invalidate();
    }
}