package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Teacher;
import org.itsci.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class TeachingClassDaoImpl implements TeachingClassDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Course> findByTeacher(Teacher teacher) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Course> criteria = builder.createQuery(Course.class);
        Root<Course> root = criteria.from(Course.class);

        criteria.select(root);
        criteria.where(builder.equal(root.get("teacher").get("id"), teacher.getId()));
        criteria.orderBy(builder.asc(root.get("subject").get("code")));

        Query<Course> query = session.createQuery(criteria);
        return query.getResultList();
    }
}
