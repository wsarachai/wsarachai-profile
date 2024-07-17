package org.itsci.model;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "logins")
public class Login implements UserDetails {
    @Id
    @Column(name="username", length = 50, nullable = false, unique = true)
    private String username;
    @Column(name="password", nullable = false)
    private String password;
    @Column(columnDefinition = "TINYINT(1)")
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "login_authority",
            joinColumns= { @JoinColumn(name = "login_id")},
            inverseJoinColumns= { @JoinColumn(name = "authority_id")})
    private Set<Authority> authorities = new HashSet<>();

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
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

    public boolean isEnabled() {
        return enabled;
    }
}
