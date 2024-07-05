package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
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
    @OneToMany(fetch = FetchType.EAGER)
    private Set<CourseSection> courseSectionSet = new HashSet<>();

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

    public Set<CourseSection> getCourseSectionSet() {
        return courseSectionSet;
    }

    public void setCourseSectionSet(Set<CourseSection> courseSectionSet) {
        this.courseSectionSet = courseSectionSet;
    }
}
