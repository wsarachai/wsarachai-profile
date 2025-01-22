package org.itsci.model;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.*;

public enum EDayOfWeek {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private static List<String> dayOfWeeks = null;
    private final String dayOfWeek;

    EDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public static List<String> getDayOfWeeks() {
        if (dayOfWeeks == null) {
            dayOfWeeks = new ArrayList<>();
            for (EDayOfWeek val : EDayOfWeek.values()) {
                dayOfWeeks.add(val.dayOfWeek);
            }
        }
        return dayOfWeeks;
    }

    public static int getDayOfWeekNumber(EDayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return 1;
            case TUESDAY:
                return 2;
            case WEDNESDAY:
                return 3;
            case THURSDAY:
                return 4;
            case FRIDAY:
                return 5;
            case SATURDAY:
                return 6;
            case SUNDAY:
                return 7;
            default:
                return 0;
        }
    }

    public static Object getAuthorityOptions(ResourceBundleMessageSource messageSource, Locale locale) {
        List<String> dayOfWeeks = getDayOfWeeks();
        Map<String, String> dayOfWeekOptions = new HashMap<>();
        for (String dayOfWeek : dayOfWeeks) {
            String label = messageSource.getMessage("day." + dayOfWeek, null, locale);
            dayOfWeekOptions.put(dayOfWeek, label);
        }
        return dayOfWeekOptions;
    }

    @Override
    public String toString() {
        return dayOfWeek;
    }
}
