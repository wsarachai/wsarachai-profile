package org.itsci.dao;

import org.itsci.model.Login;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginDao {
    UserDetails findByUsername(String username);

    void saveOrUpdate(Login login);
}
