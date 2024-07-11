package org.itsci.dao;

import org.itsci.model.Login;
import org.itsci.model.User;

import java.util.List;

public interface UserDao<T extends User> {

    T update(T user);

    void save(T user);

    T getById(Long id, Class<?> c);

    void delete(Long id);

    Login getLoginById(Long id);

    T findByUsernameGeneric(String username);

    List<T> findAll();

    User findByUsername(String username);
}
