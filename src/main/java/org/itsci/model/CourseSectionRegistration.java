package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "courseSectionRegistration", fetch = FetchType.EAGER)
    @OrderBy("weekNo ASC")
    private SortedSet<Attendance> attendances = new TreeSet<>();

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

    public SortedSet<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(SortedSet<Attendance> attendances) {
        this.attendances = attendances;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
