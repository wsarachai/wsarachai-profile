package org.itsci.dao;

import org.itsci.model.Login;
import org.itsci.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User updateUser(User user);

    void saveUser(User user);

    User getUser(Long id);

    void deleteUser(Long id);

    Login getLoginById(Long id);

    User findByUsername(String username);
}
