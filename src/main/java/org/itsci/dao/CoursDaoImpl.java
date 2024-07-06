package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.itsci.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CoursDaoImpl implements CourseDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Course getUser(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Course course = session.get(Course.class, id);
        return course;
    }
}
