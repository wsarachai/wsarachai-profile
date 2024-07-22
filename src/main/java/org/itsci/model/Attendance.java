package org.itsci.model;

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
    @Column(name="status", columnDefinition = "VARCHAR(30)")
    @Convert(converter = EAttendanceStatusConverter.class)
    private EAttendanceStatus status;
    @CreationTimestamp
    @Column(name="attendance_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date attendanceTime;
    @Column(name="week_no")
    private int weekNo;
    @Column(name="latitude")
    private Double latitude;
    @Column(name="longitude")
    private Double longitude;
    @Lob
    @Column(name="student_image")
    private Byte[] studentImage;
    @Lob
    @Column(name="code_image")
    private Byte[] codeImage;

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

    public Byte[] getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(Byte[] studentImage) {
        this.studentImage = studentImage;
    }

    public Byte[] getCodeImage() {
        return codeImage;
    }

    public void setCodeImage(Byte[] codeImage) {
        this.codeImage = codeImage;
    }

    @Override
    public int compareTo(Attendance o) {
        return this.weekNo - o.weekNo;
    }
}
