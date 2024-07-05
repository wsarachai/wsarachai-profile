package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Login;
import org.itsci.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.List;

@Repository
public class UserDaoImpl<T extends User> implements UserDao<T> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<T> getUsers() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);

        Query<User> query = session.createQuery(criteria);
        List<T> users = (List<T>) query.getResultList();

        return users;
    }

    @Override
    public T updateUser(T user) {
        Session session = sessionFactory.getCurrentSession();
        user = (T) session.merge(user);
        return user;
    }

    @Override
    public void saveUser(T user) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }

    @Override
    public T getUser(Long id, Class<?> c) {
        Session session = sessionFactory.getCurrentSession();
        T user = (T) session.get(c, id);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.load(User.class, id);
        session.delete(user);
        session.flush();
    }

    @Override
    public Login getLoginById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.load(Login.class, id);
    }

    @Override
    public T findByUsernameGeneric(String username) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<?> root = criteria.from(User.class);
        criteria.select((Selection<? extends User>) root);
        criteria.where(builder.equal(root.get("login.username"), username));

        Query<User> query = session.createQuery(criteria);
        User result = query.getSingleResult();
        return (T) result;
    }
}
