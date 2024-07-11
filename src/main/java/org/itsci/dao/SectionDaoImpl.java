package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.itsci.model.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
