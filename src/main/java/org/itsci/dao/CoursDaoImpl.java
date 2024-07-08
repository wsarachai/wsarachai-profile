package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.itsci.model.Course;
import org.itsci.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CoursDaoImpl implements CourseDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Course getCourse(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Course course = session.get(Course.class, id);
        return course;
    }

    @Override
    public Course updateCourse(Course course) {
        Session session = sessionFactory.getCurrentSession();
        course = (Course) session.merge(course);
        return course;
    }

    @Override
    public void saveCourse(Course course) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(course);
    }

    @Override
    public void deleteCourse(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Course course = session.load(Course.class, id);
        session.delete(course);
        session.flush();
    }
}
