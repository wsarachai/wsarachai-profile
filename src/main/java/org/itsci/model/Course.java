package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "courses")
public class Course implements Comparable<Course> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @ManyToOne
    @JoinColumn(name="subject_id")
    private Subject subject;
    @Column(name="semester")
    private String semester;
    @Temporal(TemporalType.DATE)
    @Column(name="start_semester")
    private Date startSemester;
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "course", fetch = FetchType.EAGER)
    @OrderBy("groupNumber ASC")
    private SortedSet<CourseSection> courseSectionSet = new TreeSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Date getStartSemester() {
        return startSemester;
    }

    public void setStartSemester(Date startSemester) {
        this.startSemester = startSemester;
    }

    public SortedSet<CourseSection> getCourseSectionSet() {
        return courseSectionSet;
    }

    public void setCourseSectionSet(SortedSet<CourseSection> courseSectionSet) {
        this.courseSectionSet = courseSectionSet;
    }

    @Override
    public int compareTo(Course o) {
        return o.getSubject().getCode().compareTo(this.getSubject().getCode());
    }
}
