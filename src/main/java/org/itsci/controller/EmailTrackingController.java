package org.itsci.controller;

import org.itsci.model.EmailTrackingLog;
import org.itsci.service.EmailTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for handling email tracking
 */
@Controller
@RequestMapping("/email")
public class EmailTrackingController {

  // A 1x1 transparent GIF pixel
  private static final byte[] TRANSPARENT_GIF = {
      0x47, 0x49, 0x46, 0x38, 0x39, 0x61, 0x01, 0x00, 0x01, 0x00, (byte) 0x80, 0x00,
      0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x00, 0x00, 0x00, 0x21, (byte) 0xF9,
      0x04, 0x01, 0x00, 0x00, 0x00, 0x00, 0x2C, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00,
      0x01, 0x00, 0x00, 0x02, 0x02, 0x44, 0x01, 0x00, 0x3B
  };

  @Autowired
  private EmailTrackingService emailTrackingService;

  /**
   * Endpoint for tracking email opens
   * 
   * @param id       the tracking ID
   * @param request  the HTTP request
   * @param response the HTTP response
   * @throws IOException if there is an error writing the response
   */
  @GetMapping("/track")
  @ResponseBody
  public byte[] trackEmailOpen(@RequestParam("id") String id,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    // Check if this tracking ID already exists
    List<EmailTrackingLog> existingLogs = emailTrackingService.getTrackingLogsByTrackingId(id);
    
    if (!existingLogs.isEmpty()) {
      // If tracking log exists, increment the opened count
      EmailTrackingLog existingLog = existingLogs.get(0);
      Integer currentCount = existingLog.getOpenedCount();
      existingLog.setOpenedCount(currentCount + 1);
      existingLog.setTimestamp(new java.util.Date()); // Update timestamp to latest open
      
      // Update the existing log
      emailTrackingService.updateEmailTracking(existingLog);
    } else {
      // Create new tracking log for first time open
      EmailTrackingLog log = new EmailTrackingLog();
      log.setTrackingId(id);
      log.setIpAddress(request.getRemoteAddr());
      log.setUserAgent(request.getHeader("User-Agent"));
      log.setReferer(request.getHeader("Referer"));
      log.setTimestamp(new java.util.Date());
      log.setOpenedCount(1); // First open
      
      // Save new tracking information
      emailTrackingService.logEmailOpened(log);
    }

    // Set the response headers
    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.IMAGE_GIF_VALUE);
    response.setContentLength(TRANSPARENT_GIF.length);

    // Return the 1x1 transparent GIF
    return TRANSPARENT_GIF;
  }
  
  /**
   * Endpoint to check if an email has been opened
   * 
   * @param id the tracking ID
   * @return JSON response with the tracking status
   */
  @GetMapping("/opened")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> checkEmailOpened(@RequestParam("id") String id) {
    List<EmailTrackingLog> logs = emailTrackingService.getTrackingLogsByTrackingId(id);
    
    Map<String, Object> response = new HashMap<>();
    boolean isOpened = !logs.isEmpty();
    
    response.put("trackingId", id);
    response.put("opened", isOpened);
    
    if (isOpened) {
      EmailTrackingLog log = logs.get(0);
      response.put("openCount", log.getOpenedCount());
      response.put("firstOpenedAt", log.getTimestamp());
      
      // If there are multiple logs (shouldn't happen with the new approach), 
      // we still show the total count from the single log's openedCount field
      if (logs.size() > 1) {
        EmailTrackingLog lastLog = logs.get(logs.size() - 1);
        response.put("lastOpenedAt", lastLog.getTimestamp());
      }
    } else {
      response.put("openCount", 0);
    }
    
    return ResponseEntity.ok(response);
  }
}
