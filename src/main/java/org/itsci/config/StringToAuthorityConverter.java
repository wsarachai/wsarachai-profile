package org.itsci.config;

import org.itsci.model.Authority;
import org.itsci.model.EAuthorityType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class StringToAuthorityConverter implements Converter<String, Authority> {
    @Override
    @Nullable
    public Authority convert(@NonNull String source) {
        EAuthorityType at = EAuthorityType.valueOf(source);
        return new Authority(at);
    }
}
