package org.itsci.model;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.*;

public enum EAttendanceStatus {
    NA("0"),
    ABSENT("1"),
    LATE("2"),
    LETTERS("3"),
    ATTENDED("4");

    private static List<String> attendances = null;
    private final String attendance;

    EAttendanceStatus(String attendance) {
        this.attendance = attendance;
    }

    public static List<String> getAttendances() {
        if (attendances == null) {
            attendances = new ArrayList<>();
            for (EAttendanceStatus val : EAttendanceStatus.values()) {
                attendances.add(val.attendance);
            }
        }
        return attendances;
    }

    public static Object getAuthorityOptions(ResourceBundleMessageSource messageSource, Locale locale) {
        List<String> attendances = getAttendances();
        Map<String, String> attendanceOptions = new HashMap<>();
        for (String attendance : attendances) {
            String label = messageSource.getMessage("page.student.atten." + attendance, null, locale);
            attendanceOptions.put(attendance, label);
        }
        return attendanceOptions;
    }

    @Override
    public String toString() {
        return attendance;
    }
}
