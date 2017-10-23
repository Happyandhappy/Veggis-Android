package com.developer.android.quickveggis.ui.utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.developer.android.quickveggis.App;


public class ConvertDpPx {

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = App.getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(int px) {
        DisplayMetrics displayMetrics = App.getContext().getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
