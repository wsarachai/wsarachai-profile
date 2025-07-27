package org.itsci.service;

import org.itsci.dao.EmailTrackingAuthDao;
import org.itsci.model.EmailTrackingAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of EmailTrackingAuthService
 */
@Service
public class EmailTrackingAuthServiceImpl implements EmailTrackingAuthService {

  private static final Logger logger = Logger.getLogger(EmailTrackingAuthServiceImpl.class.getName());
  private static final String AUTH_KEY_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final int AUTH_KEY_LENGTH = 32;
  private final SecureRandom random = new SecureRandom();

  @Autowired
  private EmailTrackingAuthDao emailTrackingAuthDao;

  @Override
  @Transactional
  public EmailTrackingAuth generateAuthKey(String trackingId, int expirationDays, String description) {
    logger.info("Generating auth key for tracking ID: " + trackingId + " with expiration: " + expirationDays + " days");

    EmailTrackingAuth auth = new EmailTrackingAuth();
    auth.setAuthKey(generateRandomKey());
    auth.setTrackingId(trackingId);
    auth.setCreatedAt(new Date());
    auth.setIsActive(true); // Explicitly set to active
    auth.setDescription(description);

    // Calculate expiration date
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, expirationDays);
    auth.setExpiresAt(calendar.getTime());

    return emailTrackingAuthDao.save(auth);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean validateAuthKey(String authKey) {
    if (authKey == null || authKey.trim().isEmpty()) {
      return false;
    }

    EmailTrackingAuth auth = emailTrackingAuthDao.findByAuthKey(authKey);
    if (auth == null || !auth.getIsActive()) {
      return false;
    }

    // Check if expired
    if (auth.isExpired()) {
      logger.info("Auth key expired: " + authKey);
      return false;
    }

    return true;
  }

  @Override
  @Transactional(readOnly = true)
  public EmailTrackingAuth getByAuthKey(String authKey) {
    return emailTrackingAuthDao.findByAuthKey(authKey);
  }

  @Override
  @Transactional(readOnly = true)
  public List<EmailTrackingAuth> getByTrackingId(String trackingId) {
    return emailTrackingAuthDao.findByTrackingId(trackingId);
  }

  @Override
  @Transactional(readOnly = true)
  public List<EmailTrackingAuth> getAllActive() {
    return emailTrackingAuthDao.findAllActive();
  }

  @Override
  @Transactional(readOnly = true)
  public List<EmailTrackingAuth> getAll() {
    return emailTrackingAuthDao.findAll();
  }

  @Override
  @Transactional
  public int cleanupExpired() {
    logger.info("Cleaning up expired authentication records");
    return emailTrackingAuthDao.deleteExpired(new Date());
  }

  @Override
  @Transactional
  public boolean deactivateAuthKey(String authKey) {
    logger.info("Deactivating auth key: " + authKey);
    int updated = emailTrackingAuthDao.deactivateByAuthKey(authKey);
    return updated > 0;
  }

  @Override
  @Transactional
  public void deleteAuthRecord(Long id) {
    logger.info("Deleting auth record with ID: " + id);
    emailTrackingAuthDao.deleteById(id);
  }

  /**
   * Generate a random authentication key
   * 
   * @return the generated key
   */
  private String generateRandomKey() {
    StringBuilder sb = new StringBuilder(AUTH_KEY_LENGTH);
    for (int i = 0; i < AUTH_KEY_LENGTH; i++) {
      sb.append(AUTH_KEY_CHARS.charAt(random.nextInt(AUTH_KEY_CHARS.length())));
    }
    return sb.toString();
  }
}
