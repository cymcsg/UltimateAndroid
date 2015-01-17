package com.marshalchen.common.commonUtils.fileUtils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Put or get data from preference.
 * You can change the default preference name using {@link #setPreferenceName(String)}
 */
public class PreferencesUtils {

    public static String getPreferenceName() {
        return preferenceName;
    }

    /**
     * Set the default preference name.
     *
     * @param preferenceName
     */
    public static void setPreferenceName(String preferenceName) {
        PreferencesUtils.preferenceName = preferenceName;
    }

    private static String preferenceName = "UltimateAndroid";

    /**
     * Put string to preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public static boolean putString(Context context, String key, String value) {
        return putString(context, preferenceName, key, value);
    }

    /**
     * Put string to preferences with custom preference
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to modify
     * @param value          The new value for the preference
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public static boolean putString(Context context, String preferenceName, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * Get string from preferences with "" as default value
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a String.
     */
    public static String getString(Context context, String key) {
        return getString(context, preferenceName, key);
    }

    /**
     * Get string from custom preferences with "" as default value
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to retrieve
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a String.
     */
    public static String getString(Context context, String preferenceName, String key) {
        return getString(context, preferenceName, key, "");
    }

    /**
     * Get string from custom preferences with custom default value
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to retrieve
     * @param defaultValue   Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a String.
     */
    public static String getString(Context context, String preferenceName, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * Put int to preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(Context context, String key, int value) {
        return putInt(context, preferenceName, key, value);
    }


    /**
     * Put int to preferences with custom preference
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to modify
     * @param value          The new value for the preference
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(Context context, String preferenceName, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * Get int from preferences with 0 as default value
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not an int.
     */
    public static int getInt(Context context, String key) {
        return getInt(context, preferenceName, key);
    }

    /**
     * Get int from custom preferences with 0 as default value
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to retrieve
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not an int.
     */
    public static int getInt(Context context, String preferenceName, String key) {
        return getInt(context, preferenceName, key, 0);
    }


    /**
     * Get int from custom preferences with custom default value
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to retrieve
     * @param defaultValue   Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not an int.
     */
    public static int getInt(Context context, String preferenceName, String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * Put long to preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(Context context, String key, long value) {
        return putLong(context, preferenceName, key, value);
    }


    /**
     * Put long to preferences with custom preference
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to modify
     * @param value          The new value for the preference
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(Context context, String preferenceName, String key, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or 0. Throws ClassCastException if there is a preference with this
     * name that is not a long
     */
    public static long getLong(Context context, String key) {
        return getLong(context, preferenceName, key);
    }

    /**
     * Get long from custom preferences with 0 as default value
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to retrieve
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not an int.
     */
    public static long getLong(Context context, String preferenceName, String key) {
        return getLong(context, preferenceName, key, 0);
    }


    /**
     * Get long from custom preferences with custom default value
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to retrieve
     * @param defaultValue   Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a long.
     */
    public static long getLong(Context context, String preferenceName, String key, long defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * Put float to preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(Context context, String key, float value) {

        return putFloat(context, preferenceName, key, value);
    }

    /**
     * Put float to preferences with custom preference
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to modify
     * @param value          The new value for the preference
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(Context context, String preferenceName, String key, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * Get float from preferences with 0 as default value
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not an int.
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, preferenceName, key);
    }

    /**
     * Get int from custom preferences with 0 as default value
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to retrieve
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not an int.
     */
    public static float getFloat(Context context, String preferenceName, String key) {
        return getFloat(context, preferenceName, key, 0);
    }


    /**
     * Get float from custom preferences with custom default value
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to retrieve
     * @param defaultValue   Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a float.
     */
    public static float getFloat(Context context, String preferenceName, String key, float defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defaultValue);
    }

    /**
     * Put boolean to preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(Context context, String key, boolean value) {

        return putBoolean(context, preferenceName, key, value);
    }

    /**
     * Put boolean to preferences with custom preference
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to modify
     * @param value          The new value for the preference
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(Context context, String preferenceName, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * Get boolean from preferences with false as default value
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a boolean.
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, preferenceName, key);
    }

    /**
     * Get boolean from custom preferences with false as default value
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to retrieve
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a boolean.
     */
    public static boolean getBoolean(Context context, String preferenceName, String key) {
        return getBoolean(context, preferenceName, key, false);
    }

    /**
     * Get boolean from custom preferences with custom default value
     *
     * @param context
     * @param preferenceName The custom preference name
     * @param key            The name of the preference to retrieve
     * @param defaultValue   Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a boolean.
     */
    public static boolean getBoolean(Context context, String preferenceName, String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void clearSharePreference(Context context) {
        clearSharePreference(context, preferenceName);
    }

    public static void clearSharePreference(Context context, String preferenceName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
