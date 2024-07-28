package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @ManyToOne
    @JoinColumn(name="section_id")
    private Section section;
    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="lec_atten_id")
    @OrderBy("weekNo ASC")
    private SortedSet<Attendance> lecAtten = new TreeSet<>();
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="lab_atten_id")
    @OrderBy("weekNo ASC")
    private SortedSet<Attendance> labAtten = new TreeSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
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
