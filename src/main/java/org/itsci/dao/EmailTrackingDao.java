package org.itsci.dao;

import org.itsci.model.EmailTrackingLog;

import java.util.List;

/**
 * DAO interface for EmailTrackingLog entities
 */
public interface EmailTrackingDao {

    /**
     * Save a tracking log entry
     * @param log the tracking log to save
     * @return the saved log with id assigned
     */
    EmailTrackingLog save(EmailTrackingLog log);
    
    /**
     * Find tracking logs by tracking ID
     * @param trackingId the tracking ID
     * @return list of tracking logs
     */
    List<EmailTrackingLog> findByTrackingId(String trackingId);
    
    /**
     * Find tracking logs by recipient email
     * @param email the recipient email
     * @return list of tracking logs
     */
    List<EmailTrackingLog> findByEmail(String email);
}
