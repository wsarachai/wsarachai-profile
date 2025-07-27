# Email Tracking Authentication Feature

## Overview

The email tracking system now includes time-based authentication to control access to tracking URLs and prevent abuse from expired links.

## Features

### 1. Authentication Key Generation

- Generate secure authentication keys for tracking IDs
- Configurable expiration period (1-365 days)
- Optional description for tracking purposes
- Automatic cleanup of expired keys

### 2. Protected Tracking Endpoint

- Tracking URLs now require valid authentication keys
- Expired keys are automatically rejected
- Graceful handling - returns tracking pixel even for invalid requests

### 3. Validation Endpoint

- Check if authentication keys are valid
- Get expiration information
- Useful for debugging and monitoring

## API Endpoints

### Generate Authentication Key

```
GET /wsarachai/email/generate-auth?trackingId=YOUR_ID&days=7&description=Optional
```

**Parameters:**

- `trackingId` (required): The tracking identifier
- `days` (optional, default: 7): Days until expiration (1-365)
- `description` (optional): Description for the auth key

**Response:**

```json
{
  "success": true,
  "authKey": "ABC123XYZ789...",
  "trackingId": "YOUR_ID",
  "expiresAt": "2025-08-03T10:30:00.000Z",
  "createdAt": "2025-07-27T10:30:00.000Z",
  "description": "Optional",
  "trackingUrl": "/wsarachai/email/track?id=YOUR_ID&auth=ABC123XYZ789..."
}
```

### Track Email Opens (Updated)

```
GET /wsarachai/email/track?id=YOUR_ID&auth=YOUR_AUTH_KEY
```

**Parameters:**

- `id` (required): The tracking identifier
- `auth` (required): Valid authentication key

**Response:** 1x1 transparent GIF pixel

### Validate Authentication Key

```
GET /wsarachai/email/validate-auth?authKey=YOUR_AUTH_KEY
```

**Parameters:**

- `authKey` (required): The authentication key to validate

**Response:**

```json
{
  "valid": true,
  "authKey": "YOUR_AUTH_KEY",
  "trackingId": "YOUR_ID",
  "expiresAt": "2025-08-03T10:30:00.000Z",
  "description": "Optional"
}
```

### Check Email Opens (Unchanged)

```
GET /wsarachai/email/opened?id=YOUR_ID
```

## Implementation Example

### 1. Generate Auth Key

```javascript
// Generate auth key for 7 days
const response = await fetch(
  "/wsarachai/email/generate-auth?trackingId=EMAIL-001&days=7&description=Newsletter%20Campaign"
);
const data = await response.json();

if (data.success) {
  const trackingUrl = data.trackingUrl;
  // Use trackingUrl in your email template
}
```

### 2. Use in Email Template

```html
<!-- Include tracking pixel in email -->
<img
  src="https://yourserver.com/wsarachai/email/track?id=EMAIL-001&auth=ABC123XYZ789..."
  width="1"
  height="1"
  alt=""
  style="display:none;"
/>
```

### 3. Google Apps Script Integration

```javascript
function sendTrackedEmails() {
  const sheet = SpreadsheetApp.getActiveSpreadsheet().getActiveSheet();
  const data = sheet.getDataRange().getValues();

  for (let i = 1; i < data.length; i++) {
    const email = data[i][0];
    const trackingId = data[i][3];

    // Generate auth key for 14 days
    const authResponse = UrlFetchApp.fetch(
      `https://yourserver.com/wsarachai/email/generate-auth?trackingId=${trackingId}&days=14`
    );
    const authData = JSON.parse(authResponse.getContentText());

    if (authData.success) {
      const trackingPixel = `<img src="https://yourserver.com${authData.trackingUrl}" width="1" height="1" alt="" />`;

      const emailBody = `
        Your email content here...
        ${trackingPixel}
      `;

      GmailApp.sendEmail(email, "Subject", "", {
        htmlBody: emailBody,
      });
    }
  }
}
```

## Database Schema

### email_tracking_auth Table

```sql
CREATE TABLE email_tracking_auth (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    auth_key VARCHAR(64) NOT NULL UNIQUE,
    tracking_id VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    description TEXT,
    INDEX idx_auth_key (auth_key),
    INDEX idx_tracking_id (tracking_id),
    INDEX idx_expires_at (expires_at)
);
```

## Security Features

### 1. Time-Based Expiration

- Authentication keys automatically expire after specified period
- Expired keys are rejected at the application level
- Database cleanup removes old expired keys

### 2. Secure Key Generation

- 32-character random keys using SecureRandom
- Alphanumeric characters (A-Z, a-z, 0-9)
- Cryptographically secure random generation

### 3. Rate Limiting

- Existing IP-based rate limiting still applies
- 60 requests per minute per IP address
- Protection against abuse and automated attacks

### 4. Graceful Degradation

- Invalid or expired auth keys still return tracking pixel
- Prevents revealing tracking system existence
- Maintains email layout integrity

## Testing

Access the test page at:

```
https://yourserver.com/wsarachai/test/ip
```

The test page allows you to:

1. Generate authentication keys
2. Test tracking with authentication
3. Validate authentication keys
4. Monitor IP detection

## Monitoring and Cleanup

### Manual Cleanup

```sql
-- Delete expired authentication keys
DELETE FROM email_tracking_auth WHERE expires_at < NOW();

-- Deactivate specific auth key
UPDATE email_tracking_auth SET is_active = false WHERE auth_key = 'YOUR_AUTH_KEY';
```

### Scheduled Cleanup

Consider setting up a scheduled task to regularly clean up expired authentication keys to maintain database performance.

## Migration Guide

### Existing Tracking URLs

Old tracking URLs without authentication will be rejected. You need to:

1. **Generate new auth keys** for existing tracking IDs
2. **Update email templates** to include auth parameter
3. **Re-send emails** with new authenticated tracking URLs

### Backward Compatibility

This is a breaking change. All tracking URLs must include valid authentication keys.
