package org.itsci.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "attendances")
public class Attendance implements Comparable<Attendance> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @Column(name = "status", columnDefinition = "VARCHAR(30)")
    @Convert(converter = EAttendanceStatusConverter.class)
    private EAttendanceStatus status;
    @CreationTimestamp
    @Column(name = "attendance_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date attendanceTime;
    @Column(name = "week_no")
    private int weekNo;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "image1_path")
    private String image1_path;
    @Column(name = "image2_path")
    private String image2_path;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EAttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(EAttendanceStatus status) {
        this.status = status;
    }

    public Date getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(Date attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public int getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(int weekNo) {
        this.weekNo = weekNo;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getImage1_path() {
        return image1_path;
    }

    public void setImage1_path(String image1_path) {
        this.image1_path = image1_path;
    }

    public String getImage2_path() {
        return image2_path;
    }

    public void setImage2_path(String image2_path) {
        this.image2_path = image2_path;
    }

    @Override
    public int compareTo(Attendance o) {
        return this.weekNo - o.weekNo;
    }
}
