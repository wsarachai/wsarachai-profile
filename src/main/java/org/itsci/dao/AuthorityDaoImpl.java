package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Authority;
import org.itsci.model.EAuthorityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    public Authority findByRole(EAuthorityType role) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Authority> criteria = builder.createQuery(Authority.class);
        Root<Authority> root = criteria.from(Authority.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("roleName"), role));

        Query<Authority> query = session.createQuery(criteria);
        return query.getSingleResult();
    }

    @Override
    public Set<Authority> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Authority> criteria = builder.createQuery(Authority.class);
        Root<Authority> root = criteria.from(Authority.class);
        criteria.select(root);

        Query<Authority> query = session.createQuery(criteria);
        List<Authority> _authorities = query.getResultList();

        return new TreeSet<>(_authorities);
    }

    @Override
    public void save(Authority authority) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(authority);
    }
}
