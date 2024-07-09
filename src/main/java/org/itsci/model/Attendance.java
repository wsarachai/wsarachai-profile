package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "attendances")
public class Attendance implements Comparable<Attendance> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_section_registration_id")
    private CourseSectionRegistration courseSectionRegistration;
    @Column(name="status", columnDefinition = "VARCHAR(30)")
    @Convert(converter = EAttendanceStatusConverter.class)
    private EAttendanceStatus status;
    @Column(name="week_no")
    private int weekNo;
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

    public CourseSectionRegistration getCourseSectionRegistration() {
        return courseSectionRegistration;
    }

    public void setCourseSectionRegistration(CourseSectionRegistration courseSectionRegistration) {
        this.courseSectionRegistration = courseSectionRegistration;
    }

    public EAttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(EAttendanceStatus status) {
        this.status = status;
    }

    public int getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(int weekNo) {
        this.weekNo = weekNo;
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
