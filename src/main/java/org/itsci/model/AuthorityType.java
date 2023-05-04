package org.itsci.model;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.*;

public enum AuthorityType {
    ROLE_MEMBER("ROLE_MEMBER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private static List<String> authorities = null;
    private final String role;

    AuthorityType(String role) {
        this.role = role;
    }

    public static List<String> getAuthorities() {
        if (authorities == null) {
            authorities = new ArrayList<>();
            for (AuthorityType val : AuthorityType.values()) {
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
