package org.itsci.config;

import org.itsci.utils.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

public class StringToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        return DateUtils.stringToDate(source);
    }
}
