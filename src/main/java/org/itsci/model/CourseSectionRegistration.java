package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "student_registrations")
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
    private SortedSet<Attendance> lecAtten = new TreeSet<>();
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "courseSectionRegistration", fetch = FetchType.EAGER)
    @OrderBy("weekNo ASC")
    private SortedSet<Attendance> labAtten = new TreeSet<>();

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

    public SortedSet<Attendance> getLecAtten() {
        return lecAtten;
    }

    public void setLecAtten(SortedSet<Attendance> lecAtten) {
        this.lecAtten = lecAtten;
    }

    public SortedSet<Attendance> getLabAtten() {
        return labAtten;
    }

    public void setLabAtten(SortedSet<Attendance> labAtten) {
        this.labAtten = labAtten;
    }
}
