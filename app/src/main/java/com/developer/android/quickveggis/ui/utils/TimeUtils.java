package com.developer.android.quickveggis.ui.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    public static boolean isNewProduct(String addedDate, String dateFormat) throws Exception {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        // get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        Long addedMilis = timeStringtoMilis(addedDate, dateFormat);

        if (cal.getTimeInMillis() - addedMilis > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static long timeStringtoMilis(String inputString, String format) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date date = sdf.parse(inputString);
        return date.getTime();
    }
}
