package org.itsci.dao;

import org.itsci.model.EmailTrackingAuth;

import java.util.Date;
import java.util.List;

/**
 * DAO interface for EmailTrackingAuth entities
 */
public interface EmailTrackingAuthDao {

  /**
   * Save an authentication key
   * 
   * @param auth the auth to save
   * @return the saved auth with id assigned
   */
  EmailTrackingAuth save(EmailTrackingAuth auth);

  /**
   * Find authentication by auth key
   * 
   * @param authKey the authentication key
   * @return the auth record or null if not found
   */
  EmailTrackingAuth findByAuthKey(String authKey);

  /**
   * Find all authentication records for a tracking ID
   * 
   * @param trackingId the tracking ID
   * @return list of auth records
   */
  List<EmailTrackingAuth> findByTrackingId(String trackingId);

  /**
   * Find all active (non-expired) authentication records
   * 
   * @return list of active auth records
   */
  List<EmailTrackingAuth> findAllActive();

  /**
   * Find all authentication records (active and inactive)
   * 
   * @return list of all auth records
   */
  List<EmailTrackingAuth> findAll();

  /**
   * Delete expired authentication records
   * 
   * @param expirationDate delete records that expired before this date
   * @return number of records deleted
   */
  int deleteExpired(Date expirationDate);

  /**
   * Deactivate an authentication key
   * 
   * @param authKey the authentication key to deactivate
   * @return number of records updated
   */
  int deactivateByAuthKey(String authKey);

  /**
   * Delete authentication record by ID
   * 
   * @param id the ID of the record to delete
   */
  void deleteById(Long id);
}
