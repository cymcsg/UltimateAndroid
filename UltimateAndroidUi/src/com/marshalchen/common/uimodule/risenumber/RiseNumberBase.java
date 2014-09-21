/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.uimodule.risenumber;

/**
 * Created by lee on 2014/7/29.
 */
public interface RiseNumberBase {
    public void start();
    public RiseNumberTextView withNumber(float number);
    public RiseNumberTextView withNumber(int number);
    public RiseNumberTextView setDuration(long duration);
    public void setOnEnd(RiseNumberTextView.EndListener callback);
}
