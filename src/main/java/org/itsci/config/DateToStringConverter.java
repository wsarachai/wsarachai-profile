package org.itsci.config;

import org.itsci.utils.DateUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * Converter for transforming {@link Date} objects to their {@link String}
 * representation.
 * Uses the DateUtils utility class for the actual conversion.
 */
public class DateToStringConverter implements Converter<Date, String> {

    /**
     * Converts a Date object to its String representation.
     *
     * @param source the Date to convert, may be null
     * @return the formatted String representation of the date, or null if source is
     *         null
     */
    @Override
    @Nullable
    public String convert(@Nullable Date source) {
        if (source == null) {
            return null;
        }
        return DateUtils.dateToString(source);
    }
}
