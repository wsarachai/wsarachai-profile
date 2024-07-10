package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Curriculum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CurriculumDaoImpl implements CurriculumDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Curriculum getCurriculumById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Curriculum curriculum = session.get(Curriculum.class, id);
        return curriculum;
    }

    @Override
    public List<Curriculum> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Curriculum> criteria = builder.createQuery(Curriculum.class);
        Root<Curriculum> root = criteria.from(Curriculum.class);
        criteria.select(root);
        Query<Curriculum> query = session.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public void save(Curriculum curriculum1) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(curriculum1);
    }
}
