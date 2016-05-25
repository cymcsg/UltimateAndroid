/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.uimodule.standuptimer.utils;

import android.graphics.Color;

public abstract class TimeFormatHelper {

    public static int determineColor(int seconds, int warningTime) {
        if (seconds <= 0) {
            return Color.RED;
        } else if (seconds <= warningTime) {
            return Color.YELLOW;
        } else {
            return Color.GREEN;
        }
    }
    
    public static String formatTime(int seconds) {
        return Integer.toString(seconds / 60) + ":" + padWithZeros(seconds % 60);
    }

    public static String formatTime(float seconds) {
        return formatTime((int) seconds);
    }

    private static String padWithZeros(int seconds) {
        return seconds < 10 ? "0" + seconds : Integer.toString(seconds);
    }
}
