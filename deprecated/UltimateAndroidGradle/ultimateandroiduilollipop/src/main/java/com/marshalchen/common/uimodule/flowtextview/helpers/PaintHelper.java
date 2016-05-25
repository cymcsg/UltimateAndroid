package com.marshalchen.common.uimodule.flowtextview.helpers;

import android.graphics.Paint;
import android.text.TextPaint;

import java.util.ArrayList;

/**
 * Created by Dean on 24/06/2014.
 */
public class PaintHelper {
    private ArrayList<TextPaint> mPaintHeap = new ArrayList<TextPaint>();

    public TextPaint getPaintFromHeap(){
        if(mPaintHeap.size()>0){
            return mPaintHeap.remove(0);
        }else{
            return new TextPaint(Paint.ANTI_ALIAS_FLAG);
        }
    }

    public void setColor(int color){
        for (TextPaint paint : mPaintHeap) {
            paint.setColor(color);
        }
    }

    public void recyclePaint(TextPaint paint){
        mPaintHeap.add(paint);
    }
}
