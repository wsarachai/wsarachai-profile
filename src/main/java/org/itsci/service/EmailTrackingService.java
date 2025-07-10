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
     * Get tracking logs for a specific email address
     * @param email the email address
     * @return list of tracking logs
     */
    List<EmailTrackingLog> getTrackingInfoByEmail(String email);
}
