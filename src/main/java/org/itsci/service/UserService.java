package org.itsci.service;

import org.itsci.model.Authority;
import org.itsci.model.Login;
import org.itsci.model.User;

import java.util.List;

public interface UserService<T> {

    T getUser(Long id, Class<?> c);

    T updateUser(T user);

    void saveUser(T user);

    List<T> getUsers();

    void deleteUser(Long id);

    void updateUser(T user, List<Authority> authorityToRemove, List<Authority> authorityToAdd);

    void register(T user);

    T findByUsernameGeneric(String username);

    Login getLoginById(Long id);
}
