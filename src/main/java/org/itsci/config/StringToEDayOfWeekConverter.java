package org.itsci.config;

import org.itsci.model.EDayOfWeek;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class StringToEDayOfWeekConverter implements Converter<String, EDayOfWeek> {
    
    @Override
    public EDayOfWeek convert(@NonNull String source) {
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        
        try {
            // Try to match by enum name first (MONDAY, TUESDAY, etc.)
            return EDayOfWeek.valueOf(source.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            // If that fails, try to match by the display value (Monday, Tuesday, etc.)
            for (EDayOfWeek day : EDayOfWeek.values()) {
                if (day.toString().equalsIgnoreCase(source.trim())) {
                    return day;
                }
            }
            // If no match found, return null or throw exception
            return null;
        }
    }
}
