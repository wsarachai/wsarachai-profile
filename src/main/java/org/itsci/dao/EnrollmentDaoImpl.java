package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class EnrollmentDaoImpl implements EnrollmentDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Enrollment> findBySectionId(Long sectionId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Enrollment> criteria = builder.createQuery(Enrollment.class);
        Root<Enrollment> root = criteria.from(Enrollment.class);

        criteria.select(root);
        criteria.where(builder.equal(root.get("section").get("id"), sectionId));
        criteria.orderBy(builder.asc(root.get("student").get("studentId")));

        Query<Enrollment> query = session.createQuery(criteria);
        List<Enrollment> enrollments = query.getResultList();
        return enrollments;
    }

    @Override
    public void save(Enrollment courseSectionRegistration) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(courseSectionRegistration);
    }

    @Override
    public Enrollment findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Enrollment enrollment = session.get(Enrollment.class, id);
        return enrollment;
    }

    @Override
    public void update(Enrollment enrollment) {
        Session session = sessionFactory.getCurrentSession();
        session.update(enrollment);
    }
}
