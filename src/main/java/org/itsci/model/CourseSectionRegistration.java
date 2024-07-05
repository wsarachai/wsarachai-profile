package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "course_section_registrations")
public class CourseSectionRegistration {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @ManyToOne
    @JoinColumn(name="course_section_id")
    private CourseSection courseSection;
    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;
    @Column(name="attendance")
    private String attendance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CourseSection getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(CourseSection courseSection) {
        this.courseSection = courseSection;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
