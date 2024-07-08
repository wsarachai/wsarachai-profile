package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceDaoImpl implements AttendanceDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void saveAttendance(Attendance attendance) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(attendance);
    }

    @Override
    public Attendance updateAttendance(Attendance attendance) {
        Session session = sessionFactory.getCurrentSession();
        attendance = (Attendance) session.merge(attendance);
        return attendance;
    }

    @Override
    public void deleteAttendance(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Attendance where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Attendance findAttendanceById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Attendance attendance = session.get(Attendance.class, id);
        return attendance;
    }
}
