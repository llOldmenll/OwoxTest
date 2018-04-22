package com.oldmen.owoxtest.data.repositories;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;

public class SharedPrefHelper {
    private static String CURRENT_POSITION_KEY = "CURRENT_POSITION_KEY";
    private static String CURRENT_PAGE_KEY = "CURRENT_PAGE_KEY";
    private static String PAGES_NUMBER_KEY = "PAGES_NUMBER_KEY";
    private static String CURRENT_SEARCH_QUERY_KEY = "CURRENT_SEARCH_QUERY_KEY";
    private static String FOOTER_STATE_KEY = "FOOTER_STATE_KEY";
    private static String USER_KEY = "UserSession";

    public static int getCurrentPosition(WeakReference<Context> contextRef) {
        SharedPreferences sharedPreferences = contextRef.get()
                .getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(CURRENT_POSITION_KEY, 0);
    }

    public static void saveCurrentPosition(int currentImgPosition, WeakReference<Context> contextRef) {
        SharedPreferences sharedPreferences = contextRef.get()
                .getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(CURRENT_POSITION_KEY, currentImgPosition).apply();
    }

    public static int getCurrentPage(WeakReference<Context> contextRef) {
        SharedPreferences sharedPreferences = contextRef.get()
                .getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(CURRENT_PAGE_KEY, 0);
    }

    public static void saveCurrentPage(int currentPage, WeakReference<Context> contextRef) {
        SharedPreferences sharedPreferences = contextRef.get()
                .getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(CURRENT_PAGE_KEY, currentPage).apply();
    }

    public static int getPagesNumber(WeakReference<Context> contextRef) {
        SharedPreferences sharedPreferences = contextRef.get()
                .getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PAGES_NUMBER_KEY, 1);
    }

    public static void savePagesNumber(int pagesNumber, WeakReference<Context> contextRef) {
        SharedPreferences sharedPreferences = contextRef.get()
                .getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(PAGES_NUMBER_KEY, pagesNumber).apply();
    }

    public static String getSearchQuery(WeakReference<Context> contextRef) {
        SharedPreferences sharedPreferences = contextRef.get()
                .getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CURRENT_SEARCH_QUERY_KEY, "");
    }

    public static void saveSearchQuery(String searchQuery, WeakReference<Context> contextRef) {
        SharedPreferences sharedPreferences = contextRef.get()
                .getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(CURRENT_SEARCH_QUERY_KEY, searchQuery).apply();
    }

    public static boolean getFooterState(WeakReference<Context> contextRef) {
        SharedPreferences sharedPreferences = contextRef.get()
                .getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(FOOTER_STATE_KEY, true);
    }

    public static void saveFooterState(boolean footerState, WeakReference<Context> contextRef) {
        SharedPreferences sharedPreferences = contextRef.get()
                .getSharedPreferences(USER_KEY, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(FOOTER_STATE_KEY, footerState).apply();
    }

    public static void removeAll(WeakReference<Context> contextRef) {
        contextRef.get().getSharedPreferences(USER_KEY, Context.MODE_PRIVATE).edit().clear().apply();
    }

}
