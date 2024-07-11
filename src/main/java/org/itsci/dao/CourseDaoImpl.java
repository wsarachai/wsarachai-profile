package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Course;
import org.itsci.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Course getCourseById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Course course = session.get(Course.class, id);
        return course;
    }

    @Override
    public Course update(Course course) {
        Session session = sessionFactory.getCurrentSession();
        course = (Course) session.merge(course);
        return course;
    }

    @Override
    public void save(Course course) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(course);
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Course course = session.load(Course.class, id);
        session.delete(course);
        session.flush();
    }

    @Override
    public List<Course> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Course> criteria = builder.createQuery(Course.class);
        Root<Course> root = criteria.from(Course.class);
        criteria.select(root);

        Query<Course> query = session.createQuery(criteria);
        List<Course> courses = query.getResultList();

        return courses;
    }
}
