package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.itsci.controller.bean.UserDetailBean;
import org.itsci.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDaoImpl implements LoginDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public UserDetails findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Login login = session.get(Login.class, username);
        UserDetailBean userDetailBean = new UserDetailBean(login);
        return userDetailBean;
    }

    @Override
    public void saveOrUpdate(Login login) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(login);
    }
}
