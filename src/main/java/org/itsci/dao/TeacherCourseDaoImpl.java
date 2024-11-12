package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Teacher;
import org.itsci.model.TeacherCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class TeacherCourseDaoImpl implements TeacherCourseDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<TeacherCourse> findCourseByTeacher(Teacher teacher, String semester, String status) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<TeacherCourse> criteria = builder.createQuery(TeacherCourse.class);
        Root<TeacherCourse> root = criteria.from(TeacherCourse.class);

        criteria.select(root);

        if (semester != null && status != null) {
            criteria.where(builder.and(
                    builder.equal(root.get("status"), status),
                    builder.equal(root.get("teacher"), teacher),
                    builder.equal(root.get("course").get("semester"), semester)
            ));
        } else if (semester != null) {
            criteria.where(builder.and(
                    builder.equal(root.get("teacher"), teacher),
                    builder.equal(root.get("course").get("semester"), semester)
            ));
        } else if (status != null) {
            criteria.where(builder.and(
                    builder.equal(root.get("status"), status),
                    builder.equal(root.get("teacher"), teacher)
            ));
        }

        Query<TeacherCourse> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public void save(TeacherCourse teacherCourse) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(teacherCourse);
    }
}
