package org.itsci.utils;

import org.itsci.model.Course;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static Calendar calendarFor(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal;
    }

    public static int getCurrentWeekSemester(Course course) {
        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        Calendar c2 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        c1.setTime(course.getStartSemester());

        int startWeek = c1.get(Calendar.WEEK_OF_YEAR);
        int curWeek = c2.get(Calendar.WEEK_OF_YEAR);

        return curWeek-startWeek;
    }

    public static Date getDateFrom(int year, int month, int day) {
        return calendarFor(year, month, day).getTime();
    }
}
