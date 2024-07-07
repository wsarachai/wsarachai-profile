package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.CourseSectionRegistration;
import org.itsci.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CourseSectionRegistrationDaoImpl implements CourseSectionRegistrationDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<CourseSectionRegistration> findStudentByCourseSectionId(Long courseSectionId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<CourseSectionRegistration> criteria = builder.createQuery(CourseSectionRegistration.class);
        Root<CourseSectionRegistration> root = criteria.from(CourseSectionRegistration.class);

        criteria.select(root);
        criteria.where(builder.equal(root.get("courseSection").get("id"), courseSectionId));
        criteria.orderBy(builder.asc(root.get("student").get("studentId")));

        Query<CourseSectionRegistration> query = session.createQuery(criteria);
        List<CourseSectionRegistration> courseSectionRegistrations = query.getResultList();
        return courseSectionRegistrations;
    }
}
