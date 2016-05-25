/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.usefulModule;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;

/**
 * Created by cym on 14-8-2.
 */
public abstract class WatchFaceActivity extends Activity
    //    implements DisplayManager.DisplayListener
{

    /**
     * Used to detect when the watch is dimming.<br/>
     * Remember to make gray-scale versions of your watch face so they won't burn
     * and drain battery unnecessarily.
     */
    public abstract void onScreenDim();

    /**
     * Used to detect when the watch is not in a dimmed state.<br/>
     * This does not handle when returning "home" from a different activity/application.
     */
    public abstract void onScreenAwake();

    /**
     * Used to detect when a watch face is being removed (switched).<br/>
     * You can either do what you need here, or simply override {@code onDestroy()}.
     */
    public void onWatchFaceRemoved(){}

    /**
     * When the screen is OFF due to "Always-On" being disabled.
     */
    public void onScreenOff(){}

    private DisplayManager displayManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Set up the display manager and register a listener (this activity).
        displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
     //   displayManager.registerDisplayListener(this, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  Unregister the listener. If you don't, even after the watch face is gone,
        //  it will still accept your callbacks.
      //  displayManager.unregisterDisplayListener(this);
    }

//    @Override
//    public void onDisplayRemoved(int displayId) {
//        onWatchFaceRemoved();
//    }
//
//    @Override
//    public void onDisplayAdded(int displayId) {
//        //  In testing, this was never called, so the callback for this was removed.
//    }
//
//
//
//    // ADD in API 20
//    @Override
//    public void onDisplayChanged(int displayId) {
////        switch(displayManager.getDisplay(displayId).getState()){
////            case Display.STATE_DOZING:
////                onScreenDim();
////                break;
////            case Display.STATE_OFF:
////                onScreenOff();
////                break;
////            default:
////                //  Not really sure what to so about Display.STATE_UNKNOWN, so
////                //  we'll treat it as if the screen is normal.
////                onScreenAwake();
////                break;
////        }
//    }
}