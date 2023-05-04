package org.itsci.config;

import org.itsci.utils.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

public class DateToStringConverter implements Converter<Date, String> {
    @Override
    public String convert(Date source) {
        return DateUtils.dateFormat.format(source);
    }
}
