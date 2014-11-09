/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.demoofui.standUpTimer;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import com.marshalchen.common.demoofui.R;

public class Prefs extends PreferenceActivity {
    private static final String SOUNDS = "sounds";
    private static final boolean SOUNDS_DEFAULT = true;
    private static final String WARNING_TIME = "warning_time";
    private static final int WARNING_TIME_DEFAULT = 15;
    private static final String UNLIMITED_PARTICIPANTS = "unlimited_participants";
    private static final boolean UNLIMITED_PARTICIPANTS_DEFAULT = false;
    private static final String VARIABLE_MEETING_LENGTH = "variable_meeting_length";
    private static final boolean VARIABLE_MEETING_LENGTH_DEFAULT = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.stand_up_timer_settings);
    }

    public static boolean playSounds(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SOUNDS, SOUNDS_DEFAULT);
    }

    public static void setPlaySounds(Context context, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(SOUNDS, value).commit();
    }

    public static int getWarningTime(Context context) {
        String value = PreferenceManager.getDefaultSharedPreferences(context).getString(WARNING_TIME, Integer.toString(WARNING_TIME_DEFAULT));
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            setWarningTime(context, WARNING_TIME_DEFAULT);
            return WARNING_TIME_DEFAULT;
        }
    }

    public static void setWarningTime(Context context, int warningTime) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(WARNING_TIME, Integer.toString(warningTime)).commit();
    }

    public static boolean allowUnlimitedParticipants(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(UNLIMITED_PARTICIPANTS, UNLIMITED_PARTICIPANTS_DEFAULT);
    }

    public static void setAllowUnlimitedParticipants(Context context, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(UNLIMITED_PARTICIPANTS, value).commit();
    }

    public static boolean allowVariableMeetingLength(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(VARIABLE_MEETING_LENGTH, VARIABLE_MEETING_LENGTH_DEFAULT);
    }

    public static void setAllowVariableMeetingLength(Context context, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(VARIABLE_MEETING_LENGTH, value).commit();
    }
}
