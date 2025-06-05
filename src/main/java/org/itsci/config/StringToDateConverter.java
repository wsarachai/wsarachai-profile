package org.itsci.config;

import org.itsci.utils.DateUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import java.util.Date;

public class StringToDateConverter implements Converter<String, Date> {
    @Override
    @Nullable
    public Date convert(@NonNull String source) {
        return DateUtils.stringToDate(source);
    }
}
