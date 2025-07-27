package org.itsci.service;

import org.itsci.model.EmailTrackingAuth;

import java.util.Date;
import java.util.List;

/**
 * Service interface for email tracking authentication operations
 */
public interface EmailTrackingAuthService {

  /**
   * Generate a new authentication key for a tracking ID
   * 
   * @param trackingId     the tracking ID
   * @param expirationDays number of days until expiration
   * @param description    optional description
   * @return the generated auth record
   */
  EmailTrackingAuth generateAuthKey(String trackingId, int expirationDays, String description);

  /**
   * Validate an authentication key
   * 
   * @param authKey the authentication key to validate
   * @return true if valid and not expired, false otherwise
   */
  boolean validateAuthKey(String authKey);

  /**
   * Get authentication record by auth key
   * 
   * @param authKey the authentication key
   * @return the auth record or null if not found
   */
  EmailTrackingAuth getByAuthKey(String authKey);

  /**
   * Get all authentication records for a tracking ID
   * 
   * @param trackingId the tracking ID
   * @return list of auth records
   */
  List<EmailTrackingAuth> getByTrackingId(String trackingId);

  /**
   * Get all active authentication records
   * 
   * @return list of active auth records
   */
  List<EmailTrackingAuth> getAllActive();

  /**
   * Get all authentication records (active and inactive)
   * 
   * @return list of all auth records
   */
  List<EmailTrackingAuth> getAll();

  /**
   * Clean up expired authentication records
   * 
   * @return number of records deleted
   */
  int cleanupExpired();

  /**
   * Deactivate an authentication key
   * 
   * @param authKey the authentication key to deactivate
   * @return true if deactivated, false if not found
   */
  boolean deactivateAuthKey(String authKey);

  /**
   * Delete authentication record by ID
   * 
   * @param id the ID of the record to delete
   */
  void deleteAuthRecord(Long id);
}
