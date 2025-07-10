package org.itsci.service;

import org.itsci.model.EmailTrackingLog;

import java.util.List;

/**
 * Service interface for email tracking operations
 */
public interface EmailTrackingService {

    /**
     * Log an email open event
     * @param log the tracking log data
     * @return the saved log
     */
    EmailTrackingLog logEmailOpened(EmailTrackingLog log);

    /**
     * Get tracking logs for a specific tracking ID
     * @param trackingId the tracking ID
     * @return list of tracking logs
     */
    List<EmailTrackingLog> getTrackingInfoById(String trackingId);
    
    /**
     * Get all tracking logs
     * @return list of all tracking logs
     */
    List<EmailTrackingLog> getAllTrackingLogs();
    
    /**
     * Get tracking logs for a specific tracking ID
     * @param trackingId the tracking ID
     * @return list of tracking logs
     */
    List<EmailTrackingLog> getTrackingLogsByTrackingId(String trackingId);

    /**
     * Get tracking logs for a specific email address
     * @param email the email address
     * @return list of tracking logs
     */
    List<EmailTrackingLog> getTrackingInfoByEmail(String email);
    
    /**
     * Delete a tracking log by its ID
     * @param id the ID of the tracking log to delete
     */
    void deleteTrackingLog(Long id);
    
    /**
     * Delete all tracking logs with a specific tracking ID
     * @param trackingId the tracking ID
     * @return the number of logs deleted
     */
    int deleteTrackingLogsByTrackingId(String trackingId);
}
