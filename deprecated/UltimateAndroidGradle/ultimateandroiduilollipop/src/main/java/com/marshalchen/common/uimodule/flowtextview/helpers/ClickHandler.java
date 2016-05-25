package com.marshalchen.common.uimodule.flowtextview.helpers;

import android.view.MotionEvent;
import android.view.View;

import com.marshalchen.common.uimodule.flowtextview.listeners.OnLinkClickListener;
import com.marshalchen.common.uimodule.flowtextview.models.HtmlLink;

import java.util.ArrayList;


/**
 * Created by Dean on 24/06/2014.
 */
public class ClickHandler implements View.OnTouchListener{

    private final SpanParser mSpanParser;
    private OnLinkClickListener mOnLinkClickListener;

    private double distance = 0;
    private float x1,y1,x2,y2 = 0f;

    public ClickHandler(SpanParser spanParser) {
        this.mSpanParser = spanParser;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        int event_code = event.getAction();

        if(event_code == MotionEvent.ACTION_DOWN){
            distance = 0;
            x1 = event.getX();
            y1 = event.getY();
        }

        if(event_code == MotionEvent.ACTION_MOVE){
            x2 = event.getX();
            y2 = event.getY();
            distance = getPointDistance(x1, y1, x2, y2);
        }

        if(distance < 10){

            if(event_code == MotionEvent.ACTION_UP){
                onClick(event.getX(), event.getY());
            }

            return true;
        }else{
            return false;
        }
    }

    private void onClick(float x, float y){

        ArrayList<HtmlLink> links = mSpanParser.getLinks();

        for (HtmlLink link : links) {
            float tlX = link.xOffset;
            float tlY = link.yOffset;
            float brX = link.xOffset + link.width;
            float brY = link.yOffset + link.height;

            if(x > tlX && x < brX){
                if(y > tlY && y < brY){
                    // collision
                    onLinkClick(link.url);
                    return;
                }
            }
        }
    }

    private void onLinkClick(String url){
        if(mOnLinkClickListener!=null) mOnLinkClickListener.onLinkClick(url);
    }

    private static double getPointDistance(float x1, float y1, float x2, float y2){
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1- y2, 2));
    }

    public OnLinkClickListener getOnLinkClickListener() {
        return mOnLinkClickListener;
    }

    public void setOnLinkClickListener(OnLinkClickListener mOnLinkClickListener) {
        this.mOnLinkClickListener = mOnLinkClickListener;
    }
}
