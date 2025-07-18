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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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

  // Rate limiting - track requests per IP per minute
  private final ConcurrentHashMap<String, AtomicLong> ipRequestTimestamps = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, AtomicInteger> ipRequestCounts = new ConcurrentHashMap<>();
  private static final int MAX_REQUESTS_PER_MINUTE = 60; // Allow up to 60 requests per minute per IP

  @Autowired
  private EmailTrackingService emailTrackingService;

  /**
   * Validate tracking ID format and check rate limiting
   * 
   * @param id      the tracking ID
   * @param request the HTTP request
   * @return true if valid and not rate limited, false otherwise
   */
  private boolean isValidRequest(String id, HttpServletRequest request) {
    // Basic validation
    if (id == null || id.trim().isEmpty() || id.length() > 100) {
      return false;
    }

    // Check if tracking ID contains only valid characters (alphanumeric, hyphens,
    // underscores)
    if (!id.matches("^[a-zA-Z0-9\\-_]+$")) {
      return false;
    }

    // Rate limiting by IP
    String clientIp = getClientIpAddress(request);
    return !isRateLimited(clientIp);
  }

  /**
   * Get the real client IP address, considering proxies, load balancers, and Docker networking
   * 
   * @param request the HTTP request
   * @return the client IP address
   */
  private String getClientIpAddress(HttpServletRequest request) {
    // List of headers to check for real client IP (in order of preference)
    String[] headerNames = {
        "X-Forwarded-For",
        "X-Real-IP", 
        "X-Original-Forwarded-For",
        "CF-Connecting-IP",        // Cloudflare
        "True-Client-IP",          // Akamai, Cloudflare
        "X-Client-IP",
        "X-Forwarded",
        "X-Cluster-Client-IP",
        "Forwarded-For",
        "Forwarded"
    };
    
    for (String headerName : headerNames) {
      String headerValue = request.getHeader(headerName);
      if (headerValue != null && !headerValue.isEmpty() && !"unknown".equalsIgnoreCase(headerValue)) {
        // Handle comma-separated list of IPs (X-Forwarded-For can contain multiple IPs)
        String[] ips = headerValue.split(",");
        for (String ip : ips) {
          ip = ip.trim();
          // Skip private/local IP addresses and get the first public IP
          if (isValidPublicIP(ip)) {
            return ip;
          }
        }
      }
    }
    
    // Fallback to remote address if no valid IP found in headers
    String remoteAddr = request.getRemoteAddr();
    
    // If we get a Docker internal IP (like 172.x.x.x), try to get a better IP
    if (isDockerInternalIP(remoteAddr)) {
      // Last resort: check if there's any forwarded header with ANY IP
      for (String headerName : headerNames) {
        String headerValue = request.getHeader(headerName);
        if (headerValue != null && !headerValue.isEmpty() && !"unknown".equalsIgnoreCase(headerValue)) {
          String[] ips = headerValue.split(",");
          for (String ip : ips) {
            ip = ip.trim();
            if (isValidIP(ip)) {
              return ip;
            }
          }
        }
      }
    }
    
    return remoteAddr;
  }
  
  /**
   * Check if IP is a valid public IP address
   * @param ip the IP address to check
   * @return true if valid public IP
   */
  private boolean isValidPublicIP(String ip) {
    if (!isValidIP(ip)) {
      return false;
    }
    
    // Skip private IP ranges
    if (ip.startsWith("10.") || 
        ip.startsWith("192.168.") || 
        ip.matches("^172\\.(1[6-9]|2[0-9]|3[0-1])\\..*") ||
        ip.startsWith("127.") ||
        ip.equals("::1") ||
        ip.startsWith("fe80:") ||
        ip.startsWith("fc00:") ||
        ip.startsWith("fd00:")) {
      return false;
    }
    
    return true;
  }
  
  /**
   * Check if IP is a Docker internal IP
   * @param ip the IP address to check
   * @return true if Docker internal IP
   */
  private boolean isDockerInternalIP(String ip) {
    return ip.startsWith("172.") && ip.matches("^172\\.(1[6-9]|2[0-9]|3[0-1])\\..*");
  }
  
  /**
   * Basic IP address validation
   * @param ip the IP address to validate
   * @return true if valid IP format
   */
  private boolean isValidIP(String ip) {
    if (ip == null || ip.isEmpty()) {
      return false;
    }
    
    // Basic IPv4 validation
    if (ip.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
      return true;
    }
    
    // Basic IPv6 validation (simplified)
    if (ip.contains(":") && ip.length() > 2) {
      return true;
    }
    
    return false;
  }

  /**
   * Check if the IP is rate limited
   * 
   * @param ip the client IP address
   * @return true if rate limited, false otherwise
   */
  private boolean isRateLimited(String ip) {
    long currentTime = System.currentTimeMillis();
    long windowStart = currentTime - 60000; // 1 minute window

    // Clean up old entries
    ipRequestTimestamps.entrySet().removeIf(entry -> entry.getValue().get() < windowStart);
    ipRequestCounts.entrySet().removeIf(entry -> !ipRequestTimestamps.containsKey(entry.getKey()));

    // Check current request count for this IP
    AtomicInteger count = ipRequestCounts.computeIfAbsent(ip, k -> new AtomicInteger(0));
    AtomicLong timestamp = ipRequestTimestamps.computeIfAbsent(ip, k -> new AtomicLong(currentTime));

    // If timestamp is older than 1 minute, reset
    if (timestamp.get() < windowStart) {
      timestamp.set(currentTime);
      count.set(0);
    }

    // Check if limit exceeded
    if (count.get() >= MAX_REQUESTS_PER_MINUTE) {
      return true;
    }

    // Increment count
    count.incrementAndGet();
    return false;
  }

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

    // Security validation
    if (!isValidRequest(id, request)) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      response.setContentType(MediaType.IMAGE_GIF_VALUE);
      response.setContentLength(TRANSPARENT_GIF.length);
      return TRANSPARENT_GIF; // Return pixel even for invalid requests to avoid revealing tracking
    }

    try {
      // Debug: Log all relevant headers for IP detection
      String clientIp = getClientIpAddress(request);
      System.out.println("=== IP Detection Debug ===");
      System.out.println("Remote Addr: " + request.getRemoteAddr());
      System.out.println("X-Real-IP: " + request.getHeader("X-Real-IP"));
      System.out.println("X-Forwarded-For: " + request.getHeader("X-Forwarded-For"));
      System.out.println("X-Forwarded-Proto: " + request.getHeader("X-Forwarded-Proto"));
      System.out.println("Detected Client IP: " + clientIp);
      System.out.println("=========================");
      
      // Check if this tracking ID already exists
      List<EmailTrackingLog> existingLogs = emailTrackingService.getTrackingLogsByTrackingId(id);

      if (!existingLogs.isEmpty()) {
        // If tracking log exists, increment the opened count
        EmailTrackingLog existingLog = existingLogs.get(0);
        Integer currentCount = existingLog.getOpenedCount();
        // Handle null case - if count is null, treat it as 0
        if (currentCount == null) {
          currentCount = 0;
        }
        existingLog.setOpenedCount(currentCount + 1);
        existingLog.setTimestamp(new java.util.Date()); // Update timestamp to latest open

        // Update client information for the latest open
        existingLog.setIpAddress(clientIp);
        existingLog.setUserAgent(request.getHeader("User-Agent"));
        existingLog.setReferer(request.getHeader("Referer"));

        // Update the existing log
        emailTrackingService.updateEmailTracking(existingLog);
      } else {
        // Create new tracking log for first time open
        EmailTrackingLog log = new EmailTrackingLog();
        log.setTrackingId(id);
        log.setIpAddress(clientIp);
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setReferer(request.getHeader("Referer"));
        log.setTimestamp(new java.util.Date());
        log.setOpenedCount(1); // First open

        // Save new tracking information
        emailTrackingService.logEmailOpened(log);
      }
    } catch (Exception e) {
      // Log error but don't expose it to the client
      System.err.println("Error tracking email open: " + e.getMessage());
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
      Integer openCount = log.getOpenedCount();
      // Handle null case - if count is null, treat it as 0
      if (openCount == null) {
        openCount = 0;
      }
      response.put("openCount", openCount);
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
