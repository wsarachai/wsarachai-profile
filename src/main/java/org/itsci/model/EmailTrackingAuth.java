package org.itsci.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Model class for storing email tracking authentication keys with expiration
 */
@Entity
@Table(name = "email_tracking_auth")
public class EmailTrackingAuth {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "auth_key", unique = true, nullable = false)
  private String authKey;

  @Column(name = "tracking_id", nullable = false)
  private String trackingId;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false)
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "expires_at", nullable = false)
  private Date expiresAt;

  @Column(name = "is_active")
  private Boolean isActive = true;

  @Column(name = "description")
  private String description;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAuthKey() {
    return authKey;
  }

  public void setAuthKey(String authKey) {
    this.authKey = authKey;
  }

  public String getTrackingId() {
    return trackingId;
  }

  public void setTrackingId(String trackingId) {
    this.trackingId = trackingId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isExpired() {
    return new Date().after(this.expiresAt);
  }

  @Override
  public String toString() {
    return "EmailTrackingAuth{" +
        "id=" + id +
        ", authKey='" + authKey + '\'' +
        ", trackingId='" + trackingId + '\'' +
        ", createdAt=" + createdAt +
        ", expiresAt=" + expiresAt +
        ", isActive=" + isActive +
        '}';
  }
}
