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
     * Find all tracking logs
     * @return list of all tracking logs
     */
    List<EmailTrackingLog> findAll();
    
    /**
     * Find tracking logs by recipient email
     * @param email the recipient email
     * @return list of tracking logs
     */
    List<EmailTrackingLog> findByEmail(String email);
    
    /**
     * Delete a tracking log by its ID
     * @param id the ID of the tracking log to delete
     */
    void deleteById(Long id);
    
    /**
     * Delete all tracking logs with a specific tracking ID
     * @param trackingId the tracking ID
     * @return the number of logs deleted
     */
    int deleteByTrackingId(String trackingId);
    
    /**
     * Update an existing tracking log
     * @param log the tracking log to update
     * @return the updated log
     */
    EmailTrackingLog update(EmailTrackingLog log);
}
