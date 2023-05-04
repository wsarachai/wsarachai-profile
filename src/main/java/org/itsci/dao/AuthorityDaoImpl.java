package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorityDaoImpl implements AuthorityDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Authority where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Authority findByAuthority(String authority) {
        Session session = sessionFactory.getCurrentSession();
        Query<Authority> query = session.createQuery("from Authority a where a.authority=:authority", Authority.class);
        query.setParameter("authority", authority);
        Authority result = query.getSingleResult();
        return result;
    }
}
