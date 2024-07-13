package org.itsci.utils;

import org.itsci.model.Course;
import org.itsci.model.EAttendanceStatus;
import org.itsci.model.EDayOfWeek;
import org.itsci.model.Section;

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

    public static boolean isInTimeForLecAttend(Section section) {
        String [] times = section.getStartLectureTime().split(":");
        int startHour = Integer.parseInt(times[0]);
        int startMinute = Integer.parseInt(times[1]);

        times = section.getEndLectureTime().split(":");
        int endHour = Integer.parseInt(times[0]);
        int endMinute = Integer.parseInt(times[1]);

        return checkForInTime(startHour, startMinute, endHour, endMinute, section.getLecDay());
    }

    public static boolean isInTimeForLabAttend(Section section) {
        String [] times = section.getStartLabTime().split(":");
        int startHour = Integer.parseInt(times[0]);
        int startMinute = Integer.parseInt(times[1]);

        times = section.getEndLabTime().split(":");
        int endHour = Integer.parseInt(times[0]);
        int endMinute = Integer.parseInt(times[1]);

        return checkForInTime(startHour, startMinute, endHour, endMinute, section.getLabDay());
    }

    private static boolean checkForInTime(int startHour, int startMinute, int endHour, int endMinute, EDayOfWeek dayOfWeek) {
        Calendar curCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));

        // Check if today is the day of the week for the section
        int dayOfWeekNumber = EDayOfWeek.getDayOfWeekNumber(dayOfWeek);
        int curDayOfWeek = curCal.get(Calendar.DAY_OF_WEEK);
        if (curDayOfWeek != dayOfWeekNumber) {
            return false;
        }

        Calendar startCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        Calendar endCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));

        startCal.set(Calendar.HOUR_OF_DAY, startHour);
        startCal.set(Calendar.MINUTE, startMinute);

        endCal.set(Calendar.HOUR_OF_DAY, endHour);
        endCal.set(Calendar.MINUTE, endMinute);

        return curCal.after(startCal) && curCal.before(endCal);
    }

    public static EAttendanceStatus checkAttendanceStatus(String startTime, String endTime) {
        String [] times = startTime.split(":");
        int startHour = Integer.parseInt(times[0]);
        int startMinute = Integer.parseInt(times[1]);

        times = endTime.split(":");
        int endHour = Integer.parseInt(times[0]);
        int endMinute = Integer.parseInt(times[1]);

        Calendar curCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        Calendar startCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        Calendar endCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        Calendar lateCal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));

        // set the start time for the calendar
        startCal.set(Calendar.HOUR_OF_DAY, startHour);
        startCal.set(Calendar.MINUTE, startMinute);

        // set the end time for the calendar
        endCal.set(Calendar.HOUR_OF_DAY, endHour);
        endCal.set(Calendar.MINUTE, endMinute);

        // 30 minutes after start time
        lateCal.set(Calendar.HOUR_OF_DAY, startHour);
        lateCal.set(Calendar.MINUTE, startMinute);
        lateCal.add(Calendar.MINUTE, 30);

        if (curCal.after(startCal) && curCal.before(lateCal)) {
            return EAttendanceStatus.ATTENDED;
        } else if (curCal.after(lateCal) && curCal.before(endCal)) {
            return EAttendanceStatus.LATE;
        }

        return EAttendanceStatus.NA;
    }
}
