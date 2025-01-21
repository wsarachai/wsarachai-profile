package org.itsci.utils;

import org.itsci.model.Course;
import org.itsci.model.EAttendanceStatus;
import org.itsci.model.EDayOfWeek;
import org.itsci.model.Section;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {
    public static final ZoneId timeZone = ZoneId.of("Asia/Bangkok");
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static long getCurrentWeekSemester(Course course) {
        // Specify the time zone
        long timeTick = course.getStartSemester().getTime();
        Date date = new Date(timeTick);
        LocalDate startDate = date
                .toInstant()
                .atZone(timeZone)
                .toLocalDate();

        ZonedDateTime startDateTime = startDate.atStartOfDay(timeZone);

        LocalDate endDate = LocalDate.now(timeZone);

        int year = endDate.getYear();
        if (year < 2500) {
            year += 543;
        }

        endDate = LocalDate.of(year, endDate.getMonth(), endDate.getDayOfMonth());
        ZonedDateTime endDateTime = endDate.atStartOfDay(timeZone);


        // Calculate the difference in weeks
        return ChronoUnit.WEEKS.between(startDateTime, endDateTime);
    }

    public static Date getDateFrom(int year, int month, int day) {
        // Create a LocalDate
        LocalDate localDate = LocalDate.of(year, month, day);
        // Convert LocalDate to Date
        return Date.from(localDate.atStartOfDay(timeZone).toInstant());
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
        // Get the current date and time in the specified time zone
        ZonedDateTime curCal = ZonedDateTime.now(timeZone);

        // Check if today is the day of the week for the section
        int dayOfWeekNumber = EDayOfWeek.getDayOfWeekNumber(dayOfWeek);

        int curDayOfWeek = curCal.getDayOfWeek().getValue();
        if (curDayOfWeek != dayOfWeekNumber) {
            return false;
        }

        // set the start time for the calendar
        ZonedDateTime startCal = ZonedDateTime.of(
                curCal.getYear(),
                curCal.getMonthValue(),
                curCal.getDayOfMonth(),
                startHour,
                startMinute, 0, 0, timeZone);

        // set the end time for the calendar
        ZonedDateTime endCal = ZonedDateTime.of(
                curCal.getYear(),
                curCal.getMonthValue(),
                curCal.getDayOfMonth(),
                endHour,
                endMinute, 0, 0, timeZone);

        return curCal.isAfter(startCal) && curCal.isBefore(endCal);
    }

    public static EAttendanceStatus checkAttendanceStatus(String startTime, String endTime) {
        String [] times = startTime.split(":");
        int startHour = Integer.parseInt(times[0]);
        int startMinute = Integer.parseInt(times[1]);

        times = endTime.split(":");
        int endHour = Integer.parseInt(times[0]);
        int endMinute = Integer.parseInt(times[1]);

        // Get the current date and time in the specified time zone
        ZonedDateTime curCal = ZonedDateTime.now(timeZone);

        // set the start time for the calendar
        ZonedDateTime startCal = ZonedDateTime.of(
                curCal.getYear(),
                curCal.getMonthValue(),
                curCal.getDayOfMonth(),
                startHour,
                startMinute, 0, 0, timeZone);

        // set the end time for the calendar
        ZonedDateTime endCal = ZonedDateTime.of(
                curCal.getYear(),
                curCal.getMonthValue(),
                curCal.getDayOfMonth(),
                endHour,
                endMinute, 0, 0, timeZone);

        // 30 minutes after start time
        ZonedDateTime lateCal = ZonedDateTime.of(
                curCal.getYear(),
                curCal.getMonthValue(),
                curCal.getDayOfMonth(),
                startHour,
                startMinute, 30, 0, timeZone);

        if (curCal.isAfter(startCal) && curCal.isBefore(lateCal)) {
            return EAttendanceStatus.ATTENDED;
        } else if (curCal.isAfter(lateCal) && curCal.isBefore(endCal)) {
            return EAttendanceStatus.LATE;
        }

        return EAttendanceStatus.NA;
    }

    public static int getCurrentSemesterYearNumber() {
        // Get the current date and time in the specified time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.now(timeZone);

        int curMonth = zonedDateTime.getMonthValue();
        int curYear = zonedDateTime.getYear();
        int year = curYear;

        if (curYear < 2500) {
            curYear += 543;
            year = curYear;
        }

        // Term 1: Month between 7 - 10
        // Term 2: Month between 11 - 12, Month between 1 - 3
        // Summer 3 - 4

        if (curMonth <= 6) {
            year = curYear - 1;
        }

        return year;
    }

    public static String getCurrentSemesterYear() {
        return String.valueOf(getCurrentSemesterYearNumber());
    }


    public static String getCurrentSemesterTerm() {
        // Get the current date and time in the specified time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.now(timeZone);

        // Get the current month as an integer (1 = January, 12 = December)
        int curMonth = zonedDateTime.getMonthValue();

        int term = 1;

        // Term 1: 7 - 11
        // Term 2: 11 - 12, 1 - 3
        // Summer 3 - 4

        if (curMonth >= 11 || curMonth <= 3) {
            term = 2;
        } else if (curMonth < 7) {
            term = 3;
        }

        return String.valueOf(term);
    }

    public static String dateToString(Date date) {
        LocalDate localDate = date.toInstant()
                .atZone(timeZone)
                .toLocalDate();
        return localDate.format(dateFormat);
    }

    public static Date stringToDate(String dateString) {
        // Parse the date strings into LocalDate objects
        LocalDate localDate = LocalDate.parse(dateString, dateFormat);
        // Convert LocalDate to Date
        return Date.from(localDate.atStartOfDay(timeZone).toInstant());
    }
}
