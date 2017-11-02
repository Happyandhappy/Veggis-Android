package com.developer.android.quickveggis.ui.utils;


import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    public static boolean isNewProduct(String addedDate, String dateFormat) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date strDate = sdf.parse(addedDate);
        return !(new Date().after(strDate));
  }

    public static long timeStringtoMilis(String inputString, String format) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date date = sdf.parse(inputString);
        return date.getTime();
    }
}
