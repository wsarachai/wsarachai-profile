package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class SectionDaoImpl implements SectionDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Section findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Section courseSection = session.get(Section.class, id);
        return courseSection;
    }

    @Override
    public void save(Section section) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(section);
    }

    @Override
    public List<Section> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Section> criteria = builder.createQuery(Section.class);
        Root<Section> root = criteria.from(Section.class);
        criteria.select(root);
        Query<Section> query = session.createQuery(criteria);
        return query.getResultList();
    }
}
