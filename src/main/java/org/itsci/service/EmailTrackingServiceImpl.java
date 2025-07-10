package org.itsci.service;

import org.itsci.dao.EmailTrackingDao;
import org.itsci.model.EmailTrackingLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of EmailTrackingService
 */
@Service
public class EmailTrackingServiceImpl implements EmailTrackingService {

    private static final Logger logger = Logger.getLogger(EmailTrackingServiceImpl.class.getName());

    @Autowired
    private EmailTrackingDao emailTrackingDao;

    @Override
    @Transactional
    public EmailTrackingLog logEmailOpened(EmailTrackingLog log) {
        logger.info("Logging email open event with tracking ID: " + log.getTrackingId());
        return emailTrackingDao.save(log);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailTrackingLog> getTrackingInfoById(String trackingId) {
        return emailTrackingDao.findByTrackingId(trackingId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailTrackingLog> getTrackingInfoByEmail(String email) {
        return emailTrackingDao.findByEmail(email);
    }
}
