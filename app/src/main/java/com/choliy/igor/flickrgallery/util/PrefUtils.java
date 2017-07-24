package com.choliy.igor.flickrgallery.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.choliy.igor.flickrgallery.FlickrConstants;
import com.choliy.igor.flickrgallery.R;

public final class PrefUtils {

    private PrefUtils() {}

    public static void isFirstStart(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean previouslyStarted = preferences.getBoolean(
                context.getString(R.string.pref_key_first_start), Boolean.FALSE);
        if (!previouslyStarted) {
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean(context.getString(R.string.pref_key_first_start), Boolean.TRUE);
            edit.putString(context.getString(R.string.pref_key_grid), FlickrConstants.DEFAULT_GRID);
            edit.putString(context.getString(R.string.pref_key_style), FlickrConstants.DEFAULT_STYLE);
            edit.putString(context.getString(R.string.pref_key_picture), FlickrConstants.DEFAULT_PICTURE);
            edit.putBoolean(context.getString(R.string.pref_key_animation), FlickrConstants.DEFAULT_ANIMATION);
            edit.putBoolean(context.getString(R.string.pref_key_notification), FlickrConstants.DEFAULT_NOTIFICATION);
            edit.apply();
        }
    }

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.pref_key_search), null);
    }

    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(context.getString(R.string.pref_key_search), query)
                .apply();
    }

    public static String getLastResultId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.pref_key_last_id), null);
    }

    public static void setLastResultId(Context context, String lastResultId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(context.getString(R.string.pref_key_last_id), lastResultId)
                .apply();
    }

    public static String[] getSettings(Context context) {
        String gridValue = getGridSettings(context);
        String styleValue = getStyleSettings(context);
        String pictureValue = getPictureSettings(context);
        boolean animationValue = getAnimationSettings(context);
        boolean notificationValue = getNotificationSettings(context);

        return new String[]{
                gridValue,
                styleValue,
                pictureValue,
                String.valueOf(animationValue),
                String.valueOf(notificationValue)};
    }

    public static String getGridSettings(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(context.getString(R.string.pref_key_grid), null);
    }

    public static String getStyleSettings(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(context.getString(R.string.pref_key_style), null);
    }

    public static String getPictureSettings(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(context.getString(R.string.pref_key_picture), null);
    }

    public static boolean getAnimationSettings(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(context.getString(R.string.pref_key_animation), Boolean.FALSE);
    }

    public static boolean getNotificationSettings(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(context.getString(R.string.pref_key_notification), Boolean.FALSE);
    }
}