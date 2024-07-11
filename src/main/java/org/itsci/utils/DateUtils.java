package org.itsci.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static Calendar calendarFor(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal;
    }

    public static Date getDateFrom(int year, int month, int day) {
        return calendarFor(year, month, day).getTime();
    }
}
