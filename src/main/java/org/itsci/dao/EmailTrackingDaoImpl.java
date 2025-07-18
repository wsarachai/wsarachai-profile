package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.EmailTrackingLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of EmailTrackingDao interface
 */
@Repository
public class EmailTrackingDaoImpl implements EmailTrackingDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public EmailTrackingLog save(EmailTrackingLog log) {
        getCurrentSession().saveOrUpdate(log);
        return log;
    }

    @Override
    public List<EmailTrackingLog> findByTrackingId(String trackingId) {
        Query<EmailTrackingLog> query = getCurrentSession().createQuery(
                "from EmailTrackingLog where trackingId = :trackingId",
                EmailTrackingLog.class);
        query.setParameter("trackingId", trackingId);
        return query.getResultList();
    }

    @Override
    public List<EmailTrackingLog> findByEmail(String email) {
        Query<EmailTrackingLog> query = getCurrentSession().createQuery(
                "from EmailTrackingLog where recipientEmail = :email",
                EmailTrackingLog.class);
        query.setParameter("email", email);
        return query.getResultList();
    }
    
    @Override
    public List<EmailTrackingLog> findAll() {
        return getCurrentSession().createQuery(
                "from EmailTrackingLog order by timestamp desc", 
                EmailTrackingLog.class).getResultList();
    }
    
    @Override
    public void deleteById(Long id) {
        EmailTrackingLog log = getCurrentSession().get(EmailTrackingLog.class, id);
        if (log != null) {
            getCurrentSession().delete(log);
        }
    }
    
    @Override
    public int deleteByTrackingId(String trackingId) {
        Query<?> query = getCurrentSession().createQuery(
                "delete from EmailTrackingLog where trackingId = :trackingId");
        query.setParameter("trackingId", trackingId);
        return query.executeUpdate();
    }
    
    @Override
    public EmailTrackingLog update(EmailTrackingLog log) {
        getCurrentSession().update(log);
        return log;
    }
}
