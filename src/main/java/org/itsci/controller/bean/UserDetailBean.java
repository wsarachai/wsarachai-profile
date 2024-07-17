package org.itsci.controller.bean;

import org.itsci.model.Login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailBean implements UserDetails  {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities = new ArrayList<>();

    public UserDetailBean(Login login) {
        this.username = login.getUsername();
        this.password = login.getPassword();
        this.authorities = new ArrayList<>(login.getAuthorities());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
