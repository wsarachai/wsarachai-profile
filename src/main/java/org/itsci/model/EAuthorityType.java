package org.itsci.model;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.*;

public enum EAuthorityType {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_TEACHER("ROLE_TEACHER"),
    ROLE_STAFF("ROLE_STAFF"),
    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_MEMBER("ROLE_MEMBER"),
    ROLE_USER("ROLE_USER");

    private static List<String> authorities = null;
    private final String role;

    EAuthorityType(String role) {
        this.role = role;
    }

    public static List<String> getAuthorities() {
        if (authorities == null) {
            authorities = new ArrayList<>();
            for (EAuthorityType val : EAuthorityType.values()) {
                authorities.add(val.role);
            }
        }
        return authorities;
    }

    public static Object getAuthorityOptions(ResourceBundleMessageSource messageSource, Locale locale) {
        List<String> authorities = getAuthorities();
        Map<String, String> authorityOptions = new HashMap<>();
        for (String authority : authorities) {
            String label = messageSource.getMessage("enum.AuthorityType." + authority, null, locale);
            authorityOptions.put(authority, label);
        }
        return authorityOptions;
    }

    @Override
    public String toString() {
        return role;
    }
}
