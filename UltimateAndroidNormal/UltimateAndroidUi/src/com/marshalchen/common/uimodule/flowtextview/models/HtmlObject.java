package com.marshalchen.common.uimodule.flowtextview.models;

import android.text.TextPaint;

/**
* Created by Dean on 24/06/2014.
*/
public class HtmlObject {

    public HtmlObject(String content, int start, int end, float xOffset,
                      TextPaint paint) {
        super();
        this.content = content;
        this.start = start;
        this.end = end;
        this.xOffset = xOffset;
        this.paint = paint;
    }
    public String content;
    public int start;
    public int end;
    public float xOffset;
    public TextPaint paint;
    public boolean recycle = false;
}
