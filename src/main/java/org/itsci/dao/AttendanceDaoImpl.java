package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Attendance;
import org.itsci.model.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

@Repository
public class AttendanceDaoImpl implements AttendanceDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void save(Attendance attendance) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(attendance);
    }

    @Override
    public void update(Attendance attendance) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(attendance);
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Attendance where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Attendance findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Attendance attendance = session.get(Attendance.class, id);
        return attendance;
    }

    @Override
    public List<Attendance> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Attendance> criteria = builder.createQuery(Attendance.class);
        Root<Attendance> root = criteria.from(Attendance.class);
        criteria.select(root);

        Query<Attendance> query = session.createQuery(criteria);
        List<Attendance> attendances = query.getResultList();

        return attendances;
    }

    @Override
    public SortedSet<Attendance> findByType(Enrollment enrollment, String type) {
        SortedSet<Attendance> attendances = new java.util.TreeSet<>();
        Session session = sessionFactory.getCurrentSession();
        Enrollment _enrollment = session.get(Enrollment.class, enrollment.getId());
        if ("lec".equalsIgnoreCase(type)) {
            for (Attendance attendance : _enrollment.getLecAtten()) {
                attendances.add(attendance);
            }
        } else if ("lab".equalsIgnoreCase(type)) {
            for (Attendance attendance : _enrollment.getLabAtten()) {
                attendances.add(attendance);
            }
        }
        return attendances;
    }
}
