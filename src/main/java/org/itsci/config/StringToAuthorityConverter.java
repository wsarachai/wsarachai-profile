package org.itsci.config;

import org.itsci.model.Authority;
import org.itsci.model.EAuthorityType;
import org.springframework.core.convert.converter.Converter;

public class StringToAuthorityConverter implements Converter<String, Authority> {
    @Override
    public Authority convert(String source) {
        EAuthorityType at = EAuthorityType.valueOf(source);
        return new Authority(at);
    }
}
