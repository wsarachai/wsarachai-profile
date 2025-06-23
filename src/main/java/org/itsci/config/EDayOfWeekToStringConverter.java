package org.itsci.config;

import org.itsci.model.EDayOfWeek;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class EDayOfWeekToStringConverter implements Converter<EDayOfWeek, String> {
    
    @Override
    public String convert(@NonNull EDayOfWeek source) {
        return source.name(); // Returns the enum name (MONDAY, TUESDAY, etc.)
    }
}
