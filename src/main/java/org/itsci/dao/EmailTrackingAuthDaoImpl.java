package org.itsci.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.itsci.model.EmailTrackingAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Implementation of EmailTrackingAuthDao interface
 */
@Repository
public class EmailTrackingAuthDaoImpl implements EmailTrackingAuthDao {

  @Autowired
  private SessionFactory sessionFactory;

  private Session getCurrentSession() {
    return sessionFactory.getCurrentSession();
  }

  @Override
  public EmailTrackingAuth save(EmailTrackingAuth auth) {
    getCurrentSession().saveOrUpdate(auth);
    return auth;
  }

  @Override
  public EmailTrackingAuth findByAuthKey(String authKey) {
    Query<EmailTrackingAuth> query = getCurrentSession().createQuery(
        "from EmailTrackingAuth where authKey = :authKey and isActive = true",
        EmailTrackingAuth.class);
    query.setParameter("authKey", authKey);
    return query.uniqueResult();
  }

  @Override
  public List<EmailTrackingAuth> findByTrackingId(String trackingId) {
    Query<EmailTrackingAuth> query = getCurrentSession().createQuery(
        "from EmailTrackingAuth where trackingId = :trackingId order by createdAt desc",
        EmailTrackingAuth.class);
    query.setParameter("trackingId", trackingId);
    return query.getResultList();
  }

  @Override
  public List<EmailTrackingAuth> findAllActive() {
    Query<EmailTrackingAuth> query = getCurrentSession().createQuery(
        "from EmailTrackingAuth where isActive = true and expiresAt > :now order by createdAt desc",
        EmailTrackingAuth.class);
    query.setParameter("now", new Date());
    return query.getResultList();
  }

  @Override
  public List<EmailTrackingAuth> findAll() {
    Query<EmailTrackingAuth> query = getCurrentSession().createQuery(
        "from EmailTrackingAuth order by createdAt desc",
        EmailTrackingAuth.class);
    return query.getResultList();
  }

  @Override
  public int deleteExpired(Date expirationDate) {
    Query<?> query = getCurrentSession().createQuery(
        "delete from EmailTrackingAuth where expiresAt < :expDate");
    query.setParameter("expDate", expirationDate);
    return query.executeUpdate();
  }

  @Override
  public int deactivateByAuthKey(String authKey) {
    Query<?> query = getCurrentSession().createQuery(
        "update EmailTrackingAuth set isActive = false where authKey = :authKey");
    query.setParameter("authKey", authKey);
    return query.executeUpdate();
  }

  @Override
  public void deleteById(Long id) {
    EmailTrackingAuth auth = getCurrentSession().get(EmailTrackingAuth.class, id);
    if (auth != null) {
      getCurrentSession().delete(auth);
    }
  }
}
