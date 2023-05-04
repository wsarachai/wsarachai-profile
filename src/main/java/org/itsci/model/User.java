package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    private String firstName;
    private String lastName;
    @Column(columnDefinition="TEXT")
    private String address;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "login_id")
    private Login login;

    public User() {
        login = new Login();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getLogin().getAuthorities();
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.getLogin().setAuthorities(authorities);
    }

    @Override
    public String getPassword() {
        return this.getLogin().getPassword();
    }

    public void setPassword(String password) {
        this.login.setPassword(password);
    }

    public String getConfirmPassword() {
        return this.login.getConfirmPassword();
    }

    public void setConfirmPassword(String confirmPassword) {
        this.login.setConfirmPassword(confirmPassword);
    }

    @Override
    public String getUsername() {
        return this.getLogin().getUsername();
    }

    public void setUsername(String username) {
        this.getLogin().setUsername(username);
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
