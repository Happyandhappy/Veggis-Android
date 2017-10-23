package com.developer.android.quickveggis.ui.utils;

public class PriceFormat {
    public static String format(float price) {
        return String.format("%.2f\u0930", new Object[]{Float.valueOf(price)});
    }

    public static String format(double price) {
        return String.format("%.2f\u0930", new Object[]{Double.valueOf(price)});
    }
}
