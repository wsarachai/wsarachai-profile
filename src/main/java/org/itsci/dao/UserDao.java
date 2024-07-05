package org.itsci.dao;

import org.itsci.model.Login;
import org.itsci.model.User;

import java.util.List;

public interface UserDao<T extends User> {

    List<T> getUsers();

    T updateUser(T user);

    void saveUser(T user);

    T getUser(Long id, Class<?> c);

    void deleteUser(Long id);

    Login getLoginById(Long id);

    T findByUsernameGeneric(String username);
}
