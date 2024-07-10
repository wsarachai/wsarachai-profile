package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Repository
public class SubjectDaoImpl implements SubjectDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Subject> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> criteria = builder.createQuery(Subject.class);
        Root<Subject> root = criteria.from(Subject.class);
        criteria.select(root);

        Query<Subject> query = session.createQuery(criteria);
        List<Subject> subjects = query.getResultList();

        return subjects;
    }

    @Override
    public void save(Subject subject) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(subject);
    }

    @Override
    public Subject getSubjectById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Subject subject = session.get(Subject.class, id);
        return subject;
    }
}
