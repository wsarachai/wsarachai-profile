package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.itsci.model.CourseSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseSectionDaoImpl implements CourseSectionDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public CourseSection getCourseSection(Long id) {
        Session session = sessionFactory.getCurrentSession();
        CourseSection courseSection = session.get(CourseSection.class, id);
        return courseSection;
    }
}
