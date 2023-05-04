package org.itsci.service;

import org.itsci.model.Authority;
import org.itsci.model.Login;
import org.itsci.model.User;

import java.util.List;

public interface UserService {

    User getUser(Long id);

    void saveUser(User user);

    List<User> getUsers();

    void deleteUser(Long id);

    void updateUser(User user, List<Authority> authorityToRemove, List<Authority> authorityToAdd);

    void register(User user);

    User findByUsername(String username);

    Login getLoginById(Long id);
}
